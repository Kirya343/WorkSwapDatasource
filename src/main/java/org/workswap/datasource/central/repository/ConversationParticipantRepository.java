package org.workswap.datasource.central.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.chat.Conversation;
import org.workswap.datasource.central.model.chat.ConversationParticipant;

import jakarta.annotation.Nullable;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {

    // Найти запись по пользователю и разговору
    ConversationParticipant findByUserAndConversation(User user, Conversation conversation);

    // Найти все записи по пользователю
    List<ConversationParticipant> findAllByUser(User user);

    // Найти все разговоры по пользователю (альтернатива через @Query)
    @Query("SELECT cp.conversation FROM ConversationParticipant cp WHERE cp.user = :user")
    List<Conversation> findAllConversationsByUser(@Param("user") User user);

    @Query("SELECT c FROM Conversation c " +
       "WHERE (:listing IS NULL AND c.listing IS NULL OR c.listing = :listing) " +
       "AND SIZE(c.participants) = 2 " +
       "AND EXISTS (SELECT 1 FROM c.participants p WHERE p.user = :user1) " +
       "AND EXISTS (SELECT 1 FROM c.participants p WHERE p.user = :user2)")
    Optional<Conversation> findConversationBetweenUsers(
            @Param("user1") User user1,
            @Param("user2") User user2,
            @Param("listing") @Nullable Listing listing); 

    // Проверка существования разговора между двумя пользователями
    @Query("""
        SELECT COUNT(cp1) > 0 FROM ConversationParticipant cp1
        JOIN ConversationParticipant cp2 ON cp1.conversation = cp2.conversation
        WHERE cp1.user = :user1 AND cp2.user = :user2
        GROUP BY cp1.conversation
        HAVING COUNT(cp1.conversation.participants) = 2
    """)
    boolean existsBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

    // Все участники разговоров по объявлению
    @Query("SELECT cp FROM ConversationParticipant cp WHERE cp.conversation.listing = :listing")
    List<ConversationParticipant> findAllByListing(@Param("listing") Listing listing);
}