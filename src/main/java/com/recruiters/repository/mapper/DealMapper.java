package com.recruiters.repository.mapper;

import com.recruiters.model.Deal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper for Deal POJO
 */
public interface DealMapper {

    @Select("SELECT deals.id, deals.status, " +
            "vacancies.id as vacancy_id, vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM deals " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters ON recruiters.id=deals.recruiter_id " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users ON employers.user_id=users.id " +
            "WHERE deals.id=#{dealId} and recruiters.id=#{recruiterId}")
    @Results({
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "vacancy.employer.user.firstName"),
            @Result(column = "lastname", property = "vacancy.employer.user.lastName"),
            @Result(property = "applicants", column = "id", javaType = List.class,
                    many = @Many(select = "com.recruiters.repository.mapper.ApplicantMapper.findApplicantsByDealId"))
    })
    Deal findByDealIdAndRecruiterId(@Param(value = "dealId") final Long dealId, @Param(value = "recruiterId") final Long recruiterId);

    @Select("SELECT deals.id, deals.status, " +
            "vacancies.id as vacancy_id, vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM deals " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters ON recruiters.id=deals.recruiter_id " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE deals.id=#{dealId} and employers.id=#{employerId}")
    @Results({
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName"),
            @Result(property = "applicants", column = "id", javaType = List.class,
                    many = @Many(select = "com.recruiters.repository.mapper.ApplicantMapper.findApplicantsByDealId"))
    })
    Deal findByIdAndEmployerId(@Param(value = "dealId") final Long dealId, @Param(value = "employerId") final Long employerId);

    @Select("SELECT deals.id, deals.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM deals " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=deals.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE recruiters.id=#{recruiterId} AND deals.status=\"IN_PROGRESS\"")
    @Results({
            @Result(column = "id", property = "id"),
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
    List<Deal> findActiveDealsByRecruiterId(final Long recruiterId);

    @Select("SELECT deals.id, deals.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname " +
            "FROM deals " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=deals.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "WHERE vacancies.employer_id=#{employerId} AND deals.status=\"IN_PROGRESS\"")
    @Results({
            @Result(column = "id", property = "id"),
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
    List<Deal> findActiveDealsByEmployerId(final Long employerId);

    @Insert("INSERT INTO deals (vacancy_id, recruiter_id, status) " +
            "SELECT v.id, b.recruiter_id, \"IN_PROGRESS\" FROM vacancies v inner join bids b on v.id = b.vacancy_id WHERE b.id = #{bidId}")
    void create(final Long bidId);
}