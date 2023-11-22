package com.olehprukhnytskyi.chat.service.impl;

import com.olehprukhnytskyi.chat.model.Chat;
import com.olehprukhnytskyi.chat.repository.ChatRepository;
import com.olehprukhnytskyi.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository repository;

    @Autowired
    public ChatServiceImpl(ChatRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Chat chat) {
        repository.save(chat);
    }

    @Override
    public Chat findByChatId(String chatId) {
        return repository.findByChatId(chatId);
    }

    @Override
    public List<Chat> findAll() {
        return repository.findAll();
    }
}
