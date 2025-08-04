package org.workswap.datasource.central.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.central.model.Listing;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.chat.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    // Найти все разговоры пользователя
    @Query("SELECT cp.chat FROM ChatParticipant cp " +
       "JOIN FETCH cp.chat.participants p " +
       "WHERE cp.user = :user")
    List<Chat> findAllByParticipant(@Param("user") User user);

    List<Chat> findAllByListing(Listing listing);
}
