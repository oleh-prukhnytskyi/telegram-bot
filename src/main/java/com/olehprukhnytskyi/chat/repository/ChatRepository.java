package com.olehprukhnytskyi.chat.repository;

import com.olehprukhnytskyi.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByChatId(String chatId);
}
