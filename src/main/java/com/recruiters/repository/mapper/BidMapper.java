package com.recruiters.repository.mapper;

import com.recruiters.model.Bid;
import com.recruiters.model.Recruiter;
import com.recruiters.model.Vacancy;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper for Bid POJO
 */
public interface BidMapper {

    @Select("SELECT bids.id, bids.message, bids.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM bids " +
            "INNER JOIN vacancies ON vacancies.id=bids.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=bids.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE bids.recruiter_id=#{recruiterId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    List<Bid> findListOfRecruiterBids(final Long recruiterId);

    @Select("SELECT bids.id, bids.message, bids.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM bids " +
            "INNER JOIN vacancies ON vacancies.id=bids.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=bids.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE vacancies.employer_id=#{employerId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    List<Bid> findAllActiveByEmployerId(final Long employerId);

    @Select("SELECT bids.id, bids.message, bids.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM bids " +
            "INNER JOIN vacancies ON vacancies.id=bids.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=bids.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE bids.id=#{bidId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    Bid getBidById(final Long bidId);

    @Select("SELECT bids.id, bids.message, bids.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM bids " +
            "INNER JOIN vacancies ON vacancies.id=bids.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=bids.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE bids.vacancy_id=#{vacancyId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "message", property = "message"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName")
    })
    List<Bid> findAllBidsForVacancy(final Long vacancyId);

    Boolean createBid(final Recruiter recruiter, final Vacancy vacancy, final  String message);
}