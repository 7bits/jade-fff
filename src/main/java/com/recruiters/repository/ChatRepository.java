package com.recruiters.repository;

import com.recruiters.model.ChatMessage;
import com.recruiters.repository.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementing all methods related to
 * ChatMessage Entity manipulations with MyBatis
 */
@Repository
public class ChatRepository {

    /** MyBatis Chat Mapper */
    @Autowired
    private ChatMapper chatMapper = null;

    /**
     * Return list of messages related to exact deal since message
     * @param dealId    Deal id
     * @param messageId Message id
     * @return list of new messages
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public List<ChatMessage> findByDealIdSinceId(final Long dealId, final Long messageId)
            throws RepositoryException {
        if (dealId ==  null) {
            throw new RepositoryException("dealId is null");
        }
        try {
            if (messageId == null) {

                return chatMapper.findByDealIdSinceId(dealId, 0L);
            }

            return chatMapper.findByDealIdSinceId(dealId, messageId);
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    /**
     * Create new message
     * @param chatMessage    Chat Message
     * @return Id of created message if success
     * @throws RepositoryException if input parameters are incorrect or there
     * were any technical issues
     */
    public ChatMessage create(final ChatMessage chatMessage) throws RepositoryException {
        if (chatMessage ==  null) {
            throw new RepositoryException("chatMessage is null");
        }
        try {
            chatMapper.create(chatMessage);

            return chatMessage;
        } catch (Exception e) {
            throw new RepositoryException("General database error: ", e);
        }
    }

    public ChatMapper getChatMapper() {
        return chatMapper;
    }

    public void setChatMapper(final ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }
}
