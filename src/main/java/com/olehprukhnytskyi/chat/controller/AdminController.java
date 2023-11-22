package com.olehprukhnytskyi.chat.controller;

import com.olehprukhnytskyi.chat.bot.TelegramBot;
import com.olehprukhnytskyi.chat.model.Chat;
import com.olehprukhnytskyi.chat.model.dto.MessageDto;
import com.olehprukhnytskyi.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Admin management", description = "Endpoints for managing chats")
@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final ChatService chatService;
    private final TelegramBot telegramBot;

    @GetMapping
    @Operation(summary = "Get all chats", description = "Fetches all chats")
    public String findAll(Model model) {
        model.addAttribute("pageTitle", "Chats");
        model.addAttribute("chats", chatService.findAll());
        return "admin";
    }

    @GetMapping("/chat/{chatId}")
    @Operation(summary = "Get a chat by id", description = "Fetches a chat by its id")
    public String findByChatId(Model model, @PathVariable String chatId) {
        Chat chat = chatService.findByChatId(chatId);
        model.addAttribute("pageTitle", chat.getUsername());
        model.addAttribute("chats", chatService.findAll());
        model.addAttribute("chat", chat);
        return "admin";
    }

    @PostMapping("/chat/{chatId}")
    @Operation(summary = "Send message", description = "Sends a message by chat id")
    public String sendMessage(@PathVariable String chatId, @ModelAttribute MessageDto message) {
        telegramBot.sendMessage(chatId, message.getMessage());
        return String.format("redirect:/admin/chat/%s", chatId);
    }
}
