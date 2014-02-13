package com.recruiters.repository.mapper;

import com.recruiters.model.Deal;
import com.recruiters.model.status.DealStatus;
import com.recruiters.repository.mapper.provider.DealFilteredProvider;
import com.recruiters.repository.specification.impl.deal.DealListSpecification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper for Deal
 */
public interface DealMapper {

    @Select("SELECT deal.id, deal.status, " +
            "vacancy.id as vacancy_id, vacancy.employer_id, vacancy.title, " +
            "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
            "vacancy.creation_date, vacancy.expiration_date, vacancy.test_file, " +
            "recruiter.id as recruiter_id, " +
            "bid.id as bid_id, bid.message, " +
            "u1.firstname as recruiter_firstname, u1.lastname as recruiter_lastname, " +
            "u2.firstname as employer_firstname, u2.lastname as employer_lastname, " +
            "feedback.id as feedback_id, feedback.employer_feedback, feedback.employer_time, " +
            "feedback.recruiter_feedback, feedback.recruiter_time " +
            "FROM deal " +
            "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
            "INNER JOIN recruiter ON recruiter.id=deal.recruiter_id " +
            "INNER JOIN employer ON employer.id = vacancy.employer_id " +
            "INNER JOIN bid ON bid.id = deal.bid_id " +
            "INNER JOIN user u1 ON recruiter.user_id=u1.id " +
            "INNER JOIN user u2 ON employer.user_id=u2.id " +
            "LEFT JOIN feedback ON feedback.deal_id=deal.id " +
            "WHERE deal.id=#{dealId}")
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
            @Result(column = "test_file", property = "vacancy.testFile.id"),
            @Result(column = "bid_id", property = "bid.id"),
            @Result(column = "message", property = "bid.message"),
            @Result(column = "recruiter_id", property = "recruiter.id"),
            @Result(column = "recruiter_firstname", property = "recruiter.user.firstName"),
            @Result(column = "recruiter_lastname", property = "recruiter.user.lastName"),
            @Result(column = "feedback_id", property = "feedback.id"),
            @Result(column = "recruiter_feedback", property = "feedback.recruiterFeedback"),
            @Result(column = "recruiter_time", property = "feedback.recruiterTime"),
            @Result(column = "employer_feedback", property = "feedback.employerFeedback"),
            @Result(column = "employer_time", property = "feedback.employerTime"),
            @Result(property = "applicants", column = "id", javaType = List.class,
                    many = @Many(select = "com.recruiters.repository.mapper.ApplicantMapper.findApplicantsByDealId"))
    })
    Deal findById(@Param(value = "dealId") final Long dealId);

    @Select("SELECT deal.id, deal.status, " +
            "vacancy.id as vacancy_id,  vacancy.employer_id, vacancy.title, " +
            "vacancy.description, vacancy.salary_from, vacancy.salary_to, " +
            "vacancy.creation_date, " +
            "recruiter.id as recruiter_id, " +
            "user.firstname, user.lastname, " +
            "(SELECT COUNT(applicant.id) FROM applicant " +
            "WHERE deal_id=deal.id) as all_applicants, " +
            "(SELECT COUNT(applicant.id) FROM applicant " +
            "WHERE deal_id=deal.id AND viewed=1) as viewed_applicants, " +
            "(SELECT COUNT(applicant.id) FROM applicant " +
            "WHERE deal_id=deal.id AND viewed=0) as unseen_applicants, " +
            "(SELECT COUNT(applicant.id) FROM applicant " +
            "WHERE deal_id=deal.id AND status=\"REJECTED\") as rejected_applicants, " +
            "max_updated.max_updated_date " +
            "FROM deal " +
            "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=deal.recruiter_id " +
            "INNER JOIN user ON recruiter.user_id=user.id " +
            "INNER JOIN (SELECT id, MAX(updated_date) as max_updated_date FROM " +
            "(SELECT id,updated_date FROM deal WHERE recruiter_id=#{recruiterId} " +
            "UNION ALL " +
            "SELECT deal.id, vacancy.updated_date FROM vacancy " +
            "INNER JOIN deal ON deal.vacancy_id=vacancy.id " +
            "WHERE deal.recruiter_id=#{recruiterId} " +
            "UNION ALL " +
            "SELECT deal.id, MAX(applicant.updated_date)  FROM applicant " +
            "INNER JOIN deal ON applicant.deal_id=deal.id " +
            "WHERE deal.recruiter_id=#{recruiterId})as updated_dates GROUP BY id) as max_updated " +
            "ON max_updated.id=deal.id " +
            "WHERE recruiter.id=#{recruiterId} AND " +
            "deal.status IN(\"IN_PROGRESS\", \"FIRED\", \"APPROVED\") AND " +
            "deal.recruiter_archived = 0")
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

    @SelectProvider(type = DealFilteredProvider.class, method = "selectDealsFiltered")
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
            @Result(column = "bid_id", property = "bid.id"),
            @Result(column = "message", property = "bid.message"),
            @Result(column = "bids", property = "bidCount"),
            @Result(column = "all_applicants", property = "allApplicantCount"),
            @Result(column = "viewed_applicants", property = "viewedApplicantCount"),
            @Result(column = "unseen_applicants", property = "unseenApplicantCount"),
            @Result(column = "rejected_applicants", property = "rejectedApplicantCount"),
            @Result(column = "max_updated_date", property = "lastModified")
})
    List<Deal> findFilteredDealsByEmployerId(
            @Param("employerId") final Long employerId,
            @Param("dealListSpecification") final DealListSpecification dealListSpecification
    );

    @Insert("INSERT INTO deal (vacancy_id, recruiter_id, bid_id, status) " +
            "SELECT v.id, b.recruiter_id, #{bidId}, \"IN_PROGRESS\" FROM vacancy v " +
            "INNER JOIN bid b on v.id = b.vacancy_id " +
            "WHERE b.id = #{bidId}")
    @Options(useGeneratedKeys = true, keyProperty = "deal.id", keyColumn = "id")
    void create(@Param(value = "bidId") final Long bidId, @Param(value = "deal") final Deal deal);

    @Update("UPDATE deal SET status = #{status} WHERE id = #{dealId} ")
    void updateStatus(@Param(value = "dealId") final Long dealId, @Param(value = "status") final DealStatus status);

    @Update("UPDATE deal SET status = \"FIRED\" WHERE id = #{dealId} ")
    void fireRecruiter(final Long dealId);

    @Update("UPDATE deal SET recruiter_archived = 1 " +
            "WHERE recruiter_id = #{recruiterId}  AND recruiter_archived = 0 " +
            "AND status = \"FIRED\"")
    void clearFiredByRecruiterId(final Long recruiterId);

    @Update("UPDATE deal SET recruiter_archived = 1 " +
            "WHERE recruiter_id = #{recruiterId}  AND recruiter_archived = 0 " +
            "AND status = \"APPROVED\"")
    void clearApprovedByRecruiterId(final Long recruiterId);
}