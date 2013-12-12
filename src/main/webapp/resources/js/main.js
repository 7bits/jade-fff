(function() {
  var selectExplain, selectSection, showError, showSuccessMsg;

  $(function() {
    $(document).ready(function() {
      var containerMargin, i, la, tmp, _i, _ref;
      containerMargin = function() {
        var margin;
        margin = $('.inside-container').css('marginTop');
        if (margin != null) {
          return parseInt(margin);
        } else {
          return 0;
        }
      };
      $(".subscribe-form-input").keypress(function(e) {
        if (e.which === 13) {
          return $('.btn-email-submit').click();
        }
      });
      if (mobileDetection !== void 0) {
        if (!mobileDetection.any()) {
          $(document).scroll(function() {
            var top;
            top = $(document).scrollTop();
            if (top > 316 + containerMargin()) {
              $('.js-gooey-menu').addClass('main-menu-fixed');
              if (!($('.menu-surrogate').length)) {
                $('.js-gooey-menu').after('<div class="menu-surrogate js-menu-surrogate"></div>');
              }
              $('.js-logo').addClass('logo-fixed');
            }
            if (top < 316 + containerMargin()) {
              $('.js-gooey-menu').removeClass('main-menu-fixed');
              $('.js-menu-surrogate').remove();
              return $('.js-logo').removeClass('logo-fixed');
            }
          });
        }
      }
      if ($.os !== void 0) {
        if ($.os.name === 'win') {
          $('.btn-large').css({
            'padding-top': 13
          });
          $('.edit-user-info').css({
            'padding-bottom': 13,
            'padding-top': 8
          });
        }
      }
      $('a[href*=#]').click(function(e) {
        var anchor;
        anchor = $(this).attr('href');
        if ($(anchor).length > 0) {
          $('html, body').stop().animate({
            scrollTop: $(anchor).offset().top - 70
          }, 1000);
          return e.preventDefault();
        }
      });
      tmp = $(".review");
      for (i = _i = 0, _ref = tmp.length; 0 <= _ref ? _i <= _ref : _i >= _ref; i = 0 <= _ref ? ++_i : --_i) {
        la = $(tmp.find("p")[i]);
        if (parseInt(la.css('height')) > 150) {
          la.css('height', 150);
        } else {
          $('div.' + la.attr('class')).hide();
        }
      }
      $('.more-info').click(function() {
        var selectClass, theClass, theClasses;
        theClass = $(this).attr('class');
        theClasses = theClass.match(/\w+|"[^"]+"/g);
        selectClass = '.sub-' + theClasses[3];
        $('p' + selectClass).css('height', 'auto');
        $(this).hide();
        return $('.hide-info').filter(selectClass).show();
      });
      return $('.hide-info').click(function() {
        var selectClass, theClass, theClasses;
        theClass = $(this).attr('class');
        theClasses = theClass.match(/\w+|"[^"]+"/g);
        selectClass = '.sub-' + theClasses[3];
        $('p' + selectClass).css('height', 150);
        $(this).hide();
        return $('.more-info').filter(selectClass).show();
      });
    });
    $('.js-login-form-switch-on').on({
      click: function() {
        $('.js-login-form').show();
        $('.login-form-exit').css('display', 'inline-block');
        return $('.js-login-form-switch-on').enable = false;
      }
    });
    $('.js-login-form-close').on({
      click: function() {
        $('.js-login-form').hide();
        $('.login-form-exit').hide();
        return $('.js-login-form-switch-on').enable = true;
      }
    });
    $("form#sign_in_user").on("ajax:success", function(e, data, status, xhr) {
      if (data.success) {
        $('.js-login-form').hide();
        return location.reload();
      } else {
        return showError(data.error, '#error-login');
      }
    });
    $('.section-element').on({
      click: function() {
        var _this = this;
        $.ajax({
          url: 'section-box',
          data: {
            section_id: $(this).attr('section_id')
          },
          dataType: 'html',
          type: 'POST',
          success: function(data) {
            $('.js-report-wrap').html(data);
            return selectSection($(_this));
          }
        });
        return false;
      }
    });
    return $('.explain-left-element').on({
      click: function() {
        var selected;
        selected = $(this).data('id');
        $('.explain-right-box .explain-right-wrap').addClass('hidden');
        $('.' + selected).removeClass('hidden');
        return selectExplain($(this));
      }
    });
  });

  selectSection = function(section) {
    $('.section-element').removeClass('current');
    return section.addClass('current');
  };

  selectExplain = function(explain) {
    $('.explain-left-element').removeClass('current');
    return explain.addClass('current');
  };

  showError = function(msg, selector) {
    $(selector).html(msg);
    return $(selector).css('display', 'block');
  };

  showSuccessMsg = function(msg) {
    return $('.subscribe-form').html('<div class="success-msg">' + msg + '</div>');
  };

  jQuery.os = {
    name: (/(win|mac|linux|sunos|solaris|iphone|ipad)/.exec(navigator.platform.toLowerCase()))[0]
  };

  window.mobileDetection = {
    Android: function() {
      return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
      return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
      return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
      return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
      return navigator.userAgent.match(/IEMobile/i);
    },
    any: function() {
      return this.Android() || this.BlackBerry() || this.iOS() || this.Opera() || this.Windows();
    }
  };

}).call(this);
