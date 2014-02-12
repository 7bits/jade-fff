prevDate = new Date()
nextDate = new Date()
showAll = "form.form-sort label#showAll"
hideButtons = "form.form-sort label[id^=hide]"
$ ->
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
  return

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

dateToString = (inputDate) ->
  day = undefined
  month = undefined
  year = undefined
  year = inputDate.getFullYear()
  month = inputDate.getMonth() + 1
  day = inputDate.getDate()
  stringDate = year + "-"
  stringDate += "0"  if month < 10
  stringDate += month + "-"
  stringDate += "0"  if day < 10
  stringDate += day
  stringDate

$ ->
  dp = $(".datepicker")
  $("#dateicon").on "click", ->
    dp.datepicker "show"
    return
  dp.datepicker().on "changeDate", (ev) ->
    dp.val(ev.target.value).trigger "change"
    dp.datepicker "hide"
    modifyDates ev.date.valueOf()
    return

$ ->
  $(showAll).on "click", ->
    $(hideButtons).removeClass("active")
    $(hideButtons).find("input").prop("checked", false)

$ ->
  $(hideButtons).on "click", (e) ->
    hideButton = $(e.target).attr("id")
    $(showAll).removeClass "active"
    $(showAll).find("input").prop "checked", false
    showAllCheck hideButton
    return

$ ->
  $("#prevDateLink").on "click", (event) ->
    event.preventDefault()
    $(".datepicker").datepicker("update", dateToString(prevDate))
    modifyDates(prevDate)

$ ->
  $("#nextDateLink").on "click", (event) ->
    event.preventDefault()
    $(".datepicker").datepicker("update", dateToString(nextDate))
    modifyDates(nextDate)

$ ->
  $("#vacanciesFilter :input").on "change", ->
    data = $("#vacanciesFilter").serialize()
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
        addHtml += "<tr><td>" + obj.title + "</td><td>" + obj.description + "</td><td>" + obj.created + "</td><td>" + obj.status + "</td><td>" + "<a href='" + obj.url + "'>" + obj.urltext + "</a>" + "</td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->

$ ->
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
        addHtml += "<tr><td class='help' title='" + obj.description + "'>" + obj.title + "</td><td>" + obj.created  + "</td><td>" + obj.updated + "</td><td>" + obj.status + "</td><td>" + obj.bids + "</td><td class='help' title='" + obj.terms + "'><a href=\"" + obj.recruiterUrl + "\">" + obj.recruiter + "</a></td><td class='help' title='" + obj.applicantsTooltip + "'>" + obj.applicants + "</td><td>" + "<a href='" + obj.url + "'>" + obj.urltext + "</a>" + "</td></tr>"
        i++
      $("#table-sort").find("tbody").html addHtml
      return

    request.fail ->

