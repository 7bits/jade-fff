package com.recruiters.repository;

import com.recruiters.model.Feedback;
import com.recruiters.repository.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementing all methods related to
 * Feedback Entity manipulations with MyBatis
 */
@Repository
public class FeedbackRepository {

    /** MyBatis Feedback Mapper */
    @Autowired
    private FeedbackMapper feedbackMapper = null;

    /**
     * Return feedback related to exact deal
     * @param dealId    Deal id
     * @return feedback
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Feedback findByDealId(final Long dealId)
            throws RepositoryException {
        if (dealId ==  null) {
            throw new RepositoryException("dealId is null");
        }
        try {

            return feedbackMapper.findByDealId(dealId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Create new empty feedback for certain deal
     * @param feedback    Feedback with deal id, employer id, recruiter id fields filled
     * @return Feedback with id filled
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Feedback create(final Feedback feedback) throws RepositoryException {
        if (feedback ==  null) {
            throw new RepositoryException("feedback is null");
        }
        try {
            feedbackMapper.create(feedback);

            return feedback;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update recruiter feedback for certain deal
     * @param dealId               Deal id
     * @param recruiterFeedback    Recruiter feedback
     * @return Deal id if everything goes right
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long updateRecruiterFeedback(
            final Long dealId,
            final String recruiterFeedback
    ) throws RepositoryException {
        if (dealId ==  null || recruiterFeedback == null) {
            throw new RepositoryException("dealId or recruiterFeedback is null");
        }
        try {
            feedbackMapper.updateRecruiterFeedback(dealId, recruiterFeedback);

            return dealId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Update employer feedback for certain deal
     * @param dealId               Deal id
     * @param employerFeedback     Employer feedback
     * @return Deal id if everything goes right
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public Long updateEmployerFeedback(
            final Long dealId,
            final String employerFeedback
    ) throws RepositoryException {
        if (dealId ==  null || employerFeedback == null) {
            throw new RepositoryException("dealId or employerFeedback is null");
        }
        try {
            feedbackMapper.updateEmployerFeedback(dealId, employerFeedback);

            return dealId;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }
    public FeedbackMapper getFeedbackMapper() {
        return feedbackMapper;
    }

    public void setFeedbackMapper(final FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }
}
