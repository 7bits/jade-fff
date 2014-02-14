$ ->
  employerFeedback = ->
    table = $("table#employer-feedback")
    if table.length
      request = $.ajax(
        url: "employer-control-panel-feedback.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        dealList = data.entry.value
        i = 0
        while i < dealList.length
          obj = dealList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.recruiterUrl + "\">" + obj.recruiter + "</td><td>" + obj.status  + "</td><td>" + obj.updated  + "</td><td><a href=\"" + obj.feedbackUrl + "\">" + obj.feedback + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          employerFeedback()
          return
        ), 10000
        return

      request.fail ->
        return

    return

  employerFeedback()
  return
