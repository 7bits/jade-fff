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
            "vacancies.description, vacancies.salary_from, vacancies.salary_to, " +
            "vacancies.creation_date, vacancies.expiration_date, " +
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
            @Result(column = "salary_from", property = "vacancy.salaryFrom"),
            @Result(column = "salary_to", property = "vacancy.salaryTo"),
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
            "vacancies.description, vacancies.salary_from, vacancies.salary_to, " +
            "vacancies.creation_date, " +
            "recruiters.id as recruiter_id, " +
            "users.firstname, users.lastname, " +
            "(SELECT COUNT(applicants.id) FROM applicants " +
            "WHERE deal_id=deals.id) as all_applicants, " +
            "(SELECT COUNT(applicants.id) FROM applicants " +
            "WHERE deal_id=deals.id AND viewed=1) as viewed_applicants, " +
            "(SELECT COUNT(applicants.id) FROM applicants " +
            "WHERE deal_id=deals.id AND viewed=0) as unseen_applicants, " +
            "(SELECT COUNT(applicants.id) FROM applicants " +
            "WHERE deal_id=deals.id AND status=\"REJECTED\") as rejected_applicants, " +
            "max_updated.max_updated_date " +
            "FROM deals " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=deals.recruiter_id " +
            "INNER JOIN users ON recruiters.user_id=users.id " +
            "INNER JOIN (SELECT id, MAX(updated_date) as max_updated_date FROM " +
            "(SELECT id,updated_date FROM deals WHERE recruiter_id=#{recruiterId} " +
            "UNION ALL " +
            "SELECT deals.id, vacancies.updated_date FROM vacancies " +
            "INNER JOIN deals ON deals.vacancy_id=vacancies.id " +
            "WHERE deals.recruiter_id=#{recruiterId} " +
            "UNION ALL " +
            "SELECT deals.id, MAX(applicants.updated_date)  FROM applicants " +
            "INNER JOIN deals ON applicants.deal_id=deals.id " +
            "WHERE deals.recruiter_id=#{recruiterId})as updated_dates GROUP BY id) as max_updated " +
            "ON max_updated.id=deals.id " +
            "WHERE recruiters.id=#{recruiterId} AND " +
            "deals.status IN(\"IN_PROGRESS\", \"FIRED\", \"APPROVED\") AND " +
            "deals.recruiter_archived = 0")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "status", property = "status"),
            @Result(column = "vacancy_id", property = "vacancy.id"),
            @Result(column = "employer_id", property = "vacancy.employer.id"),
            @Result(column = "title", property = "vacancy.title"),
            @Result(column = "description", property = "vacancy.description"),
            @Result(column = "salary_from", property = "vacancy.salaryFrom"),
            @Result(column = "salary_to", property = "vacancy.salaryTo"),
            @Result(column = "creation_date", property = "vacancy.creationDate"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "firstname", property = "recruiter.user.firstName"),
            @Result(column = "lastname", property = "recruiter.user.lastName"),
            @Result(column = "all_applicants", property = "allApplicantCount"),
            @Result(column = "viewed_applicants", property = "viewedApplicantCount"),
            @Result(column = "unseen_applicants", property = "unseenApplicantCount"),
            @Result(column = "rejected_applicants", property = "rejectedApplicantCount"),
            @Result(column = "max_updated_date", property = "lastModified")
    })
    List<Deal> findActiveDealsByRecruiterId(final Long recruiterId);

    @Select("SELECT deals.id, deals.status, " +
            "vacancies.id as vacancy_id,  vacancies.employer_id, vacancies.title, " +
            "vacancies.description, vacancies.salary_from, vacancies.salary_to, " +
            "vacancies.creation_date, " +
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
            @Result(column = "salary_from", property = "vacancy.salaryFrom"),
            @Result(column = "salary_to", property = "vacancy.salaryTo"),
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

    @Update("UPDATE deals SET recruiter_archived = 1 " +
            "WHERE recruiter_id = #{recruiterId}  AND recruiter_archived = 0 " +
            "AND status = \"FIRED\"")
    void clearFiredByRecruiterId(final Long recruiterId);

    @Update("UPDATE deals SET recruiter_archived = 1 " +
            "WHERE recruiter_id = #{recruiterId}  AND recruiter_archived = 0 " +
            "AND status = \"APPROVED\"")
    void clearApprovedByRecruiterId(final Long recruiterId);
}