$ ->

  # We are now ready to cut the request

  # For demonstration of how you can customize the fallbackTransport based on the browser

  # First message is author's name
  addMessage = (author, message, color, datetime) ->
    content.append "<p><span style=\"color:" + color + "\">" + author + "</span> @ " + +((if datetime.getHours() < 10 then "0" + datetime.getHours() else datetime.getHours())) + ":" + ((if datetime.getMinutes() < 10 then "0" + datetime.getMinutes() else datetime.getMinutes())) + ": " + message + "</p>"
    return
  "use strict"
  detect = $("#detect")
  header = $("#header")
  content = $("#content")
  input = $("#input")
  status = $("#status")
  myName = false
  author = null
  logged = false
  socket = $.atmosphere
  request =
    url: document.location.toString() + "/chat"
    contentType: "application/json"
    logLevel: "debug"
    transport: "websocket"
    fallbackTransport: "long-polling"

  request.onOpen = (response) ->
    content.html $("<p>",
      text: "Atmosphere connected using " + response.transport
    )
    input.removeAttr("disabled").focus()
    status.text "Choose name:"
    return

  request.onTransportFailure = (errorMsg, request) ->
    jQuery.atmosphere.info errorMsg
    request.fallbackTransport = "sse"  if window.EventSource
    header.html $("<h3>",
      text: "Atmosphere Chat. Default transport is WebSocket, fallback is " + request.fallbackTransport
    )
    return

  request.onReconnect = (request, response) ->
    socket.info "Reconnecting"
    return

  request.onMessage = (response) ->
    message = response.responseBody
    try
      json = jQuery.parseJSON(message)
    catch e
      console.log "This doesn't look like a valid JSON: ", message.data
      return
    unless logged
      logged = true
      status.text(myName + ": ").css "color", "blue"
      input.removeAttr("disabled").focus()
    else
      input.removeAttr "disabled"
      me = json.author is author
      date = (if typeof (json.time) is "string" then parseInt(json.time) else json.time)
      addMessage json.author, json.text, (if me then "blue" else "black"), new Date(date)
    return

  request.onClose = (response) ->
    logged = false
    return

  request.onError = (response) ->
    content.html $("<p>",
      text: "Sorry, but there's some problem with your " + "socket or the server is down"
    )
    return

  subSocket = socket.subscribe(request)
  input.keydown (e) ->
    if e.keyCode is 13
      msg = $(this).val()
      author = msg  unless author?
      subSocket.push jQuery.stringifyJSON(
        author: author
        message: msg
      )
      $(this).val ""
      input.attr "disabled", "disabled"
      myName = msg  if myName is false
    return

  return
