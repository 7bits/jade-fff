package com.recruiters.repository.mapper;

import com.recruiters.model.Vacancy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper for Vacancy POJO
 */
public interface VacancyMapper {

    @Select("SELECT vacancies.*, users.firstname, users.lastname " +
            "FROM vacancies " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users  ON employers.user_id=users.id " +
            "WHERE vacancies.id=#{vacancyId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary", property = "salary"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName")
    })
    Vacancy findById(final Long vacancyId);

    @Select("SELECT vacancies.*, users.firstname, users.lastname " +
            "FROM vacancies " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users  ON employers.user_id=users.id " +
            "WHERE vacancies.id = #{vacancyId} " +
            "AND EXISTS (SELECT * FROM bids b WHERE b.vacancy_id = vacancies.id AND b.recruiter_id = #{recruiterId})")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary", property = "salary"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName")
    })
    Vacancy findWithActiveBidByIdAndRecruiterId(@Param(value = "vacancyId") final Long vacancyId, @Param(value = "recruiterId") final Long recruiterId);

    @Select("SELECT vacancies.*, users.firstname, users.lastname " +
            "FROM vacancies " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users  ON employers.user_id=users.id " +
            "WHERE status='ACTIVE' " +
            "AND NOT EXISTS (SELECT * FROM bids b WHERE b.vacancy_id = vacancies.id AND b.recruiter_id = #{recruiterId}) ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary", property = "salary"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName")
    })
    List<Vacancy> findAvailableVacanciesForRecruiter(final Long recruiterId);

    @Select("SELECT vacancies.*, count(bids.id) as bid_count, " +
            "users.firstname, users.lastname " +
            "FROM vacancies " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users  ON employers.user_id=users.id " +
            "LEFT JOIN bids ON bids.vacancy_id=vacancies.id AND bids.status like 'ACTIVE' " +
            "WHERE vacancies.employer_id=#{employerId} " +
            "GROUP BY vacancies.id")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary", property = "salary"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile"),
            @Result(column = "bid_count", property = "bidCount"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName")
    })
    List<Vacancy> findVacanciesByEmployerId(final Long employerId);
}