package com.recruiters.web.controller.recruiter;

import com.recruiters.model.Attachment;
import com.recruiters.model.User;
import com.recruiters.service.RecruiterService;
import com.recruiters.service.exception.NotAffiliatedException;
import com.recruiters.service.exception.NotFoundException;
import com.recruiters.service.exception.ServiceException;
import com.recruiters.web.controller.utils.UserUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Send file to Recruiter, requested downloading
 */
@Controller
public class RecruiterAttachment {

    /** User utils for obtaining any session user information */
    @Autowired
    private UserUtils userUtils = null;
    /** Recruiter Service provides all Recruiter related methods */
    @Autowired
    private RecruiterService recruiterService = null;

    /**
     * Download attachment
     * @param attachmentId    Attachment id
     * @param request         Http Request
     * @param response        Http Response
     * Send file to requester or display error:
     * Internal Server Error page, if something is wrong with obtaining data due to
     * technical or any other reasons,
     * Forbidden, if this requester have no permission to download this file
     * Not Found, if there are no attachment with such id
     * @throws Exception in very rare circumstances: it should be runtime
     * or servlet Exception to be thrown
     */
    @RequestMapping(value = "/recruiter-download-attachment/{attachmentId}", method = RequestMethod.GET)
    public void downloadAttachment(
            @PathVariable final Long attachmentId,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        try {
            User user = userUtils.getCurrentUser(request);
            Attachment attachment = recruiterService.findAttachment(attachmentId, user.getRecruiterId());
            String userAgent = request.getHeader("User-Agent");
            String encodedFileName = null;
            // Different browsers using different technique for recognizing UTF-8 filenames
            if (userAgent.contains("MSIE") || userAgent.contains("Opera")) {
                encodedFileName = URLEncoder.encode(attachment.getPublicFilename(), "UTF-8");
            } else {
                encodedFileName = "=?UTF-8?B?" +
                        new String(Base64.encodeBase64(attachment.getPublicFilename().getBytes("UTF-8")), "UTF-8") +
                        "?=";
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
            IOUtils.copy(attachment.getFileStream(), response.getOutputStream());
            response.flushBuffer();
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotAffiliatedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
