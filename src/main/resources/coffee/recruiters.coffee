$ ->
  prevDate = new Date()
  nextDate = new Date()
  showAll = "form.form-sort label#showAll"
  hideButtons = "form.form-sort label[id^=hide]"

  showAllCheck = (button) ->
    showAllState = true
    if not $(hideButtons).hasClass("active") and button isnt ""
      showAllState = false
    else
      $(hideButtons).each ->
        currentActive = $(this).hasClass("active")
        currentId = $(this).attr("id")
        showAllState = false  if currentActive and (currentId isnt button)
        return

    $(showAll).addClass "active"  if showAllState
    return

  modifyDates = (inputDate) ->
    curDate = new Date(inputDate)
    nextDate = new Date(curDate.getTime())
    prevDate = new Date(curDate.getTime())
    prevDate.setDate prevDate.getDate() - 1
    nextDate.setDate nextDate.getDate() + 1

  dp = $(".datepicker")
  $("#dateicon").on "click", ->
    dp.datepicker "show"
    return
  dp.datepicker().on "changeDate", (ev) ->
    dp.val(ev.target.value).trigger "change"
    dp.datepicker "hide"
    modifyDates ev.date.valueOf()
    return

  $(showAll).on "click", ->
    $(hideButtons).removeClass("active")
    $(hideButtons).find("input").prop("checked", false)

  $(hideButtons).on "click", (e) ->
    hideButton = $(e.target).attr("id")
    $(showAll).removeClass "active"
    $(showAll).find("input").prop "checked", false
    showAllCheck hideButton
    return

  firstVisit = ->
    $(showAll).find("input").trigger "change"
    $(hideButtons).each ->
      currentState = $(this).find("input").prop("checked")
      $(this).addClass "active"  if currentState
      return

    showAllCheck ""
    modifyDates new Date($(".datepicker").val())
    return

  setTimeout firstVisit, 100

  $("#prevDateLink").on "click", (event) ->
    event.preventDefault()
    $(".datepicker").datepicker("update", prevDate)
    modifyDates(prevDate)

  $("#nextDateLink").on "click", (event) ->
    event.preventDefault()
    $(".datepicker").datepicker("update", nextDate)
    modifyDates(nextDate)

  $("#recruiterVacanciesFilter :input").on "change", ->
    data = $("#recruiterVacanciesFilter").serialize()
    request = $.ajax(
      url: "recruiter-vacancies-filter-ajax.json"
      type: "POST"
      data: data
      dataType: "json"
    )
    request.done (data) ->
      addHtml = ""
      vacancyList = data.entry.value
      i = 0

      while i < vacancyList.length
        obj = vacancyList[i]
        addHtml += "<tr><td><a href=\"" + obj.url + "\">" + obj.title + "</a></td><td>" + obj.description + "</td><td>" + obj.created + "</td><td>" + obj.status + "</td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->

  $("#employerDealsFilter :input").on "change", ->
    data = $("#employerDealsFilter").serialize()
    request = $.ajax(
      url: "employer-deals-filter-ajax.json"
      type: "POST"
      data: data
      dataType: "json"
    )
    request.done (data) ->
      addHtml = ""
      vacancyList = data.entry.value
      i = 0

      while i < vacancyList.length
        obj = vacancyList[i]
        applicants = "<span class=\"green\">" + obj.unseenApplicantCount + "</span>/<span>" + obj.allApplicantCount + " (</span><span class=\"red\">" + obj.rejectedApplicantCount + "</span>/<span class=\"yellow\">" + obj.viewedApplicantCount + "</span>)"
        addHtml += "<tr><td class='help' title='" + obj.description + "'>" + obj.title + "</td><td>" + obj.created  + "</td><td>" + obj.updated + "</td><td>" + obj.status + "</td><td>" + obj.bids + "</td><td class='help' title='" + obj.terms + "'><a href=\"" + obj.recruiterUrl + "\">" + obj.recruiter + "</a></td><td class='help' title='" + obj.applicantsTooltip + "'>" + applicants + "</td><td>" + "<a href='" + obj.url + "'>" + obj.urlText + "</a>" + "</td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->

  $("#employerVacanciesFilter :input").on "change", ->
    data = $("#employerVacanciesFilter").serialize()
    request = $.ajax(
      url: "employer-vacancies-filter-ajax.json"
      type: "POST"
      data: data
      dataType: "json"
    )
    request.done (data) ->
      addHtml = ""
      vacancyList = data.entry.value
      i = 0

      while i < vacancyList.length
        obj = vacancyList[i]
        bids = "<span class=\"green\">" + obj.unseenBidCount + "</span>/<span>" + obj.allBidCount + " (</span><span class=\"red\">" + obj.rejectedBidCount + "</span>/<span class=\"yellow\">" + obj.viewedBidCount + "</span>)"
        addHtml += "<tr><td class='help' title='" + obj.description + "'>" + obj.title + "</td><td>" + obj.created  + "</td><td>" + obj.updated + "</td><td>" + obj.status + "</td><td class='help' title='" + obj.bidsTooltip + "'>" + bids + "</td><td>" + "<a href='" + obj.url + "'>" + obj.urlText + "</a>" + "</td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->


  $("#recruiterBidsFilter :input").on "change", ->
    data = $("#recruiterBidsFilter").serialize()
    request = $.ajax(
      url: "recruiter-bids-filter-ajax.json"
      type: "POST"
      data: data
      dataType: "json"
    )
    request.done (data) ->
      addHtml = ""
      bidList = data.entry.value
      i = 0

      while i < bidList.length
        obj = bidList[i]
        addHtml += "<tr><td><a href=\"#\" data-id=\"" + obj.bidId + "\" class=\"help\" recruiter-bid=\"" + obj.bidId + "\">" + obj.title + "</a></td><td>" + obj.description  + "</td><td>" + obj.created + "</td><td>" + obj.status + "</td><td><a href=\"" + obj.employerUrl + "\">" + obj.employer + "</a></td><td>" + obj.viewed + "</td><td><div class=\"btn-group btn-group-xs\">"
        if obj.withdraw?
          addHtml += "<button class=\"btn btn-success bidWithdraw\" data-id=\"" + obj.bidId + "\">" + obj.withdraw + "</button>"
        if obj.withdrawn?
          addHtml += "<button class=\"btn btn-success bidWithdraw\" data-id=\"" + obj.bidId + "\" disabled>" + obj.withdrawn + "</button>"
        addHtml += "</div></td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->

  $("#recruiterDealsFilter :input").on "change", ->
    data = $("#recruiterDealsFilter").serialize()
    request = $.ajax(
      url: "recruiter-deals-filter-ajax.json"
      type: "POST"
      data: data
      dataType: "json"
    )
    request.done (data) ->
      addHtml = ""
      dealsList = data.entry.value
      i = 0

      while i < dealsList.length
        obj = dealsList[i]
        applicants = "<span class=\"green\">" + obj.unseenApplicantCount + "</span>/<span>" + obj.allApplicantCount + " (</span><span class=\"red\">" + obj.rejectedApplicantCount + "</span>/<span class=\"yellow\">" + obj.viewedApplicantCount + "</span>)"
        addHtml += "<tr><td><a href=\"" + obj.dealUrl + "\">" + obj.title + "</a></td><td>" + obj.description  + "</td><td class=\"help\" title=\"" + obj.applicantsTooltip + "\">" + applicants + "</td><td>" + obj.status + "</td><td>" + obj.created + "</td><td>" + obj.updated + "</td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->