package com.olehprukhnytskyi.chat.service;

import com.olehprukhnytskyi.chat.model.Chat;
import java.util.List;

public interface ChatService {
    void save(Chat chat);

    Chat findByChatId(String chatId);

    List<Chat> findAll();
}
