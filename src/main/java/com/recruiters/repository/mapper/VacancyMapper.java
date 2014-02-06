package com.recruiters.repository.mapper;

import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;
import com.recruiters.repository.specification.vacancy.VacancySpecification;
import org.apache.ibatis.annotations.*;

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
            @Result(column = "salary_from", property = "salaryFrom"),
            @Result(column = "salary_to", property = "salaryTo"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile.id"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName")
    })
    Vacancy findById(final Long vacancyId);

    @Select("SELECT vacancies.*, users.firstname, users.lastname, " +
            "bids.id as bid_id, deals.id as deal_id " +
            "FROM vacancies " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users  ON employers.user_id=users.id " +
            "LEFT JOIN bids ON bids.vacancy_id=vacancies.id AND bids.recruiter_id=#{recruiterId} " +
            "LEFT JOIN deals ON deals.vacancy_id=vacancies.id " +
            "WHERE vacancies.id=#{vacancyId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary_from", property = "salaryFrom"),
            @Result(column = "salary_to", property = "salaryTo"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile.id"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName"),
            @Result(column = "bid_id", property = "bidId"),
            @Result(column = "deal_id", property = "dealId")
    })
    Vacancy findByIdAndRecruiterId(@Param("vacancyId") final Long vacancyId, @Param("recruiterId") final Long recruiterId);

    @SelectProvider(type = VacancyFilteredProvider.class, method = "selectVacancyFiltered")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary_from", property = "salaryFrom"),
            @Result(column = "salary_to", property = "salaryTo"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile.id"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName"),
            @Result(column = "bid_id", property = "bidId"),
            @Result(column = "deal_id", property = "dealId")
    })
    List<Vacancy> findFilteredVacanciesForRecruiter(
            @Param("recruiterId") final Long recruiterId,
            @Param("date") final String date,
            @Param("searchLikeText") final String searchLikeText,
            @Param("vacancySpecification") final VacancySpecification vacancySpecification
                                                    );

    @Select("SELECT vacancies.*, count(bids.id) as bid_count, " +
            "users.firstname, users.lastname " +
            "FROM vacancies " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users  ON employers.user_id=users.id " +
            "LEFT JOIN bids ON bids.vacancy_id=vacancies.id " +
            "WHERE vacancies.employer_id=#{employerId} " +
            "AND vacancies.status like 'ACTIVE' " +
            "GROUP BY vacancies.id")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "employer_id", property = "employer.id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "salary_from", property = "salaryFrom"),
            @Result(column = "salary_to", property = "salaryTo"),
            @Result(column = "creation_date", property = "creationDate"),
            @Result(column = "expiration_date", property = "expirationDate"),
            @Result(column = "test_file", property = "testFile.id"),
            @Result(column = "bid_count", property = "bidCount"),
            @Result(column = "firstname", property = "employer.user.firstName"),
            @Result(column = "lastname", property = "employer.user.lastName")
    })
    List<Vacancy> findVacanciesByEmployerId(final Long employerId);

    @Update("UPDATE vacancies SET status = #{status} WHERE id = #{vacancyId} ")
    void updateStatus(@Param(value = "vacancyId") final Long vacancyId, @Param(value = "status") final VacancyStatus status);

    @Insert("INSERT INTO vacancies (employer_id, title, description, " +
            "salary_from, salary_to, creation_date, expiration_date, " +
            "test_file, status) " +
            "VALUES (#{employer.id}, #{title}, #{description}, " +
            "#{salaryFrom}, #{salaryTo}, #{creationDate}, #{expirationDate}, " +
            "#{testFile.id}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long create(final Vacancy vacancy);
}