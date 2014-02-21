$ ->
  ajaxData = ""
  ajaxTitle = ""
  showEmployerBid = "a.showEmployerBid"
  $(document).on
    click: (event) ->
      event.preventDefault()
    mouseenter: (event) ->
      if $(event.target).is("strong")
        $link = $(event.target).parent()
      else
        $link = $(event.target)
      $link.popover(
        placement: "bottom"
        html: true
        content: ""
        template: '<div class="popover" onmouseover="refreshTimeout(this);"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
        trigger: "manual")
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
        ajaxData = "<dl class=\"dl popup\"><dt>" + bid.headRecruiter + "</dt><dd><a href=\"" + bid.recruiterUrl + "\" target=\"blank\">" + bid.recruiter + "</a></dd><dt>" + bid.headStatus + "</dt><dd>" + bid.status + "</dd><dt>" + bid.headCreated + "</dt><dd>" + bid.created + "</dd><dt>" + bid.headMessage + "</dt><dd>" + bid.message + "</dd>" + "</dl>"
        ajaxTitle = bid.popupTitle;
        if ($link.children("strong").length != 0)
          $link.html($link.find("strong").html())
        return

      request.fail ->
      $popover.options.content = ajaxData
      $popover.options.title = ajaxTitle
      $popover.setContent()
      $popover.show()
      return
    mouseleave: (event) ->
      if $(event.target).is("strong")
        $link = $(event.target).parent()
      else
        $link = $(event.target)
      $popover = $link.data("bs.popover")
      window.timeoutObj = setTimeout(->
        $popover.hide()
        return
      , 350)
  #    $popover.options.content = ""
  #    $popover.setContent()
  #    $popover.hide()
  #    ajaxData = ""
      return
  , showEmployerBid

  showApplicant = "a.showApplicant"
  $(document).on
    click: (event) ->
      event.preventDefault()
    mouseenter: (event) ->
      if $(event.target).is("strong")
        $link = $(event.target).parent()
      else
        $link = $(event.target)
      $link.popover(
        placement: "bottom"
        html: true
        content: ""
        template: '<div class="popover" onmouseover="refreshTimeout(this);"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
        trigger: "manual")
      $popover = $link.data("bs.popover")
      requestData = "applicantId=" + $link.attr("data-id")
      request = $.ajax(
        url: "../employer-applicant-show.json"
        type: "GET"
        async: false
        data: requestData
        dataType: "json"
      )
      request.done (data) ->
        applicant = data.entry.value
        ajaxData = "<dl class=\"dl popup\"><dt>" + applicant.headApplicant + "</dt><dd>" + applicant.applicant + "</dd><dt>" + applicant.headCreated + "</dt><dd>" + applicant.created + "</dd><dt>" + applicant.headGender + "</dt><dd>" + applicant.gender + "</dd><dt>" + applicant.headAge + "</dt><dd>" + applicant.age + "</dd>"
        if (applicant.resume?)
          ajaxData += "<dt>" + applicant.headResume + "</dt><dd><a href=\"" + applicant.resumeUrl + "\">" + applicant.resume + "</a></dd>"
        if (applicant.testFile?)
          ajaxData += "<dt>" + applicant.headTestAnswer + "</dt><dd><a href=\"" + applicant.testAnswerUrl + "\">" + applicant.testAnswer + "</a></dd>"
        ajaxData += "<dt>" + applicant.headDescription + "</dt><dd>" + applicant.description + "</dd>" + "</dl>"
        ajaxTitle = applicant.popupTitle;
        if ($link.children("strong").length != 0)
          $link.html($link.find("strong").html())
        return

      request.fail ->
      $popover.options.content = ajaxData
      $popover.options.title = ajaxTitle
      $popover.setContent()
      $popover.show()
      return
    mouseleave: (event) ->
      if $(event.target).is("strong")
        $link = $(event.target).parent()
      else
        $link = $(event.target)
      $popover = $link.data("bs.popover")
      window.timeoutObj = setTimeout(->
        $popover.hide()
        return
      , 350)
      return
  , showApplicant


  showRecruiterBid = "a.showRecruiterBid"
  $(document).on
    click: (event) ->
      event.preventDefault()
    mouseenter: (event) ->
      $link = $(event.target)
      $link.popover(
        placement: "bottom"
        html: true
        content: ""
        template: '<div class="popover" onmouseover="refreshTimeout(this);"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
        trigger: "manual"
      )
      $popover = $link.data("bs.popover")
      requestData = "bidId=" + $link.attr("data-id")
      request = $.ajax(
        url: "recruiter-bid-show.json"
        type: "GET"
        async: false
        data: requestData
        dataType: "json"
      )
      request.done (data) ->
        bid = data.entry.value
        ajaxData = "<dl class=\"dl popup\">"
        if (bid.deal?)
          ajaxData += "<dt>" + bid.headDeal + "</dt><dd><a href=\"" + bid.dealUrl + "\">" + bid.deal + "</a></dd>"
        ajaxData += "<dt>" + bid.headVacancy + "</dt><dd>" + bid.vacancy + "</dd><dt>" + bid.headSalary + "</dt><dd>" + bid.salary + "</dd><dt>" + bid.headCreated + "</dt><dd>" + bid.created + "</dd><dt>" + bid.headUpdated + "</dt><dd>" + bid.updated + "</dd><dt>" + bid.headExpiration + "</dt><dd>" + bid.expiration + "</dd><dt>" + bid.headStatus + "</dt><dd>" + bid.status + "</dd><dt>" + bid.headDescription + "</dt><dd>" + bid.description + "</dd>"
        ajaxTitle = bid.popupTitle
        return

      request.fail ->

      $popover.options.content = ajaxData
      $popover.options.title = ajaxTitle
      $popover.setContent()
      $popover.show()

    mouseleave: (event) ->
      $link = $(event.target)
      $popover = $link.data("bs.popover")
      window.timeoutObj = setTimeout(->
        $popover.hide()
        return
      , 350)
      return
  , showRecruiterBid


window.timeoutObj = undefined
window.refreshTimeout = (obj) ->
  clearTimeout(window.timeoutObj);
  $(obj).mouseleave ->
    $(obj).hide()
  return
