package com.recruiters.repository.mapper;

import com.recruiters.model.Bid;
import com.recruiters.model.status.BidStatus;
import com.recruiters.repository.mapper.provider.BidFilteredProvider;
import com.recruiters.repository.specification.impl.bid.BidListSpecification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper for Bid
 */
public interface BidMapper {

    @SelectProvider(type = BidFilteredProvider.class, method = "selectBidsFiltered")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "viewed", property = "viewed"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "firstname", property = "vacancy.employer.user.firstName"),
            @Result(column = "lastname", property = "vacancy.employer.user.lastName"),
            @Result(column = "creation_date", property = "dateCreated"),
            @Result(column = "updated_date", property = "lastModified"),
            @Result(column = "recruiter_id", property = "recruiter.id")
    })
    List<Bid> findBidsByRecruiterId(@Param("recruiterId") final Long recruiterId,
                                    @Param("bidListSpecification") final BidListSpecification bidListSpecification
    );

    @Select("SELECT bid.id, bid.message, bid.status, " +
            "bid.creation_date as bid_created, bid.updated_date as bid_updated, " +
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
            @Result(column = "bid_created", property = "dateCreated"),
            @Result(column = "bid_updated", property = "lastModified"),
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

    @Select("SELECT bid.id, bid.message, bid.status, bid.creation_date, bid.viewed, " +
            "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
            "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
            "vacancy.creation_date, vacancy.expiration_date, " +
            "recruiter.id as recruiter_id, " +
            "user.firstname, user.lastname " +
            "FROM bid " +
            "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=bid.recruiter_id " +
            "INNER JOIN user ON recruiter.user_id=user.id " +
            "WHERE bid.vacancy_id=#{vacancyId} " +
            "ORDER BY bid.viewed")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "creation_date", property = "dateCreated"),
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
    List<Bid> findBidsByVacancyId(final Long vacancyId);

    @Select("SELECT bid.id, bid.creation_date, " +
            "vacancy.id AS vacancy_id, vacancy.title, " +
            "recruiter.id AS recruiter_id, " +
            "user.firstname, user.lastname " +
            "FROM bid " +
            "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=bid.recruiter_id " +
            "INNER JOIN user ON recruiter.user_id=user.id " +
            "WHERE vacancy.employer_id=#{employerId} AND bid.viewed=0 " +
            "ORDER BY bid.creation_date DESC ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "creation_date", property = "dateCreated"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    List<Bid> findNewBidsForEmployer(final Long employerId);

    @Select("SELECT bid.id, bid.updated_date, bid.viewed, bid.status, " +
            "vacancy.id AS vacancy_id, vacancy.title, " +
            "employer.id AS employer_id, " +
            "user.firstname, user.lastname " +
            "FROM bid " +
            "INNER JOIN vacancy ON vacancy.id=bid.vacancy_id " +
            "INNER JOIN employer ON employer.id=vacancy.employer_id " +
            "INNER JOIN user ON employer.user_id=user.id " +
            "WHERE bid.recruiter_id=#{recruiterId} AND bid.updated_date>DATE_SUB(NOW(), INTERVAL 14 day) " +
            "ORDER BY bid.updated_date DESC ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "updated_date", property = "lastModified"),
            @Result(column = "viewed", property = "viewed"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "firstname", property = "vacancy.employer.user.firstName"),
            @Result(column = "lastname", property = "vacancy.employer.user.lastName")
    })
    List<Bid> findLastBidsForRecruiter(final Long recruiterId);

    @Insert("INSERT INTO bid (vacancy_id, recruiter_id, message, creation_date) " +
            "VALUES (#{vacancy.id}, #{recruiter.id}, #{message}, #{dateCreated})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void create(final Bid bid);

    @Update("UPDATE bid SET status = #{status} WHERE id = #{bidId} ")
    void updateStatus(@Param(value = "bidId") final Long bidId, @Param(value = "status") final BidStatus status);

    @Update("UPDATE bid SET viewed = 1 " +
            "WHERE id=#{bidId} AND viewed = 0")
    void setViewed(final Long bidId);

    @Update("UPDATE bid SET recruiter_archived = 1 " +
            "WHERE recruiter_id = #{recruiterId}  AND recruiter_archived = 0 " +
            "AND status = \"REJECTED\"")
    void clearRejectedByRecruiterId(final Long recruiterId);

    @Update("UPDATE bid SET recruiter_archived = 1 " +
            "WHERE recruiter_id = #{recruiterId}  AND recruiter_archived = 0 " +
            "AND status = \"APPROVED\"")
    void clearApprovedByRecruiterId(final Long recruiterId);
}