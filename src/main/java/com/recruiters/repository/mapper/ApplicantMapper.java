package com.recruiters.repository.mapper;

import com.recruiters.model.Applicant;
import com.recruiters.model.status.ApplicantStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper for Applicant
 */
public interface ApplicantMapper {

    @Select("SELECT applicant.*, " +
            "vacancy.id as vacancy_id, " +
            "vacancy.employer_id, " +
            "recruiter.id as recruiter_id, " +
            "deal.status as deal_status " +
            "FROM applicant " +
            "INNER JOIN deal ON deal.id=applicant.deal_id " +
            "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=deal.recruiter_id " +
            "WHERE applicant.id=#{applicantId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "description", property = "description"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "resume_file", property = "resumeFile.id"),
            @Result(column = "test_answer_file", property = "testAnswerFile.id"),
            @Result(column = "status", property = "status"),
            @Result(column = "deal_id", property = "deal.id"),
            @Result(column = "vacancy_id", property = "deal.vacancy.id"),
            @Result(column = "employer_id", property = "deal.vacancy.employer.id"),
            @Result(column = "recruiter_id", property = "deal.recruiter.id"),
            @Result(column = "deal_status", property = "deal.status")
    })
    Applicant findById(final Long applicantId);

    @Select("SELECT applicant.*, " +
            "vacancy.id as vacancy_id, " +
            "vacancy.employer_id, " +
            "recruiter.id as recruiter_id " +
            "FROM applicant " +
            "INNER JOIN deal ON deal.id=applicant.deal_id " +
            "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=deal.recruiter_id " +
            "WHERE applicant.deal_id=#{dealId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "description", property = "description"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "resume_file", property = "resumeFile.id"),
            @Result(column = "test_answer_file", property = "testAnswerFile.id"),
            @Result(column = "status", property = "status"),
            @Result(column = "deal_id", property = "deal.id"),
            @Result(column = "vacancy_id", property = "deal.vacancy.id"),
            @Result(column = "employer_id", property = "deal.vacancy.employer.id"),
            @Result(column = "recruiter_id", property = "deal.recruiter.id")
    })
    List<Applicant> findApplicantsByDealId(final Long dealId);

    @Insert("INSERT INTO applicant (deal_id, first_name, last_name, description, " +
            "sex, age, resume_file, test_answer_file) " +
            "VALUES (#{deal.id}, #{firstName}, #{lastName}, #{description}, " +
            "#{sex}, #{age}, #{resumeFile.id}, #{testAnswerFile.id})")
    void create(final Applicant applicant);

    @Update("UPDATE applicant SET first_name=#{firstName}, " +
            "last_name=#{lastName}, description=#{description}, " +
            "sex=#{sex}, age=#{age}, " +
            "resume_file=#{resumeFile.id}, test_answer_file=#{testAnswerFile.id} " +
            "WHERE id=#{id}")
    void update(final Applicant applicant);

    @Update("UPDATE applicant SET status=#{status} " +
            "WHERE id=#{applicantId}")
    void updateStatus(@Param("applicantId") final Long applicantId,
                      @Param("status") final ApplicantStatus applicantStatus);

    @Update("UPDATE applicant SET viewed = 1 " +
            "WHERE id=#{applicantId} AND viewed = 0")
    void setViewed(final Long applicantId);
}
