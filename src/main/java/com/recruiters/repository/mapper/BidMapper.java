package com.recruiters.repository.mapper;

import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper for Bid
 */
public interface BidMapper {

    @Select("SELECT bid.id, bid.message, bid.status, bid.viewed, " +
            "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
            "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
            "vacancy.creation_date, vacancy.expiration_date, " +
            "recruiter.id as recruiter_id, " +
            "user.firstname, user.lastname " +
            "FROM bid " +
            "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=bid.recruiter_id " +
            "INNER JOIN user ON recruiter.user_id=user.id " +
            "WHERE bid.recruiter_id=#{recruiterId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "viewed", property = "viewed"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary_from", property = "vacancy.salaryFrom"),
            @Result(column = "salary_to", property = "vacancy.salaryTo"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "expiration_date", property = "vacancy.expirationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    List<Bid> findBidsByRecruiterId(final Long recruiterId);

    @Select("SELECT bid.id, bid.message, bid.status, " +
            "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
            "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
            "vacancy.creation_date, vacancy.expiration_date, vacancy.test_file, " +
            "recruiter.id as recruiter_id, " +
            "user.firstname, user.lastname, " +
            "deal.id as deal_id " +
            "FROM bid " +
            "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=bid.recruiter_id " +
            "INNER JOIN user ON recruiter.user_id=user.id " +
            "LEFT JOIN deal ON deal.vacancy_id=bid.vacancy_id " +
            "WHERE bid.id=#{bidId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary_from", property = "vacancy.salaryFrom"),
            @Result(column = "salary_to", property = "vacancy.salaryTo"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "expiration_date", property = "vacancy.expirationDate"),
            @Result(column = "test_file", property = "vacancy.testFile.id"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName"),
            @Result(column = "deal_id", property = "dealId")
    })
    Bid findById(final Long bidId);

    @Select("SELECT bid.id, bid.message, bid.status, " +
            "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
            "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
            "vacancy.creation_date, vacancy.expiration_date, " +
            "recruiter.id as recruiter_id, " +
            "user.firstname, user.lastname " +
            "FROM bid " +
            "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=bid.recruiter_id " +
            "INNER JOIN user ON recruiter.user_id=user.id " +
            "WHERE bid.vacancy_id=#{vacancyId} ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary_from", property = "vacancy.salaryFrom"),
            @Result(column = "salary_to", property = "vacancy.salaryTo"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "expiration_date", property = "vacancy.expirationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    List<Bid> findBidsByVacancyId(final Long vacancyId);

    @Insert("INSERT INTO bid (vacancy_id, recruiter_id, message) " +
            "VALUES (#{vacancy.id}, #{recruiter.id}, #{message})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(final Bid bid);

    @Update("UPDATE bid SET status = #{status} WHERE id = #{bidId} ")
    void updateStatus(@Param(value = "bidId") final Long bidId, @Param(value = "status") final BidStatus status);

    @Update("UPDATE bid SET viewed = 1 " +
            "WHERE id=#{bidId} AND viewed = 0")
    void setViewed(final Long bidId);
}