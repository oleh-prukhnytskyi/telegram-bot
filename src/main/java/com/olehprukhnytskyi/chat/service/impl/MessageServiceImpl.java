package com.olehprukhnytskyi.chat.service.impl;

import com.olehprukhnytskyi.chat.model.Message;
import com.olehprukhnytskyi.chat.repository.MessageRepository;
import com.olehprukhnytskyi.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;

    @Autowired
    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }
}
