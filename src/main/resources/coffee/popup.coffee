$ ->
  $showBid = $("a.showBid")
  ajaxData = ""
  ajaxTitle = ""
  $showBid.popover(
    placement: "bottom"
    html: true
    content: ""
    template: '<div class="popover" onmouseover="refreshTimeout(this);"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
    trigger: "manual"
  ).hover ((event) ->
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
      bid = data.entry.value
      ajaxData = "<dl class=\"dl popup\"><dt>" + bid.headRecruiter + "</dt><dd><a href=\"" + bid.recruiterUrl + "\" target=\"blank\">" + bid.recruiter + "</a></dd><dt>" + bid.headCreated + "</dt><dd>" + bid.created + "</dd><dt>" + bid.headMessage + "</dt><dd>" + bid.message + "</dd>" + "</dl>"
      ajaxTitle = bid.popupTitle;
      return

    request.fail ->
    $popover.options.content = ajaxData
    $popover.options.title = ajaxTitle
    $popover.setContent()
    $popover.show()
    return
  ), (event) ->
    $popover = $(event.target).data("bs.popover")
    window.timeoutObj = setTimeout(->
      $popover.hide()
      return
    , 350)
#    $popover.options.content = ""
#    $popover.setContent()
#    $popover.hide()
#    ajaxData = ""
    return

  return
exports = this
window.timeoutObj = undefined
window.refreshTimeout = (obj) ->
  clearTimeout(window.timeoutObj);
  $(obj).mouseleave ->
    $(obj).hide()
  return
