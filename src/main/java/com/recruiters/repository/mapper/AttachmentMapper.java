package com.recruiters.repository.mapper;

import com.recruiters.model.Attachment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper for Attachment POJO
 */
public interface AttachmentMapper {

    @Select("SELECT * " +
            "FROM attachments " +
            "WHERE id=#{attachmentId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "system_filename", property = "systemFilename"),
            @Result(column = "public_filename", property = "publicFilename"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "recruiter_id", property = "recruiter.id")
    })
    Attachment findById(final Long attachmentId);



    @Insert("INSERT INTO attachments (system_filename, public_filename, employer_id, recruiter_id) " +
            "VALUES (#{systemFilename}, #{publicFilename}, #{employer.id}, #{recruiter.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(final Attachment attachment);
}
