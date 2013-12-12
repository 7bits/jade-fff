$ ->
  $(document).ready ->

    containerMargin = ->
      margin = $('.inside-container').css('marginTop')
      if margin?
        parseInt(margin)
      else
        0

    #    $('#banner.carousel').carousel('cycle')
    $(".subscribe-form-input").keypress (e)->
      if e.which == 13
        $('.btn-email-submit').click()

    #    $('#speaker.carousel').carousel('pause')
    #    $('#section.carousel').carousel('pause')
    if mobileDetection != undefined
      if !mobileDetection.any()
        $(document).scroll ->
          top = $(document).scrollTop()
          if top > 316 + containerMargin()
            $('.js-gooey-menu').addClass('main-menu-fixed')
            if !($('.menu-surrogate').length)
              $('.js-gooey-menu').after('<div class="menu-surrogate js-menu-surrogate"></div>')
            $('.js-logo').addClass('logo-fixed')
          if top < 316 + containerMargin()
            $('.js-gooey-menu').removeClass('main-menu-fixed')
            $('.js-menu-surrogate').remove()
            $('.js-logo').removeClass('logo-fixed')

    if $.os != undefined
      if $.os.name == 'win'
        $('.btn-large').css({'padding-top' : 13})
        $('.edit-user-info').css({'padding-bottom' : 13, 'padding-top' : 8})
    $('a[href*=#]').click (e) ->
      anchor = $(this).attr('href')
      if $(anchor).length > 0
        $('html, body').stop().animate({
          scrollTop: $(anchor).offset().top - 70
        }, 1000)
        e.preventDefault();
    tmp = $(".review");
    for i in [0..tmp.length]
      la = $(tmp.find("p")[i])
      if parseInt(la.css('height')) > 150
        la.css('height',150)
      else
        $('div.' + la.attr('class')).hide()

    $('.more-info').click () ->
      theClass = $(this).attr('class')
      theClasses = theClass.match(/\w+|"[^"]+"/g);
      selectClass = '.sub-'+theClasses[3]
      $('p' + selectClass).css('height','auto')
      $(this).hide()
      $('.hide-info').filter(selectClass).show()
    $('.hide-info').click () ->
      theClass = $(this).attr('class')
      theClasses = theClass.match(/\w+|"[^"]+"/g);
      selectClass = '.sub-'+theClasses[3]
      $('p' + selectClass).css('height',150)
      $(this).hide()
      $('.more-info').filter(selectClass).show()

  $('.js-login-form-switch-on').on click: ->
    $('.js-login-form').show()
    $('.login-form-exit').css('display', 'inline-block');
    $('.js-login-form-switch-on').enable = false


  $('.js-login-form-close').on click: ->
    $('.js-login-form').hide()
    $('.login-form-exit').hide()
    $('.js-login-form-switch-on').enable = true

  $("form#sign_in_user").on "ajax:success", (e, data, status, xhr) ->
    if data.success
      $('.js-login-form').hide()
      location.reload()
    else
      showError(data.error, '#error-login')

  #  $('.js-subscribe-submit').on click: ->
  #    $.ajax(
  #      url: 'landing/subscribe'
  #      data:
  #        email: $('.email-text').val()
  #      dataType: 'json'
  #      type: 'POST'
  #      success: (json) ->
  #        if json.success
  #          showSuccessMsg(json.msg)
  #        else
  #          $('#subscription-promise').hide()
  #          showError(json.error, '#error-email')
  #    )
  #    false

  $('.section-element').on click: ->
    $.ajax(
      url: 'section-box'
      data:
        section_id: $(@).attr('section_id')
      dataType: 'html'
      type: 'POST'
      success: (data) =>
        $('.js-report-wrap').html(data)
        selectSection($(@))
    )
    false

  $('.explain-left-element').on click: ->
    selected = $(@).data('id')
    $('.explain-right-box .explain-right-wrap').addClass('hidden')
    $('.' + selected).removeClass('hidden')
    selectExplain($(@))

selectSection = (section) ->
  $('.section-element').removeClass('current')
  section.addClass('current')

selectExplain = (explain) ->
  $('.explain-left-element').removeClass('current')
  explain.addClass('current')

showError = (msg, selector) ->
  $(selector).html(msg)
  $(selector).css('display', 'block')

showSuccessMsg = (msg) ->
  $('.subscribe-form').html('<div class="success-msg">' + msg + '</div>')
jQuery.os =  {
  name: (/(win|mac|linux|sunos|solaris|iphone|ipad)/.exec(navigator.platform.toLowerCase()))[0]
};

window.mobileDetection = {
  Android: () ->
    navigator.userAgent.match(/Android/i)
  BlackBerry: () ->
    navigator.userAgent.match(/BlackBerry/i)
  iOS: () ->
    navigator.userAgent.match(/iPhone|iPad|iPod/i)
  Opera: () ->
    navigator.userAgent.match(/Opera Mini/i)
  Windows: () ->
    navigator.userAgent.match(/IEMobile/i)
  any: () ->
    (this.Android() || this.BlackBerry() || this.iOS() || this.Opera() || this.Windows())
};

