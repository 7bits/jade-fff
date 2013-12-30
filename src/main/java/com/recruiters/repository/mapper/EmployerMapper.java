package com.recruiters.repository.mapper;

import com.recruiters.model.Employer;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Mapper for Employer POJO
 */
public interface EmployerMapper {

    @Select("SELECT employers.id, employers.user_id, " +
            "users.firstname, users.lastname, users.username, users.password, users.description " +
            "FROM employers " +
            "RIGHT JOIN users ON users.id = employers.user_id " +
            "WHERE employers.id=#{employerId}")
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

    @Select("SELECT employers.id, employers.user_id, " +
            "users.firstname, users.lastname, users.username, users.password, users.description " +
            "FROM employers " +
            "RIGHT JOIN users ON users.id = employers.user_id " +
            "WHERE employers.user_id=#{userId}")
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