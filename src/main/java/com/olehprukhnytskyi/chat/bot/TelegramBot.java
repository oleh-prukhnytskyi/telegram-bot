package com.olehprukhnytskyi.chat.bot;

import com.olehprukhnytskyi.chat.model.Chat;
import com.olehprukhnytskyi.chat.model.Message;
import com.olehprukhnytskyi.chat.service.ChatService;
import com.olehprukhnytskyi.chat.service.RoleService;
import com.olehprukhnytskyi.chat.util.ChatGPTUtil;
import com.olehprukhnytskyi.chat.util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String BOT_NAME = "";
    private final ChatGPTUtil chatGPTUtil;
    private final ChatService chatService;
    private final RoleService roleService;

    @Autowired
    public TelegramBot(@Value("${bot.token}") String botToken,
                       ChatGPTUtil chatGPTUtil, ChatService chatService, RoleService roleService) {
        super(botToken);
        this.chatGPTUtil = chatGPTUtil;
        this.chatService = chatService;
        this.roleService = roleService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String messageText = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();

        Chat chat = chatService.findByChatId(chatId);
        Message userMessage = new Message();
        userMessage.setMessage(messageText);
        userMessage.setTimestamp(LocalDateTime.now());
        userMessage.setRole(roleService.findByRoleName(RoleName.USER));
        if (chat == null) {
            chat = new Chat();
            chat.setChatId(chatId);
            chat.setMessages(List.of(userMessage));
            chat.setUsername(update.getMessage().getFrom().getUserName());
        } else {
            chat.getMessages().add(userMessage);
        }
        chatService.save(chat);
        sendMessage(chatId, chatGPTUtil.sendMessage(messageText));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    public void sendMessage(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);

            Chat chat = chatService.findByChatId(chatId);
            Message botMessage = new Message();
            botMessage.setMessage(messageText);
            botMessage.setTimestamp(LocalDateTime.now());
            botMessage.setRole(roleService.findByRoleName(RoleName.BOT));
            chat.getMessages().add(botMessage);
            chatService.save(chat);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't send message", e);
        }
    }
}
