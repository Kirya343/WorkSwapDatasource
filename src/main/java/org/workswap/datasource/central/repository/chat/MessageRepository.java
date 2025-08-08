package org.workswap.datasource.central.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workswap.datasource.central.model.User;
import org.workswap.datasource.central.model.chat.Chat;
import org.workswap.datasource.central.model.chat.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Получить все непрочитанные сообщения для пользователя
    List<Message> findByReceiverAndReadFalse(User receiver);

    // Получить все непрочитанные сообщения для пользователя в конкретном разговоре
    List<Message> findByChatAndReceiverAndReadFalse(Chat chat, User receiver);

    List<Message> findByChatOrderBySentAtAsc(Chat chat);

    // Новый метод: получить сообщения по ID разговора (с сортировкой по времени)
    Page<Message> findByChatIdOrderBySentAtDesc(Long chatId, Pageable pageable);

    @Modifying
    @Query("UPDATE Message m SET m.read = true WHERE m.chat.id = :chatId AND m.receiver.id = :userId AND m.read = false")
    void markMessagesAsRead(@Param("chatId") Long chatId, @Param("userId") Long userId);
}


