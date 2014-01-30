package com.recruiters.repository.mapper;

import com.recruiters.model.Attachment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * Mapper for Attachment POJO
 */
public interface AttachmentMapper {
    @Insert("INSERT INTO attachments (system_filename, public_filename, employer_id, recruiter_id) " +
            "VALUES (#{systemFilename}, #{publicFilename}, #{employer.id}, #{recruiter.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(final Attachment attachment);
}
