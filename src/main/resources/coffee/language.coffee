$ ->
  $("#lang").on "change", ->
    $(location).attr "href", @value
