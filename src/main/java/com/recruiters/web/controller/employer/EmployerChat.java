package com.recruiters.web.controller.employer;

import com.recruiters.model.ChatMessage;
import com.recruiters.model.User;
import com.recruiters.service.EmployerService;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import com.recruiters.web.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Chat related controllers
 */
@Controller
public class EmployerChat {

    /** Employer Service provides all Employer related methods */
    @Autowired
    private EmployerService employerService = null;
    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Json converter service */
    @Autowired
    private JsonService jsonService;

    /**
     * Send message (saving it in database)
     * @param message     Message
     * @param dealId      Deal id
     * @param request     Http Request
     * @param response    Http Response
     * @return object with success message if everything is ok,
     * Forbidden page if message is submitted to deal not related
     * to current employer,
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/employer-send-message.json", method = RequestMethod.POST)
    public Object[] sendMessage(
            @RequestParam(value = "message", required = false) final String message,
            @RequestParam(value = "id", required = true) final Long dealId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            employerService.sendMessage(dealId, message, user.getEmployerId());
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

        return new Object[]{"All ok"};
    }

    /**
     * Show chat messages to employer
     * @param dealId      Deal id
     * @param messageId   Id of message to start list from
     * @param request     Http Request
     * @param response    Http Response
     * @return list of new messages since messageId,
     * Forbidden page if message is submitted to deal not related
     * to current employer,
     * Internal Server Error page if something is wrong with
     * saving data due to technical or any other reasons
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown     */
    @RequestMapping(value = "/employer-show-messages.json", method = RequestMethod.GET)
    public List<Map<String,String>> showMessages(
            @RequestParam(value = "messageId", required = false) final Long messageId,
            @RequestParam(value = "dealId", required = true) final Long dealId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        List<ChatMessage> messages = new ArrayList<ChatMessage>();
        try {
            User user = userUtils.getCurrentUser(request);
            Integer i = 0;
            while (messages.isEmpty() && i < 60) {
                messages = employerService.findMessages(dealId, messageId, user.getEmployerId());
                TimeUnit.SECONDS.sleep(1);
                i++;
            }
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        Locale locale = RequestContextUtils.getLocale(request);
        return jsonService.chatMessageList(messages, locale);
    }


    public UserUtils getUserUtils() {
        return userUtils;
    }

    public void setUserUtils(final UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public JsonService getJsonService() {
        return jsonService;
    }

    public void setJsonService(final JsonService jsonService) {
        this.jsonService = jsonService;
    }
}

