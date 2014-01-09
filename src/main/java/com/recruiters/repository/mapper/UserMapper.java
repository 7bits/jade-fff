package com.recruiters.repository.mapper;

import com.recruiters.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 */
public interface UserMapper  {

    @Select("SELECT u.*,e.id as employerId, r.id as recruiterId FROM users u " +
            "LEFT JOIN employers e ON e.user_id = u.id " +
            "LEFT JOIN recruiters r ON r.user_id = u.id " +
            "   WHERE username = #{userName}")
    User findByUsername(@Param("userName") String username);

    @Update("UPDATE users SET firstname=#{firstName}, " +
            "lastname=#{lastName}, description=#{description}, " +
            "password=#{password} " +
            "WHERE id=#{id}")
    void update(final User user);
}