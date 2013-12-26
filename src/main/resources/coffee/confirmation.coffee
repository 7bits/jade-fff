$ ->
  $('[data-toggle="confirmation"]').confirmation({
      title: 'Вы уверены?'
      btnOkLabel: '<i class="icon-ok-sign icon-white"></i> Да'
      btnCancelLabel: '<i class="icon-remove-sign"></i> Нет'
      onConfirm: ()->
        url = $(this).attr('href')
        $.ajax
          url: url
          type: "post"
          data: {'message': $('.js-message-for-bid').val()}
          success: ->
            alert "success"
            window.location = '/recruiters'

          error: ->
            alert "failure"

  })