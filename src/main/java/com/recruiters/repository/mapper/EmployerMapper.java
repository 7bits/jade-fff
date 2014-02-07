package com.recruiters.repository.mapper;

import com.recruiters.model.Employer;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper for Employer
 */
public interface EmployerMapper {

    @Select("SELECT employer.id, employer.user_id, " +
            "user.firstname, user.lastname, user.username, user.password, user.description " +
            "FROM employer " +
            "RIGHT JOIN user ON user.id = employer.user_id " +
            "WHERE employer.id=#{employerId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user.id"),
            @Result(column = "firstname", property = "user.firstName"),
            @Result(column = "lastname", property = "user.lastName"),
            @Result(column = "username", property = "user.username"),
            @Result(column = "password", property = "user.password"),
            @Result(column = "description", property = "user.description")
    })
    Employer findById(final Long employerId);

    @Select("SELECT employer.id, employer.user_id, " +
            "user.firstname, user.lastname, user.username, user.password, user.description " +
            "FROM employer " +
            "RIGHT JOIN user ON user.id = employer.user_id " +
            "WHERE employer.user_id=#{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user.id"),
            @Result(column = "firstname", property = "user.firstName"),
            @Result(column = "lastname", property = "user.lastName"),
            @Result(column = "username", property = "user.username"),
            @Result(column = "password", property = "user.password"),
            @Result(column = "description", property = "user.description")
    })
    Employer getByUserId(final Long userId);
}