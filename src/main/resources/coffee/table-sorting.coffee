$ ->
  sortColumn = ""
  sortAsc = 1

  updateFilter = (newSortColumn, newSortAsc) ->
    sortColumn = newSortColumn
    sortAsc = newSortAsc
    inputSortColumn = $("form.form-sort input#sortColumn")
    inputSortColumn.val sortColumn
    inputSortAsc = $("form.form-sort input#sortAsc")
    inputSortAsc.val sortAsc
    inputSortColumn.trigger "change"
    return

  updateTable = ->
    $("#table-sort th.sorting_asc").addClass "sorting"
    $("#table-sort th.sorting_asc").removeClass "sorting_asc"
    $("#table-sort th.sorting_desc").addClass "sorting"
    $("#table-sort th.sorting_desc").removeClass "sorting_desc"
    activeColumn = $("#table-sort th#s" + sortColumn)
    if sortAsc is 1
      activeColumn.removeClass "sorting"
      activeColumn.addClass "sorting_asc"
    if sortAsc is 0
      activeColumn.removeClass "sorting"
      activeColumn.addClass "sorting_desc"

  $("#table-sort th").on "click",(e) ->
    sortAttr = $(e.target).attr("data-id")
    if sortAttr?
      if sortColumn is sortAttr and sortAsc is 1
        updateFilter sortAttr, 0
        updateTable()
      else
        updateFilter sortAttr, 1
        updateTable()

  return

