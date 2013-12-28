package com.recruiters.repository.mapper;

import com.recruiters.model.Applicant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
    Applicant getApplicantById(final Long applicantId);

    @Select("SELECT applicants.*, " +
            "vacancies.id as vacancy_id, " +
            "vacancies.employer_id, " +
            "recruiters.id as recruiter_id " +
            "FROM applicants " +
            "INNER JOIN deals ON deals.id=applicants.deal_id " +
            "INNER JOIN vacancies ON vacancies.id=deals.vacancy_id " +
            "INNER JOIN recruiters  ON recruiters.id=deals.recruiter_id " +
            "WHERE applicants.deal_id=#{dealId}")
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
    public List<Applicant> getApplicantByDealId(final Long dealId);

    //TODO
    Boolean saveApplicant(final Applicant applicant);
}
