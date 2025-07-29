package org.workswap.datasource.central.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.chat.Conversation;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    // Найти все разговоры пользователя
    @Query("SELECT cp.conversation FROM ConversationParticipant cp " +
       "JOIN FETCH cp.conversation.participants p " +
       "WHERE cp.user = :user")
    List<Conversation> findAllByParticipant(@Param("user") User user);

    List<Conversation> findAllByListing(Listing listing);
}
