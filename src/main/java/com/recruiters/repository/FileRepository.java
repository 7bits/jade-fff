package com.recruiters.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * Repository implementing all methods related to
 * File manipulations
 */
@Repository
public class FileRepository {

    /**
     * Saves file and returns url for it
     * Not implemented yet
     * @param file    File
     * @return File Url
     */
    public String saveFile(final MultipartFile file) {
        return "";
    }
}
