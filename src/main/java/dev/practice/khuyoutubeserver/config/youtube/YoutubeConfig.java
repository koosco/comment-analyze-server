package dev.practice.khuyoutubeserver.config.youtube;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeConfig {

    private static final String APPLICATION_NAME = "spring-youtube-api";
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Bean
    public YouTube youTube() {
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, request -> {})
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
}
