

initializeSelectedRoom = ->
  selectedRoom = $('li.selected .room-wrap')
  updatePriceRoomTypeAndServiceId(selectedRoom)

initializeInTotal = ->
  updateInTotal()

updatePriceRoomTypeAndServiceId = (selectedRoom) ->
  price = selectedRoom.attr('price')
  roomType = selectedRoom.find('.room-type').html()
  serviceId = selectedRoom.parents('li').first().attr('service_id')
  li = selectedRoom.parents('li.residence-info').first()
  if $('#residence-cb').prop('checked')
    $('.residence-info .price').removeClass('unchecked')
    li.attr('service_id', serviceId)
    li.attr('price', price)
  else
    $('.residence-info .price').addClass('unchecked')
    li.removeAttr('service_id')
    li.removeAttr('price')
  doubleString = ""
  if $('#plus_one').prop('checked')
    doubleString = "2 x "
  $('.residence-info .price').html(doubleString + price + ' Р.')
  $('.residence-info .residence-type').html(roomType)

collectSelectedServices = ->
  service_ids = []
  $('ul.payments-items li.paid-thing').each (index, value) ->
    input = $(value).find('input')
    if input.prop('checked')
      service_ids.push($(value).attr('service_id'))
  service_ids

getDiscount = -> parseFloat($('#promo').attr('discount'))
setDiscount = (discount) -> $('#promo').attr('discount', discount)

inTotal = ->
  sum = 0
  discount = 0
  if (getDiscount())
    discount = getDiscount()
  unless discount == 1
    $('ul.payments-items li.paid-thing').each (index, value) ->
      input = $(value).find('input')
      if input.prop('checked')
        price = parseInt($(value).attr('price'))
        service_id = $(@).attr('service_id')
        if service_id == "3" || service_id == "2" || service_id == "8"
          price *= (1 - discount)
        if (service_id == "5" || service_id == "6" || service_id == "10") && $('#plus_one').prop('checked')
          price *= 2
        sum += price
  Math.floor(sum)

updateInTotal = ->
  $('.in-total span').html(inTotal() + ' Р.')

sendGroupHash = (group_hash) ->
  $.ajax
    type: 'GET'
    url: 'group_hash'
    data: { group_hash: group_hash }
    dataType: 'json'
    success: ->
      $('.js-group-hash-btn').hide()

ofertaFire = ->
  if !$('.js-oferta').is(':checked')
    $('.oferta').addClass('achtung')

$ ->
  $('.js-oferta').click ->
    updateInTotal()
    if $('.js-oferta').is(':checked')
      $('.oferta').removeClass('achtung')

  $('.js-student-payment-btn').click ->
    if $('.js-oferta').is(':checked') || $('.js-oferta').length == 0
      $(@).attr('disabled', 'disabled')
      $('.js-payment-spinner').show()
      $.ajax(
        type: 'POST'
        url: 'payment/student-robokassa-button'
        async: false
        success: (data) ->
          if data.success
            if data.paid
              window.location.reload()
            else
              $('#hidden-payment-btn-block').html(data.button)
              $('#hidden-payment-btn').trigger('click')
          else
            $('.js-payment-btn').removeAttr('disabled')
            $('.js-payment-spinner').hide()
            $('.error').html(data.errorMessage)
        dataType: 'json'
      )
    else
      ofertaFire()

  $('.js-payment-btn').click ->
    if $('.js-oferta').is(':checked') || $('.js-oferta').length == 0
      if ($('ul.payments-items li.paid-thing input:checked').size() == 0)

      else
        $(@).attr('disabled', 'disabled')
        $('.js-payment-spinner').show()
        params = { service_ids: collectSelectedServices(), promocode: $('#promo').val(), plus_one: $('#plus_one').prop('checked') }
        unless typeof yaCounter17140027 is 'undefined'
          yaCounter17140027.reachGoal('click_robokassa', window.yaParams || {});
        # groupHashInput = $('#group_hash')
        # params['group_hash'] = groupHashInput.val() if groupHashInput

        if $('.legal-entity').find('input[name="pay"]').is(':checked')
          $.ajax(
            type: 'POST'
            url: 'requisites/prepare'
            async: false
            data: params
            success: (data) ->
              if data.success
                if data.paid
                  window.location.reload()
                else
                  window.location.href = 'requisites/' + data.invoice_id
              else
                $('.error').html(data.errorMessage)
            dataType: 'json'
          )
          false
        else
          $.ajax(
            type: 'POST'
            url: 'payment/robokassa-button'
            async: false
            data: params
            success: (data) ->
              if data.success
                if data.paid
                  window.location.reload()
                else
                  $('#hidden-payment-btn-block').html(data.button)
                  $('#hidden-payment-btn').trigger('click')
              else
                $('.js-payment-btn').removeAttr('disabled')
                $('.js-payment-spinner').hide()
                $('.error').html(data.errorMessage)
            dataType: 'json'
          )
          false
    else
      ofertaFire()

  $('.room-wrap').click ->
    if !$(@).hasClass('disabled')
      $('#residence-cb').prop('checked', true)
      $('#residence-cb').trigger('change')
      $('.residence-info').find('li').removeClass('selected')
      li = $(@).parents('li').first()
      li.addClass('selected')
      updatePriceRoomTypeAndServiceId($(@))
    updateInTotal()

  $('#residence-cb').change ->
    selectedRoom = $('li.selected .room-wrap')
    updatePriceRoomTypeAndServiceId(selectedRoom)


  $('ul.payments-items input').change ->
    updateInTotal()
    if ($(@).prop('checked'))
      $(@).parents('li').find('.price').removeClass('unchecked')
    else
      $(@).parents('li').find('.price').addClass('unchecked')

  $('#plus_one').change ->
    updateInTotal()
    selectedRoom = $('li.selected .room-wrap')
    updatePriceRoomTypeAndServiceId(selectedRoom)

  stoppedTyping = false

  $(".popup-friends").hide()
  $(".answer").click (e)->
    _top = $(".answer").offset().top
    _left = $(".answer").offset().left
    $(".popup-friends").toggle()
    $(".popup-friends").offset({top: _top - 170, left:_left - $(".popup-friends").width() + 225})

  $(".popup-friends").click ->
    $(".popup-friends").offset({top: 0, left: 0})
    $(".popup-friends").hide()

  $('.js-group-hash').keypress (e)->
    if e.which == 13
      sendGroupHash($('.js-group-hash').val())

  $('.js-group-hash-btn').on click: ->
    sendGroupHash($('.js-group-hash').val())

  $('.js-after-party-cb').change ->
    if $(@).prop('checked')
      residence = $('.js-residence-cb')[0]
      if residence && !$(residence).prop('checked')
        $('.js-after-party-hint').show()
      else
        $('.js-after-party-hint').hide()
    else
      $('.js-after-party-hint').hide()

  $('.js-residence-cb').change ->
    afterPartyCb = $('.js-after-party-cb')[0]
    if afterPartyCb
      if !$(@).prop('checked') && $(afterPartyCb).prop('checked')
        $('.js-after-party-hint').show()
      else
        $('.js-after-party-hint').hide()


  $('#promo').on('input', ->
    clearTimeout(stoppedTyping) if stoppedTyping
    stoppedTyping = setTimeout(
      ->
        $.ajax(
          type: 'POST'
          url: 'promocode'
          async: false
          data: { promocode: $('#promo').val() }
          success: (data) ->
            setDiscount(data.discount)
          dataType: 'json'
        )
        updateInTotal()
      1000
    )
  )
  initializeSelectedRoom()
  initializeInTotal()


