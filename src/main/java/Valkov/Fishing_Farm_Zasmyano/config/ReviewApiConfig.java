package Valkov.Fishing_Farm_Zasmyano.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@ConfigurationProperties(prefix = "review.api")
@Getter
@Setter
public class ReviewApiConfig {

private String baseUrl;

    @Bean()
    public RestClient reviewRestClient(ReviewApiConfig reviewApiConfig) {
        return RestClient
                .builder()
                .baseUrl(reviewApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
