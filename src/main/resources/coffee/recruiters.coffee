prevDate = new Date()
nextDate = new Date()
$ ->
  firstVisit = ->
    $("#searchText").trigger "change"
    hideVacancies = $("#hideVacancies").find("input").prop("checked")
    $("#hideVacancies").addClass "active"  if hideVacancies
    hideBids = $("#hideBids").find("input").prop("checked")
    $("#hideBids").addClass "active"  if hideBids
    hideDeals = $("#hideDeals").find("input").prop("checked")
    $("#hideDeals").addClass "active"  if hideDeals
    showAllCheck ""
    modifyDates new Date($(".datepicker").val())
    return

  setTimeout firstVisit, 100
  return

showAllCheck = (button) ->
  hideBids = undefined
  hideDeals = undefined
  hideVacancies = undefined
  showAllActive = undefined
  hideVacancies = $("#hideVacancies").hasClass("active")
  hideBids = $("#hideBids").hasClass("active")
  hideDeals = $("#hideDeals").hasClass("active")
  showAllActive = $("#showAll").hasClass("active")
  hideVacancies = not hideVacancies  if button is "hideVacancies"
  hideBids = not hideBids  if button is "hideBids"
  hideDeals = not hideDeals  if button is "hideDeals"
  $("#showAll").addClass "active"  if not hideVacancies and not hideBids and not hideDeals and not showAllActive
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
  $("#showAll").on "click", ->
    $("#hideVacancies").removeClass("active")
    $("#hideVacancies").find("input").prop("checked", false)
    $("#hideBids").removeClass("active")
    $("#hideBids").find("input").prop("checked", false)
    $("#hideDeals").removeClass("active")
    $("#hideDeals").find("input").prop("checked", false)

$ ->
  $("#hideVacancies").on "click", ->
    $("#showAll").removeClass("active")
    $("#showAll").find("input").prop("checked", false)
    showAllCheck "hideVacancies"

$ ->
  $("#hideBids").on "click", ->
    $("#showAll").removeClass("active")
    $("#showAll").find("input").prop("checked", false)
    showAllCheck "hideBids"

$ ->
  $("#hideDeals").on "click", ->
    $("#showAll").removeClass("active")
    $("#showAll").find("input").prop("checked", false)
    showAllCheck "hideDeals"

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
      $("#vacancies").find("tbody").html addHtml
      return

    request.fail ->

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

