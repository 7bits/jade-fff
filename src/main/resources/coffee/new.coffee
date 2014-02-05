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

$ ->
  dp = $(".datepicker")
  dp.datepicker().on "changeDate", (ev) ->
    dp.val(ev.target.value).trigger "change"
    dp.datepicker "hide"

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
    $(".datepicker").val($("#prevDate").val()).trigger "change"

$ ->
  $("a#nextDateLink").on "click", (event) ->
    event.preventDefault()
    $(".datepicker").val($("#nextDate").val()).trigger "change"

