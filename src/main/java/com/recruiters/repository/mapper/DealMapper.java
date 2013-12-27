package com.recruiters.repository.mapper;

import com.recruiters.model.Deal;
import com.recruiters.model.Recruiter;
import com.recruiters.repository.ApplicantRepository;
import com.recruiters.repository.EmployerRepository;
import com.recruiters.repository.VacancyRepository;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public interface DealMapper {

    @Select("SELECT * FROM deals WHERE id = #{dealId}")
    Deal getById(final Long dealId);

    List<Deal> findAllActiveByRecruiterId(final Long recruiterId);

    Deal findByRecruiterIdAndVacancyId(final Long recruiterId, final Long vacancyId);

    List<Deal> findAllActiveByEmployerId(final Long employerId);
}