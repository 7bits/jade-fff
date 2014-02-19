$ ->
  bidTable = "table.employerBidView"
  $applyBidButtons = $(bidTable + " " + "button.bidApply")
  $declineBidButtons = $(bidTable + " " + "button.bidDecline")
  $applyBidButtons. on "click", (event) ->
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

  $declineBidButtons. on "click", (event) ->
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

  applicantTable = "table.employerApplicantView"
  $applyApplicantButtons = $(applicantTable + " " + "button.applicantApply")
  $declineApplicantButtons = $(applicantTable + " " + "button.applicantDecline")
  $applyApplicantButtons. on "click", (event) ->
    event.preventDefault()
    $button = $(event.target)
    requestData = "applicantId=" + $button.attr("data-id")
    request = $.ajax(
      url: "../employer-applicant-approve.json"
      type: "GET"
      data: requestData
      dataType: "json"
    )
    request.done (data) ->
      location.reload()
#      newLabel = data.entry.value
#      $button.html(newLabel)
#      $button.prop("disabled", true)
#      $(applicantTable).find("button").not("[disabled]").remove()
      return

    request.fail ->

  $declineApplicantButtons. on "click", (event) ->
    event.preventDefault()
    $button = $(event.target)
    requestData = "applicantId=" + $button.attr("data-id")
    request = $.ajax(
      url: "../employer-applicant-decline.json"
      type: "GET"
      data: requestData
      dataType: "json"
    )
    request.done (data) ->
      newLabel = data.entry.value
      $button.html(newLabel)
      $button.prop("disabled", true)
      bidId = $button.attr("data-id")
      $(applicantTable).find("button.btn-success[data-id=" + bidId + "]").remove()
      return

    request.fail ->
