package com.recruiters.repository.mapper;

import com.recruiters.model.Recruiter;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper for Recruiter POJO
 */
public interface RecruiterMapper {

    @Select("SELECT recruiters.id, recruiters.user_id, " +
            "users.firstname, users.lastname, users.username, users.password " +
            "FROM recruiters " +
            "RIGHT JOIN users ON users.id = recruiters.user_id " +
            "WHERE recruiters.id=#{recruiterId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user.id"),
            @Result(column = "firstname", property = "user.firstName"),
            @Result(column = "lastname", property = "user.lastName"),
            @Result(column = "username", property = "user.username"),
            @Result(column = "password", property = "user.password")
    })
    Recruiter findById(final Long recruiterId);
}