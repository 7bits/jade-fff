$ ->
  $(document).on "mouseover", "div.popover", (event) ->
    clearTimeout(window.timeoutObj);
    popover = $(this)
    popover.mouseleave ->
      window.timeoutObj = setTimeout(->
        popover.hide()
      , 350)
