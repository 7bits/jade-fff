package com.recruiters.repository.mapper;

import com.recruiters.model.Vacancy;
import com.recruiters.model.status.VacancyStatus;
import com.recruiters.repository.specification.impl.vacancy.VacancyListSpecification;
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
 * Mapper for Vacancy
 */
public interface VacancyMapper {

    @Select("SELECT vacancy.*, user.firstname, user.lastname " +
            "FROM vacancy " +
            "INNER JOIN employer ON employer.id = vacancy.employer_id " +
            "INNER JOIN user  ON employer.user_id=user.id " +
            "WHERE vacancy.id=#{vacancyId}")
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

    @Select("SELECT vacancy.*, user.firstname, user.lastname, " +
            "bid.id as bid_id, deal.id as deal_id " +
            "FROM vacancy " +
            "INNER JOIN employer ON employer.id = vacancy.employer_id " +
            "INNER JOIN user  ON employer.user_id=user.id " +
            "LEFT JOIN bid ON bid.vacancy_id=vacancy.id AND bid.recruiter_id=#{recruiterId} " +
            "LEFT JOIN deal ON deal.vacancy_id=vacancy.id " +
            "WHERE vacancy.id=#{vacancyId}")
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
            @Param("vacancyListSpecification") final VacancyListSpecification vacancyListSpecification
                                                    );

    @Select("SELECT vacancy.*, count(bid.id) as bid_count, " +
            "user.firstname, user.lastname " +
            "FROM vacancy " +
            "INNER JOIN employer ON employer.id = vacancy.employer_id " +
            "INNER JOIN user  ON employer.user_id=user.id " +
            "LEFT JOIN bid ON bid.vacancy_id=vacancy.id " +
            "WHERE vacancy.employer_id=#{employerId} " +
            "AND vacancy.status like 'ACTIVE' " +
            "GROUP BY vacancy.id")
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

    @Update("UPDATE vacancy SET status = #{status} WHERE id = #{vacancyId} ")
    void updateStatus(@Param(value = "vacancyId") final Long vacancyId, @Param(value = "status") final VacancyStatus status);

    @Insert("INSERT INTO vacancy (employer_id, title, description, " +
            "salary_from, salary_to, creation_date, expiration_date, " +
            "test_file, status) " +
            "VALUES (#{employer.id}, #{title}, #{description}, " +
            "#{salaryFrom}, #{salaryTo}, #{creationDate}, #{expirationDate}, " +
            "#{testFile.id}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long create(final Vacancy vacancy);
}