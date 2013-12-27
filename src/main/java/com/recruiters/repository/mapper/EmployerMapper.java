package com.recruiters.repository.mapper;

import com.recruiters.model.Employer;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 *
 */
public interface EmployerMapper {

    @Select("SELECT employers.*, users.* " +
            "FROM employers " +
            "RIGHT JOIN users ON users.id = employers.user_id " +
            "WHERE employers.id=#{employerId}")
    @Results({
            @Result(column="id", property="id"),
            @Result(column="user_id", property="user.id"),
            @Result(column="firstname", property="user.firstName"),
            @Result(column="lastname", property="user.lastName"),
            @Result(column="username", property="user.username"),
            @Result(column="password", property="user.password")
    })
    Employer getById(final Long employerId);

    @Select("SELECT employers.*, users.* " +
            "FROM employers " +
            "RIGHT JOIN users ON users.id = employers.user_id " +
            "WHERE employers.user_id=#{userId}")
    @Results({
            @Result(column="id", property="id"),
            @Result(column="user_id", property="user.id"),
            @Result(column="firstname", property="user.firstName"),
            @Result(column="lastname", property="user.lastName"),
            @Result(column="username", property="user.username"),
            @Result(column="password", property="user.password")
    })
    Employer getByUserId(final Long userId);
}