$ ->
  sortColumn = ""
  sortAsc = 1

  updateFilter = (newSortColumn, newSortAsc) ->
    sortColumn = newSortColumn
    sortAsc = newSortAsc
    inputSortColumn = $("form#vacanciesFilter input#sortColumn")
    inputSortColumn.val sortColumn
    inputSortAsc = $("form#vacanciesFilter input#sortAsc")
    inputSortAsc.val sortAsc
    inputSortColumn.trigger "change"
    return

  updateTable = ->
    $("#vacancies th.sorting_asc").addClass "sorting"
    $("#vacancies th.sorting_asc").removeClass "sorting_asc"
    $("#vacancies th.sorting_desc").addClass "sorting"
    $("#vacancies th.sorting_desc").removeClass "sorting_desc"
    if sortColumn is "title" and sortAsc is 1
      $("#vacancies th#stitle").removeClass "sorting"
      return $("#vacancies th#stitle").addClass("sorting_asc")
    if sortColumn is "title" and sortAsc is 0
      $("#vacancies th#stitle").removeClass "sorting"
      return $("#vacancies th#stitle").addClass("sorting_desc")
    if sortColumn is "description" and sortAsc is 1
      $("#vacancies th#sdesc").removeClass "sorting"
      return $("#vacancies th#sdesc").addClass("sorting_asc")
    if sortColumn is "description" and sortAsc is 0
      $("#vacancies th#sdesc").removeClass "sorting"
      return $("#vacancies th#sdesc").addClass("sorting_desc")
    if sortColumn is "creation_date" and sortAsc is 1
      $("#vacancies th#sdate").removeClass "sorting"
      return $("#vacancies th#sdate").addClass("sorting_asc")
    if sortColumn is "creation_date" and sortAsc is 0
      $("#vacancies th#sdate").removeClass "sorting"
      return $("#vacancies th#sdate").addClass("sorting_desc")
    if sortColumn is "type" and sortAsc is 1
      $("#vacancies th#stype").removeClass "sorting"
      return $("#vacancies th#stype").addClass("sorting_asc")
    if sortColumn is "type" and sortAsc is 0
      $("#vacancies th#stype").removeClass "sorting"
      $("#vacancies th#stype").addClass "sorting_desc"

  $("#vacancies th#stitle").on "click", ->
    if sortColumn is "title" and sortAsc is 1
      updateFilter "title", 0
      updateTable()
    else
      updateFilter "title", 1
      updateTable()
    return

  $("#vacancies th#sdesc").on "click", ->
    if sortColumn is "description" and sortAsc is 1
      updateFilter "description", 0
      updateTable()
    else
      updateFilter "description", 1
      updateTable()
    return

  $("#vacancies th#sdate").on "click", ->
    if sortColumn is "creation_date" and sortAsc is 1
      updateFilter "creation_date", 0
      updateTable()
    else
      updateFilter "creation_date", 1
      updateTable()
    return

  $("#vacancies th#stype").on "click", ->
    if sortColumn is "type" and sortAsc is 1
      updateFilter "type", 0
      updateTable()
    else
      updateFilter "type", 1
      updateTable()
    return

  return

