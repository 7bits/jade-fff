package com.recruiters.repository.mapper;

import com.recruiters.model.Applicant;
import com.recruiters.model.ApplicantStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper for Applicant POJO
 */
public interface ApplicantMapper {

    @Select("SELECT applicants.*, " +
            "vacancies.id as vacancy_id, " +
            "vacancies.employer_id, " +
            "recruiters.id as recruiter_id " +
            "FROM applicants " +
            "INNER JOIN deals ON deals.id=applicants.deal_id " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=deals.recruiter_id " +
            "WHERE applicants.id=#{applicantId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "description", property = "description"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "resume_file", property = "resumeFile"),
            @Result(column = "test_answer_file", property = "testAnswerFile"),
            @Result(column = "status", property = "status"),
            @Result(column = "deal_id", property = "deal.id"),
            @Result(column = "vacancy_id", property = "deal.vacancy.id"),
            @Result(column = "employer_id", property = "deal.vacancy.employer.id"),
            @Result(column = "recruiter_id", property = "deal.recruiter.id")
    })
    Applicant findById(final Long applicantId);

    @Select("SELECT applicants.*, " +
            "vacancies.id as vacancy_id, " +
            "vacancies.employer_id, " +
            "recruiters.id as recruiter_id " +
            "FROM applicants " +
            "INNER JOIN deals ON deals.id=applicants.deal_id " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=deals.recruiter_id " +
            "WHERE applicants.deal_id=#{dealId} AND applicants.status=\"IN_PROGRESS\"")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "description", property = "description"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "resume_file", property = "resumeFile"),
            @Result(column = "test_answer_file", property = "testAnswerFile"),
            @Result(column = "status", property = "status"),
            @Result(column = "deal_id", property = "deal.id"),
            @Result(column = "vacancy_id", property = "deal.vacancy.id"),
            @Result(column = "employer_id", property = "deal.vacancy.employer.id"),
            @Result(column = "recruiter_id", property = "deal.recruiter.id")
    })
    List<Applicant> findApplicantsByDealId(final Long dealId);

    @Insert("INSERT INTO applicants (deal_id, first_name, last_name, description, " +
            "sex, age, resume_file, test_answer_file) " +
            "VALUES (#{deal.id}, #{firstName}, #{lastName}, #{description}, " +
            "#{sex}, #{age}, #{resumeFile}, #{testAnswerFile})")
    void create(final Applicant applicant);

    @Update("UPDATE applicants SET first_name=#{firstName}, " +
            "last_name=#{lastName}, description=#{description}, " +
            "sex=#{sex}, age=#{age}, " +
            "resume_file=#{resumeFile}, test_answer_file=#{testAnswerFile} " +
            "WHERE id=#{id}")
    void update(final Applicant applicant);

    @Update("UPDATE applicants SET status=#{status} " +
            "WHERE id=#{applicantId} and applicants.deal_id IN " +
            "(SELECT deals.id FROM deals " +
            "INNER JOIN vacancies ON vacancies.id = deals.vacancy_id " +
            "WHERE vacancies.employer_id=#{employerId})")
    void updateStatus(@Param("applicantId") final Long applicantId,
                      @Param("status") final ApplicantStatus applicantStatus,
                      @Param("employerId") final Long employerId);
}
