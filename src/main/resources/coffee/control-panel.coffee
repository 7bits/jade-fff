$ ->
  employerFeedback = ->
    table = $("table#employer-feedback")
    if table.length
      request = $.ajax(
        url: "employer-control-panel-feedback.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        dealList = data.entry.value
        i = 0
        while i < dealList.length
          obj = dealList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.recruiterUrl + "\">" + obj.recruiter + "</td><td>" + obj.status  + "</td><td>" + obj.updated  + "</td><td><a href=\"" + obj.feedbackUrl + "\">" + obj.feedback + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          employerFeedback()
          return
        ), 10000
        return

      request.fail ->
        return

    return

  employerApplicants = ->
    table = $("table#employer-applicants")
    if table.length
      request = $.ajax(
        url: "employer-control-panel-applicants.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        applicantList = data.entry.value
        i = 0
        while i < applicantList.length
          obj = applicantList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.recruiterUrl + "\">" + obj.recruiter + "</td><td>" + obj.updated  + "</td><td><a href=\"" + obj.applicantUrl + "\">" + obj.applicant + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          employerApplicants()
          return
        ), 10000
        return

      request.fail ->
        return

    return


  employerBids = ->
    table = $("table#employer-bids")
    if table.length
      request = $.ajax(
        url: "employer-control-panel-bids.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        bidList = data.entry.value
        i = 0
        while i < bidList.length
          obj = bidList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.recruiterUrl + "\">" + obj.recruiter + "</td><td>" + obj.updated  + "</td><td><a href=\"" + obj.bidUrl + "\">" + obj.bid + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          employerBids()
          return
        ), 10000
        return

      request.fail ->
        return

    return

  recruiterFeedback = ->
    table = $("table#recruiter-feedback")
    if table.length
      request = $.ajax(
        url: "recruiter-control-panel-feedback.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        dealList = data.entry.value
        i = 0
        while i < dealList.length
          obj = dealList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.employerUrl + "\">" + obj.employer + "</td><td>" + obj.status  + "</td><td>" + obj.updated  + "</td><td><a href=\"" + obj.feedbackUrl + "\">" + obj.feedback + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          recruiterFeedback()
          return
        ), 10000
        return

      request.fail ->
        return

    return

  recruiterDeals = ->
    table = $("table#recruiter-deals")
    if table.length
      request = $.ajax(
        url: "recruiter-control-panel-deals.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        dealList = data.entry.value
        i = 0
        while i < dealList.length
          obj = dealList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.employerUrl + "\">" + obj.employer + "</td><td>" + obj.created + "</td><td><a href=\"" + obj.dealUrl + "\">" + obj.deal + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          recruiterDeals()
          return
        ), 10000
        return

      request.fail ->
        return

    return

  recruiterBids = ->
    table = $("table#recruiter-bids")
    if table.length
      request = $.ajax(
        url: "recruiter-control-panel-bids.json"
        type: "GET"
        async: true
        dataType: "json"
      )
      request.done (data) ->
        addHtml = ""
        bidList = data.entry.value
        i = 0
        while i < bidList.length
          obj = bidList[i]
          addHtml += "<tr><td><a href=\"" + obj.vacancyUrl + "\">" + obj.vacancy + "</a></td><td><a href=\"" + obj.employerUrl + "\">" + obj.employer + "</td><td>" + obj.updated  + "</td><td>" + obj.viewed  + "</td><td>" + obj.status  + "</td><td><a href=\"" + obj.bidUrl + "\">" + obj.bid + "</td></tr>"
          i++
        table.find("tbody").html addHtml
        setTimeout (->
          recruiterBids()
          return
        ), 10000
        return

      request.fail ->
        return

    return

  employerFeedback()
  employerApplicants()
  employerBids()
  recruiterFeedback()
  recruiterDeals()
  recruiterBids()
  return
