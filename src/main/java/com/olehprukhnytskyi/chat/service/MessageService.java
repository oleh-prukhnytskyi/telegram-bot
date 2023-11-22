package com.olehprukhnytskyi.chat.service;

import com.olehprukhnytskyi.chat.model.Message;

public interface MessageService {
    Message save(Message message);
}
