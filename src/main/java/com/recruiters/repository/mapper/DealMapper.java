package com.recruiters.repository.mapper;

import com.recruiters.model.Deal;
import com.recruiters.model.DealStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper for Deal POJO
 */
public interface DealMapper {

    @Select("SELECT deals.id, deals.status, " +
            "vacancies.id as vacancy_id, vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary, vacancies.creation_date, vacancies.expiration_date, " +
            "recruiters.id as recruiter_id, " +
            "u1.firstname as recruiter_firstname, u1.lastname as recruiter_lastname, " +
            "u2.firstname as employer_firstname, u2.lastname as employer_lastname " +
            "FROM deals " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters ON recruiters.id=deals.recruiter_id " +
            "INNER JOIN employers ON employers.id = vacancies.employer_id " +
            "INNER JOIN users u1 ON recruiters.user_id=u1.id " +
            "INNER JOIN users u2 ON employers.user_id=u2.id " +
            "WHERE deals.id=#{dealId}")
    @Results({
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "employer_firstname", property = "vacancy.employer.user.firstName"),
            @Result(column = "employer_lastname", property = "vacancy.employer.user.lastName"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary", property = "vacancy.salary"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "expiration_date", property = "vacancy.expirationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "recruiter_firstname", property = "recruiter.user.firstName"),
            @Result(column = "recruiter_lastname", property = "recruiter.user.lastName"),
            @Result(property = "applicants", column = "id", javaType = List.class,
                    many = @Many(select = "com.recruiters.repository.mapper.ApplicantMapper.findApplicantsByDealId"))
    })
    Deal findById(@Param(value = "dealId") final Long dealId);

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
            "SELECT v.id, b.recruiter_id, \"IN_PROGRESS\" FROM vacancies v " +
            "INNER JOIN bids b on v.id = b.vacancy_id " +
            "WHERE b.id = #{bidId}")
    @Options(useGeneratedKeys = true, keyProperty = "deal.id", keyColumn = "id")
    void create(@Param(value = "bidId") final Long bidId, @Param(value = "deal") final Deal deal);

    @Update("UPDATE deals SET status = #{status} WHERE id = #{dealId} ")
    void updateStatus(@Param(value = "dealId") final Long dealId, @Param(value = "status") final DealStatus status);
}