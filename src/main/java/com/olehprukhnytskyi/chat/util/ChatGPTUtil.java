package com.olehprukhnytskyi.chat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ChatGPTUtil {
    @Value("${openai.key}")
    private String API_KEY = "";
    @Value("${openai.model}")
    private String MODEL = "";
    @Value("${openai.url}")
    private String CHAT_URL = "";

    public String sendMessage(String prompt) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(CHAT_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            String.format("{\"model\": \"%s\", \"messages\":" +
                                            " [{\"role\": \"user\", \"content\": \"%s\"}]}",
                                    MODEL, prompt)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return extractMessageFromJSONResponse(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractMessageFromJSONResponse(String response) {
        try {
            return new ObjectMapper().readTree(response)
                    .path("choices")
                    .path(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            throw new RuntimeException("Can't extract message", e);
        }
    }
}
