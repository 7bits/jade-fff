package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.model.Recruiter;
import com.recruiters.model.Vacancy;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository for working with vacancy
 */
@Repository
public class VacancyRepository {

    /** Id of 1st vacancy */
    static final Long VACANCY1_ID = 1L;
    /** Title of 1st vacancy */
    static final String VACANCY1_TITLE = "Программист";
    /** Description of 1st vacancy */
    static final String VACANCY1_SHORT_DESCRIPTION = "PHP guru";
    /** Date of 1st vacancy */
    static final Date VACANCY1_DATE = new Date();

    /** Id of 2nd vacancy */
    static final Long VACANCY2_ID = 2L;
    /** Title of 2nd vacancy */
    static final String VACANCY2_TITLE = "Лесоруб";
    /** Description of 2nd vacancy */
    static final String VACANCY2_SHORT_DESCRIPTION = "Уметь клёво рубить сосны";
    /** Date of 2nd vacancy */
    static final Date VACANCY2_DATE = new Date();

    /** Id of 3rd vacancy */
    static final Long VACANCY3_ID = 3L;
    /** Title of 3rd vacancy */
    static final String VACANCY3_TITLE = "Сантехник";
    /** Description of 3rd vacancy */
    static final String VACANCY3_SHORT_DESCRIPTION = "Не пить!";
    /** Date of 3rd vacancy */
    static final Date VACANCY3_DATE = new Date();

    @Autowired
    private EmployerRepository employerRepository = null;

    /**
     * Method return vacancy by id
     * @param vacancyId
     * @return
     */
    public Vacancy getById(final Long vacancyId) {

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, employerRepository.getById(1L), VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, employerRepository.getById(1L), VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, employerRepository.getById(1L), VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        if (vacancyId.equals(1L)) {
            return vacancy1;
        }
        if (vacancyId.equals(2L)) {
            return vacancy2;
        }
        if (vacancyId.equals(3L)) {
            return vacancy3;
        }

        return null;
    }

    /**
     * Method must return all vacancies for this recruiter
     * @return
     */
    public List<Vacancy> findListOfAvailableVacancies() {
        List <Vacancy> vacancies = new ArrayList<Vacancy>();

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, employerRepository.getById(1L), VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, employerRepository.getById(1L), VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, employerRepository.getById(1L), VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        vacancies.add(vacancy1);
        vacancies.add(vacancy2);
        vacancies.add(vacancy3);

        return vacancies;
    }

    public Boolean updateVacancy(final Recruiter recruiter, final String message) {

        return true;
    }

    /**
     * Find vacancies of exact employer by its id
     * @param employerId    Id of employer
     * @return List of vacancies
     */
    public List<Vacancy> findEmployerVacancies(final Long employerId) {

        Vacancy vacancy1 = new Vacancy(VACANCY1_ID, employerRepository.getById(1L), VACANCY1_TITLE, VACANCY1_SHORT_DESCRIPTION, VACANCY1_DATE);
        Vacancy vacancy2 = new Vacancy(VACANCY2_ID, employerRepository.getById(1L), VACANCY2_TITLE, VACANCY2_SHORT_DESCRIPTION, VACANCY2_DATE);
        Vacancy vacancy3 = new Vacancy(VACANCY3_ID, employerRepository.getById(1L), VACANCY3_TITLE, VACANCY3_SHORT_DESCRIPTION, VACANCY3_DATE);
        List<Vacancy> vacancyList = new ArrayList<Vacancy>();

        if (employerId.equals(1L)) {
            vacancyList.add(vacancy1);
            vacancyList.add(vacancy2);
            vacancyList.add(vacancy3);
            return vacancyList;
        }

        return null;
    }

    /**
     * Find vacancies of exact employer by its id
     * with count of bids for each
     * @param employerId    Id of employer
     * @return List of vacancies
     */
    public List<Vacancy> findEmployerVacanciesWithBidCount(final Long employerId) {

        BidRepository bidRepository = new BidRepository();
        List<Vacancy> vacancies = findEmployerVacancies(employerId);
        List<Bid> bids  = bidRepository.findAllActiveByEmployerId(employerId);

        Map<Long, Long> bidCount = new HashMap<Long, Long>();
        for (Bid bid: bids) {
            Long vacancyId = bid.getVacancy().getId();
            if (bidCount.get(vacancyId) == null) {
                bidCount.put(vacancyId, 1L);
            } else {
                bidCount.put(vacancyId, bidCount.get(vacancyId) + 1L);
            }
        }

        for (Vacancy vacancy: vacancies) {
            if (bidCount.get(vacancy.getId()) != null) {
                vacancy.setBidCount(bidCount.get(vacancy.getId()));
            }
        }

        return vacancies;
    }

    public EmployerRepository getEmployerRepository() {
        return employerRepository;
    }

    public void setEmployerRepository(final EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }
}