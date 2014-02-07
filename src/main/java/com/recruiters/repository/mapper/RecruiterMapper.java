package com.recruiters.repository.mapper;

import com.recruiters.model.Recruiter;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper for Recruiter
 */
public interface RecruiterMapper {

    @Select("SELECT recruiter.id, recruiter.user_id, " +
            "user.firstname, user.lastname, user.username, user.password, user.description " +
            "FROM recruiter " +
            "RIGHT JOIN user ON user.id = recruiter.user_id " +
            "WHERE recruiter.id=#{recruiterId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user.id"),
            @Result(column = "firstname", property = "user.firstName"),
            @Result(column = "lastname", property = "user.lastName"),
            @Result(column = "username", property = "user.username"),
            @Result(column = "password", property = "user.password"),
            @Result(column = "description", property = "user.description")
    })
    Recruiter findById(final Long recruiterId);
}