package com.we.travelplanner.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;

@Configuration
public class AppConfig {

    /**
     * @return OpenAiService
     */
    // config for openai api key and timeout duration

    @Bean
    public OpenAiService openAiService() {

        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("OPENAI_KEY");

        if (token == null) {
            throw new IllegalArgumentException("API_KEY not set in the environment variables.");
        }

        // added timeout duration of 60s
        return new OpenAiService(token, Duration.ofSeconds(60));
    }
}
