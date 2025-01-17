package com.valkov.fishingfarm.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${whether.api-key}")
    private String API_KEY;
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

    public JsonNode getWeather() {
        double longitude = 27.716726;
        double latitude = 43.402563;
        String url = String.format(WEATHER_URL, latitude, longitude, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(response);
        } catch (Exception e) {
            return null;
        }
    }
}
