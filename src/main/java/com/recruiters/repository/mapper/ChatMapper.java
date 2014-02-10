package com.recruiters.repository.mapper;

import com.recruiters.model.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper for ChatMessage
 */
public interface ChatMapper {

    @Select("SELECT chat.*, " +
            "u1.firstname as employer_firstname, u1.lastname as employer_lastname, " +
            "u2.firstname as recruiter_firstname, u2.lastname as recruiter_lastname " +
            "FROM chat " +
            "LEFT JOIN employer ON employer.id = chat.employer_id " +
            "LEFT JOIN user u1 ON u1.id = employer.user_id " +
            "LEFT JOIN recruiter ON recruiter.id = chat.recruiter_id " +
            "LEFT JOIN user u2 ON u2.id = recruiter.user_id " +
            "WHERE chat.deal_id=#{dealId} AND chat.id>#{messageId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "deal_id", property = "deal.id"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "recruiter_firstname", property = "recruiter.user.firstName"),
            @Result(column = "recruiter_lastname", property = "recruiter.user.lastName"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "employer_firstname", property = "employer.user.firstName"),
            @Result(column = "employer_lastname", property = "employer.user.lastName"),
            @Result(column = "date", property = "date"),
            @Result(column = "message", property = "message")
    })
    List<ChatMessage> findByDealIdSinceId(@Param("dealId") final Long dealId,
                                          @Param("messageId") final Long messageId);



    @Insert("INSERT INTO chat (deal_id, recruiter_id, employer_id, message) " +
            "VALUES (#{deal.id}, #{recruiter.id}, #{employer.id}, #{message})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(final ChatMessage chatMessage);
}
