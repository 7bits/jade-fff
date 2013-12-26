package com.recruiters.repository;

import com.recruiters.model.Bid;
import com.recruiters.model.Deal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for working with bid
 */
@Repository
public class BidRepository {
    /**
     * Method must return list of recruiter bids.
     * @return
     */
    public List<Bid> findListOfRecruiterBids(final Long recruiterId) {
        List <Bid> bids = new ArrayList<Bid>();
        VacancyRepository vacancyRepository = new VacancyRepository();
        RecruiterRepository recruiterRepository = new RecruiterRepository();

        Bid bid1 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L));
        Bid bid2 = new Bid(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L));
        Bid bid3 = new Bid(3L, vacancyRepository.getById(3L), recruiterRepository.getByUserId(1L));


        bids.add(bid1);
        bids.add(bid2);
        bids.add(bid3);

        return bids;
    }

    /**
     * Find all bids for vacancies of exact employer
     * @param employerId    Id of employer
     * @return List of Bid POJO instances
     */
    public List<Bid> findAllActiveByEmployerId(final Long employerId) {

        if (employerId.equals(1L)) {
            List <Bid> bids = new ArrayList<Bid>();
            VacancyRepository vacancyRepository = new VacancyRepository();
            RecruiterRepository recruiterRepository = new RecruiterRepository();

            Bid bid1 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L));
            Bid bid2 = new Bid(2L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(2L));
            Bid bid3 = new Bid(3L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(3L));
            Bid bid4 = new Bid(4L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L));
            Bid bid5 = new Bid(5L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(2L));


            bids.add(bid1);
            bids.add(bid2);
            bids.add(bid3);
            bids.add(bid4);
            bids.add(bid5);

            return bids;
        }

        return null;
    }

    /**
     * Get Bid by its id
     * @param bidId    Id of bid
     * @return Bid POJO instance
     */
    public Bid getBidById(final Long bidId) {

        VacancyRepository vacancyRepository = new VacancyRepository();
        RecruiterRepository recruiterRepository = new RecruiterRepository();

        Bid bid1 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L));
        bid1.setMessage("Условия такие условия, такие интересные условия");
        Bid bid2 = new Bid(2L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(2L));
        bid2.setMessage("Какие то другие условия.");
        Bid bid3 = new Bid(3L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(3L));
        bid3.setMessage("Условия Условия Условия Условия Условия Условия Условия Условия " +
                "Условия Условия Условия Условия Условия Условия Условия Условия Условия Условия ");
        Bid bid4 = new Bid(4L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L));
        bid4.setMessage("Кто не согласен с условиями тот не прав. Условия такие хорошие");
        Bid bid5 = new Bid(5L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(2L));
        bid5.setMessage("Грех не отказаться от условий под дулом пистолета");

        if (bidId.equals(1L)) {
            return bid1;
        } else if (bidId.equals(2L)) {
            return bid2;
        } else if (bidId.equals(3L)) {
            return bid3;
        } else if (bidId.equals(4L)) {
            return bid4;
        } else if (bidId.equals(5L)) {
            return bid5;
        }
        return null;
    }

    /**
     * Find all bids for exact vacancy
     * @param vacancyId    Vacancy Id
     * @return List of Bid POJO instances
     */
    public List<Bid> findAllBidsForVacancy(final Long vacancyId) {
        VacancyRepository vacancyRepository = new VacancyRepository();
        RecruiterRepository recruiterRepository = new RecruiterRepository();

        Bid bid1 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L));
        Bid bid2 = new Bid(2L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(2L));
        Bid bid3 = new Bid(3L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(3L));
        Bid bid4 = new Bid(4L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L));
        Bid bid5 = new Bid(5L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(2L));

        List<Bid> bids = new ArrayList<Bid>();
        if (vacancyId.equals(1L)) {
            bids.add(bid1);
            bids.add(bid2);
            bids.add(bid3);
            return bids;
        } else if (vacancyId.equals(2L)) {
            bids.add(bid4);
            bids.add(bid5);
            return bids;
        } else if (vacancyId.equals(3L)) {
            return bids;
        }
        return null;
    }
}
