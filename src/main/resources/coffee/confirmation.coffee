$ ->
  $('[data-toggle="confirmation"]').confirmation({
      title: 'Вы уверены?'
      btnOkLabel: '<i class="icon-ok-sign icon-white"></i> Да'
      btnCancelLabel: '<i class="icon-remove-sign"></i> Нет'
      onConfirm: ()->
        window.location = '/recruiters'
  })