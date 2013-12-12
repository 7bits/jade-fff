(function() {
  var collectSelectedServices, getDiscount, inTotal, initializeInTotal, initializeSelectedRoom, ofertaFire, sendGroupHash, setDiscount, updateInTotal, updatePriceRoomTypeAndServiceId;

  initializeSelectedRoom = function() {
    var selectedRoom;
    selectedRoom = $('li.selected .room-wrap');
    return updatePriceRoomTypeAndServiceId(selectedRoom);
  };

  initializeInTotal = function() {
    return updateInTotal();
  };

  updatePriceRoomTypeAndServiceId = function(selectedRoom) {
    var doubleString, li, price, roomType, serviceId;
    price = selectedRoom.attr('price');
    roomType = selectedRoom.find('.room-type').html();
    serviceId = selectedRoom.parents('li').first().attr('service_id');
    li = selectedRoom.parents('li.residence-info').first();
    if ($('#residence-cb').prop('checked')) {
      $('.residence-info .price').removeClass('unchecked');
      li.attr('service_id', serviceId);
      li.attr('price', price);
    } else {
      $('.residence-info .price').addClass('unchecked');
      li.removeAttr('service_id');
      li.removeAttr('price');
    }
    doubleString = "";
    if ($('#plus_one').prop('checked')) {
      doubleString = "2 x ";
    }
    $('.residence-info .price').html(doubleString + price + ' Р.');
    return $('.residence-info .residence-type').html(roomType);
  };

  collectSelectedServices = function() {
    var service_ids;
    service_ids = [];
    $('ul.payments-items li.paid-thing').each(function(index, value) {
      var input;
      input = $(value).find('input');
      if (input.prop('checked')) {
        return service_ids.push($(value).attr('service_id'));
      }
    });
    return service_ids;
  };

  getDiscount = function() {
    return parseFloat($('#promo').attr('discount'));
  };

  setDiscount = function(discount) {
    return $('#promo').attr('discount', discount);
  };

  inTotal = function() {
    var discount, sum;
    sum = 0;
    discount = 0;
    if (getDiscount()) {
      discount = getDiscount();
    }
    if (discount !== 1) {
      $('ul.payments-items li.paid-thing').each(function(index, value) {
        var input, price, service_id;
        input = $(value).find('input');
        if (input.prop('checked')) {
          price = parseInt($(value).attr('price'));
          service_id = $(this).attr('service_id');
          if (service_id === "3" || service_id === "2" || service_id === "8") {
            price *= 1 - discount;
          }
          if ((service_id === "5" || service_id === "6" || service_id === "10") && $('#plus_one').prop('checked')) {
            price *= 2;
          }
          return sum += price;
        }
      });
    }
    return Math.floor(sum);
  };

  updateInTotal = function() {
    return $('.in-total span').html(inTotal() + ' Р.');
  };

  sendGroupHash = function(group_hash) {
    return $.ajax({
      type: 'GET',
      url: 'group_hash',
      data: {
        group_hash: group_hash
      },
      dataType: 'json',
      success: function() {
        return $('.js-group-hash-btn').hide();
      }
    });
  };

  ofertaFire = function() {
    if (!$('.js-oferta').is(':checked')) {
      return $('.oferta').addClass('achtung');
    }
  };

  $(function() {
    var stoppedTyping;
    $('.js-oferta').click(function() {
      updateInTotal();
      if ($('.js-oferta').is(':checked')) {
        return $('.oferta').removeClass('achtung');
      }
    });
    $('.js-student-payment-btn').click(function() {
      if ($('.js-oferta').is(':checked') || $('.js-oferta').length === 0) {
        $(this).attr('disabled', 'disabled');
        $('.js-payment-spinner').show();
        return $.ajax({
          type: 'POST',
          url: 'payment/student-robokassa-button',
          async: false,
          success: function(data) {
            if (data.success) {
              if (data.paid) {
                return window.location.reload();
              } else {
                $('#hidden-payment-btn-block').html(data.button);
                return $('#hidden-payment-btn').trigger('click');
              }
            } else {
              $('.js-payment-btn').removeAttr('disabled');
              $('.js-payment-spinner').hide();
              return $('.error').html(data.errorMessage);
            }
          },
          dataType: 'json'
        });
      } else {
        return ofertaFire();
      }
    });
    $('.js-payment-btn').click(function() {
      var params;
      if ($('.js-oferta').is(':checked') || $('.js-oferta').length === 0) {
        if ($('ul.payments-items li.paid-thing input:checked').size() === 0) {

        } else {
          $(this).attr('disabled', 'disabled');
          $('.js-payment-spinner').show();
          params = {
            service_ids: collectSelectedServices(),
            promocode: $('#promo').val(),
            plus_one: $('#plus_one').prop('checked')
          };
          if (typeof yaCounter17140027 !== 'undefined') {
            yaCounter17140027.reachGoal('click_robokassa', window.yaParams || {});
          }
          if ($('.legal-entity').find('input[name="pay"]').is(':checked')) {
            $.ajax({
              type: 'POST',
              url: 'requisites/prepare',
              async: false,
              data: params,
              success: function(data) {
                if (data.success) {
                  if (data.paid) {
                    return window.location.reload();
                  } else {
                    return window.location.href = 'requisites/' + data.invoice_id;
                  }
                } else {
                  return $('.error').html(data.errorMessage);
                }
              },
              dataType: 'json'
            });
            return false;
          } else {
            $.ajax({
              type: 'POST',
              url: 'payment/robokassa-button',
              async: false,
              data: params,
              success: function(data) {
                if (data.success) {
                  if (data.paid) {
                    return window.location.reload();
                  } else {
                    $('#hidden-payment-btn-block').html(data.button);
                    return $('#hidden-payment-btn').trigger('click');
                  }
                } else {
                  $('.js-payment-btn').removeAttr('disabled');
                  $('.js-payment-spinner').hide();
                  return $('.error').html(data.errorMessage);
                }
              },
              dataType: 'json'
            });
            return false;
          }
        }
      } else {
        return ofertaFire();
      }
    });
    $('.room-wrap').click(function() {
      var li;
      if (!$(this).hasClass('disabled')) {
        $('#residence-cb').prop('checked', true);
        $('#residence-cb').trigger('change');
        $('.residence-info').find('li').removeClass('selected');
        li = $(this).parents('li').first();
        li.addClass('selected');
        updatePriceRoomTypeAndServiceId($(this));
      }
      return updateInTotal();
    });
    $('#residence-cb').change(function() {
      var selectedRoom;
      selectedRoom = $('li.selected .room-wrap');
      return updatePriceRoomTypeAndServiceId(selectedRoom);
    });
    $('ul.payments-items input').change(function() {
      updateInTotal();
      if ($(this).prop('checked')) {
        return $(this).parents('li').find('.price').removeClass('unchecked');
      } else {
        return $(this).parents('li').find('.price').addClass('unchecked');
      }
    });
    $('#plus_one').change(function() {
      var selectedRoom;
      updateInTotal();
      selectedRoom = $('li.selected .room-wrap');
      return updatePriceRoomTypeAndServiceId(selectedRoom);
    });
    stoppedTyping = false;
    $(".popup-friends").hide();
    $(".answer").click(function(e) {
      var _left, _top;
      _top = $(".answer").offset().top;
      _left = $(".answer").offset().left;
      $(".popup-friends").toggle();
      return $(".popup-friends").offset({
        top: _top - 170,
        left: _left - $(".popup-friends").width() + 225
      });
    });
    $(".popup-friends").click(function() {
      $(".popup-friends").offset({
        top: 0,
        left: 0
      });
      return $(".popup-friends").hide();
    });
    $('.js-group-hash').keypress(function(e) {
      if (e.which === 13) {
        return sendGroupHash($('.js-group-hash').val());
      }
    });
    $('.js-group-hash-btn').on({
      click: function() {
        return sendGroupHash($('.js-group-hash').val());
      }
    });
    $('.js-after-party-cb').change(function() {
      var residence;
      if ($(this).prop('checked')) {
        residence = $('.js-residence-cb')[0];
        if (residence && !$(residence).prop('checked')) {
          return $('.js-after-party-hint').show();
        } else {
          return $('.js-after-party-hint').hide();
        }
      } else {
        return $('.js-after-party-hint').hide();
      }
    });
    $('.js-residence-cb').change(function() {
      var afterPartyCb;
      afterPartyCb = $('.js-after-party-cb')[0];
      if (afterPartyCb) {
        if (!$(this).prop('checked') && $(afterPartyCb).prop('checked')) {
          return $('.js-after-party-hint').show();
        } else {
          return $('.js-after-party-hint').hide();
        }
      }
    });
    $('#promo').on('input', function() {
      if (stoppedTyping) {
        clearTimeout(stoppedTyping);
      }
      return stoppedTyping = setTimeout(function() {
        $.ajax({
          type: 'POST',
          url: 'promocode',
          async: false,
          data: {
            promocode: $('#promo').val()
          },
          success: function(data) {
            return setDiscount(data.discount);
          },
          dataType: 'json'
        });
        return updateInTotal();
      }, 1000);
    });
    initializeSelectedRoom();
    return initializeInTotal();
  });

}).call(this);
