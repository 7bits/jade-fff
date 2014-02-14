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
            "WHERE applicant.deal_id=#{dealId} " +
            "ORDER BY applicant.viewed")
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

    @Select("SELECT applicant.id, applicant.first_name, applicant.last_name, applicant.creation_date, " +
            "deal.id as deal_id, " +
            "vacancy.id as vacancy_id, vacancy.title as vacancy_title, " +
            "recruiter.id as recruiter_id, " +
            "user.firstname as recruiter_first_name, user.lastname as recruiter_last_name " +
            "FROM applicant " +
            "INNER JOIN deal ON deal.id=applicant.deal_id " +
            "INNER JOIN vacancy ON vacancy.id=deal.vacancy_id " +
            "INNER JOIN recruiter  ON recruiter.id=deal.recruiter_id " +
            "INNER JOIN user ON user.id = recruiter.user_id " +
            "WHERE vacancy.employer_id=#{employerId} AND applicant.viewed=0 " +
            "ORDER BY applicant.creation_date DESC ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName"),
            @Result(column = "creation_date", property = "dateCreated"),
            @Result(column = "deal_id", property = "deal.id"),
            @Result(column = "vacancy_id", property = "deal.vacancy.id"),
            @Result(column = "vacancy_title", property = "deal.vacancy.title"),
            @Result(column = "recruiter_id", property = "deal.recruiter.id"),
            @Result(column = "recruiter_first_name", property = "deal.recruiter.user.firstName"),
            @Result(column = "recruiter_last_name", property = "deal.recruiter.user.lastName")
    })
    List<Applicant> findNewApplicantsForEmployer(final Long employerId);

    @Insert("INSERT INTO applicant (deal_id, first_name, last_name, description, " +
            "sex, age, resume_file, test_answer_file, creation_date) " +
            "VALUES (#{deal.id}, #{firstName}, #{lastName}, #{description}, " +
            "#{sex}, #{age}, #{resumeFile.id}, #{testAnswerFile.id}, #{dateCreated})")
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
