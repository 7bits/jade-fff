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

    public List<Bid> findAllActiveByEmployerId(final Long employerId) {

        if (employerId.equals(1L)) {
            List <Bid> bids = new ArrayList<Bid>();
            VacancyRepository vacancyRepository = new VacancyRepository();
            RecruiterRepository recruiterRepository = new RecruiterRepository();

            Bid bid1 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(1L));
            Bid bid2 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(2L));
            Bid bid3 = new Bid(1L, vacancyRepository.getById(1L), recruiterRepository.getByUserId(3L));
            Bid bid4 = new Bid(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(1L));
            Bid bid5 = new Bid(2L, vacancyRepository.getById(2L), recruiterRepository.getByUserId(2L));


            bids.add(bid1);
            bids.add(bid2);
            bids.add(bid3);
            bids.add(bid4);
            bids.add(bid5);

            return bids;
        }

        return null;
    }
}
