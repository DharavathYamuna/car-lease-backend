package com.carai.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class AIService {

    private final String API_URL = "https://api-inference.huggingface.co/models/facebook/bart-large-cnn";
    private final String API_KEY = "YOUR_API_KEY";

    public String summarize(String text) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String safeText = text.length() > 500 ? text.substring(0, 500) : text;

            String json = "{\"inputs\":\"Summarize this:\n" + safeText.replace("\"", "'") + "\"}";

            HttpEntity<String> request = new HttpEntity<>(json, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(API_URL, request, String.class);

            String result = response.getBody();

            if (result != null && result.contains("summary_text")) {
                return result.split("\"summary_text\":\"")[1].split("\"")[0];
            }

            return "AI not responding";

        } catch (Exception e) {
            return "Summary unavailable";
        }
    }
}