$ ->
  lastMessageId = null
  form = $("form#messagesend")
  role = form.find("input[name='role']").val()
  form.on "submit", (event) ->
    event.preventDefault()
    data = form.serialize()
    request = $.ajax(
      url: "../" + role + "-send-message.json"
      type: "POST"
      data: data
      dataType: "json"
    )
    request.done (data) ->
      form.find("textarea").val("")
    request.fail ->
      alert "error"
      return
    return

  waitForMsg = ->
    form = $("form#messagesend")
    role = form.find("input[name='role']").val()
    dealId = form.find("input[name='id']").val()
    if typeof dealId isnt "undefined"
      data = "dealId=" + dealId
      data += "&messageId=" + lastMessageId  if lastMessageId isnt null
      request = $.ajax(
        url: "../" + role + "-show-messages.json"
        type: "GET"
        async: true
        data: data
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        messageList = data.entry.value
        i = 0
        while i < messageList.length
          obj = messageList[i]
          addHtml += "<div class='row'><div class='col-md-2'>" + obj.from + "</div><div class='col-md-2'>" + obj.date + "</div><div class='col-md-6'>" + obj.message + "</div></div>"
          i++
        if messageList.length > 0
          lastMessageId = messageList[i-1].id
        $("#chat").append addHtml
        scrollBottom = $("#chat").height()
        $("#chat").scrollTop scrollBottom
        setTimeout (->
          waitForMsg()
          return
        ), 1000
        return

      request.fail ->
        return

    return

  waitForMsg()
  return
