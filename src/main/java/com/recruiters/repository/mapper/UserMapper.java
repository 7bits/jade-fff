package com.recruiters.repository.mapper;

import com.recruiters.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Mapper for User
 */
public interface UserMapper  {

    @Select("SELECT u.*,e.id as employerId, r.id as recruiterId FROM user u " +
            "LEFT JOIN employer e ON e.user_id = u.id " +
            "LEFT JOIN recruiter r ON r.user_id = u.id " +
            "   WHERE username = #{userName}")
    User findByUsername(@Param("userName") String username);

    @Update("UPDATE user SET firstname=#{firstName}, " +
            "lastname=#{lastName}, description=#{description}, " +
            "password=#{password} " +
            "WHERE id=#{id}")
    void update(final User user);
}