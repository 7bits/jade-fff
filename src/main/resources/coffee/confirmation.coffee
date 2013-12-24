$ ->
  $('[data-toggle="confirmation"]').confirmation({
      onConfirm: ()->
        window.location = '/recruiters'
  })