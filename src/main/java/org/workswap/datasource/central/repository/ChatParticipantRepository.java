package org.workswap.datasource.central.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.chat.Chat;
import org.workswap.datasource.central.model.chat.ChatParticipant;

import jakarta.annotation.Nullable;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    // Найти запись по пользователю и разговору
    ChatParticipant findByUserAndChat(User user, Chat chat);

    // Найти все записи по пользователю
    List<ChatParticipant> findAllByUser(User user);

    // Найти все разговоры по пользователю (альтернатива через @Query)
    @Query("SELECT cp.chat FROM ChatParticipant cp WHERE cp.user = :user")
    List<Chat> findAllChatsByUser(@Param("user") User user);

    @Query("SELECT c FROM Chat c " +
       "WHERE (:listing IS NULL AND c.listing IS NULL OR c.listing = :listing) " +
       "AND SIZE(c.participants) = 2 " +
       "AND EXISTS (SELECT 1 FROM c.participants p WHERE p.user = :user1) " +
       "AND EXISTS (SELECT 1 FROM c.participants p WHERE p.user = :user2)")
    Optional<Chat> findChatBetweenUsers(
            @Param("user1") User user1,
            @Param("user2") User user2,
            @Param("listing") @Nullable Listing listing); 

    // Проверка существования разговора между двумя пользователями
    @Query("""
        SELECT COUNT(cp1) > 0 FROM ChatParticipant cp1
        JOIN ChatParticipant cp2 ON cp1.chat = cp2.chat
        WHERE cp1.user = :user1 AND cp2.user = :user2
        GROUP BY cp1.chat
        HAVING COUNT(cp1.chat.participants) = 2
    """)
    boolean existsBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

    // Все участники разговоров по объявлению
    @Query("SELECT cp FROM ChatParticipant cp WHERE cp.chat.listing = :listing")
    List<ChatParticipant> findAllByListing(@Param("listing") Listing listing);
}