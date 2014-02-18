$ ->
  $showBid = $("a.showBid")
  ajaxData = ""
  $showBid.popover(
    placement: "top"
    html: true
    content: ""
    trigger: "manual"
  ).hover ((event) ->
    event.preventDefault()
    $link = $(event.target)
    $popover = $link.data("bs.popover")
    requestData = "bidId=" + $link.attr("data-id")
    request = $.ajax(
      url: "../employer-recruiter-show.json"
      type: "GET"
      async: false
      data: requestData
      dataType: "json"
    )
    request.done (data) ->
      bid = data.entry
      ajaxData += "<dl class=\"dl-horizontal\"><dt>" + bid.headTitle + "</dt><dd>" + bid.title + "</dd><dt>" + bid.headDescription + "</dt><dd>" + bid.description + "</dd><dt>" + bid.headSalary + "</dt><dd>" + bid.salary + "</dd><dt>" + bid.headCreated + "</dt><dd>" + bid.created + "</dd><dt>" + bid.headMessage + "</dt><dd>" + bid.message + "</dd><dt>" + bid.headRecruiter + "</dt><dd>" + bid.recruiter + "</dd>" + "</dl>"
      return

    request.fail ->
    $popover.options.content = ajaxData
    $popover.setContent()
    $popover.show()
    return
  ), (event) ->
    $popover = $(event.target).data("bs.popover")
    $popover.options.content = ""
    $popover.setContent()
    $popover.hide()
    ajaxData = ""
    return

  return