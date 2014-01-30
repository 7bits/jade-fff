package com.recruiters.repository;

import com.recruiters.model.Attachment;
import com.recruiters.model.Employer;
import com.recruiters.model.Recruiter;
import com.recruiters.repository.mapper.AttachmentMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Repository implementing all methods related to
 * Attachment manipulations
 */
@Repository
@PropertySource("classpath:upload.properties")
public class AttachmentRepository {

    /** MyBatis Attachment Mapper */
    @Autowired
    private AttachmentMapper attachmentMapper = null;
    /** Environment for getting properties */
    @Autowired
    private Environment environment;

    /**
     * Saves file and returns url for it
     * @param file    File
     * @return File Url
     */
    public Attachment save(
            final MultipartFile file,
            final String publicFilename,
            final Long recruiterId,
            final Long employerId
    ) throws RepositoryException {
        Attachment attachment = new Attachment();
        if (file == null || publicFilename == null) {
            throw new RepositoryException("file or publicFilename is null");
        }
        try {
            File saveFile = filePrepare();
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(saveFile);
            IOUtils.copy(is, os);
            attachment.setSystemFilename(saveFile.getPath());
            attachment.setPublicFilename(publicFilename);
            attachment.setEmployer(new Employer(employerId));
            attachment.setRecruiter(new Recruiter(recruiterId));
            attachmentMapper.create(attachment);
        return attachment;
        } catch (Exception e) {
            throw new RepositoryException("Database or file saving error ", e);
        }
    }

    /**
     * Prepare file for writing content in it
     * @return file
     */
    private File filePrepare() {
        try {
            String directory = environment.getProperty("recruiter-upload.save-path");
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return File.createTempFile("att", "", dir);
        } catch (Exception e) {
            return null;
        }
    }
}
