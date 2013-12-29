package com.recruiters.repository.mapper;

import com.recruiters.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 */
public interface UserMapper  {

    @Select("SELECT * FROM users WHERE username = #{userName}")
    User findByUsername(@Param("userName") String username);

    @Select("SELECT * FROM users WHERE id = #{userId}")
    User findById(@Param("id") Long userId);

}