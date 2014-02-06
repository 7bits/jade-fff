$ ->
  sorting = "none"

  updateFilter = (newSorting) ->
    sorting = newSorting
    inputSorting = $("form#vacanciesFilter input#sortingOrder")
    inputSorting.val sorting
    inputSorting.trigger "change"
    return

  updateTable = ->
    $("#vacancies th.sorting_asc").addClass "sorting"
    $("#vacancies th.sorting_asc").removeClass "sorting_asc"
    $("#vacancies th.sorting_desc").addClass "sorting"
    $("#vacancies th.sorting_desc").removeClass "sorting_desc"
    switch sorting
      when "title_asc"
        $("#vacancies th#stitle").removeClass "sorting"
        $("#vacancies th#stitle").addClass "sorting_asc"
      when "title_desc"
        $("#vacancies th#stitle").removeClass "sorting"
        $("#vacancies th#stitle").addClass "sorting_desc"
      when "desc_asc"
        $("#vacancies th#sdesc").removeClass "sorting"
        $("#vacancies th#sdesc").addClass "sorting_asc"
      when "desc_desc"
        $("#vacancies th#sdesc").removeClass "sorting"
        $("#vacancies th#sdesc").addClass "sorting_desc"
      when "date_asc"
        $("#vacancies th#sdate").removeClass "sorting"
        $("#vacancies th#sdate").addClass "sorting_asc"
      when "date_desc"
        $("#vacancies th#sdate").removeClass "sorting"
        $("#vacancies th#sdate").addClass "sorting_desc"
      when "type_asc"
        $("#vacancies th#stype").removeClass "sorting"
        $("#vacancies th#stype").addClass "sorting_asc"
      when "type_desc"
        $("#vacancies th#stype").removeClass "sorting"
        $("#vacancies th#stype").addClass "sorting_desc"

  $("#vacancies th#stitle").on "click", ->
    unless sorting is "title_asc"
      updateFilter "title_asc"
      updateTable()
    else
      updateFilter "title_desc"
      updateTable()
    return

  $("#vacancies th#sdesc").on "click", ->
    unless sorting is "desc_asc"
      updateFilter "desc_asc"
      updateTable()
    else
      updateFilter "desc_desc"
      updateTable()
    return

  $("#vacancies th#sdate").on "click", ->
    unless sorting is "date_asc"
      updateFilter "date_asc"
      updateTable()
    else
      updateFilter "date_desc"
      updateTable()
    return

  $("#vacancies th#stype").on "click", ->
    unless sorting is "type_asc"
      updateFilter "type_asc"
      updateTable()
    else
      updateFilter "type_desc"
      updateTable()
    return

  return
