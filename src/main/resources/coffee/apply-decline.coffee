$ ->
  bidTable = "table.employerBidView"
  $applyButtons = $(bidTable + " " + "button.bidApply")
  $declineButtons = $(bidTable + " " + "button.bidDecline")
  $applyButtons. on "click", (event) ->
    event.preventDefault()
    $button = $(event.target)
    requestData = "bidId=" + $button.attr("data-id")
    request = $.ajax(
      url: "../employer-recruiter-approve.json"
      type: "GET"
      data: requestData
      dataType: "json"
    )
    request.done (data) ->
      newLabel = data.entry.value
      $button.html(newLabel)
      $button.prop("disabled", true)
      $(bidTable).find("button").not("[disabled]").remove()
      return

    request.fail ->

  $declineButtons. on "click", (event) ->
    event.preventDefault()
    $button = $(event.target)
    requestData = "bidId=" + $button.attr("data-id")
    request = $.ajax(
      url: "../employer-recruiter-decline.json"
      type: "GET"
      data: requestData
      dataType: "json"
    )
    request.done (data) ->
      newLabel = data.entry.value
      $button.html(newLabel)
      $button.prop("disabled", true)
      bidId = $button.attr("data-id")
      $(bidTable).find("button.btn-success[data-id=" + bidId + "]").remove()
      return

    request.fail ->
