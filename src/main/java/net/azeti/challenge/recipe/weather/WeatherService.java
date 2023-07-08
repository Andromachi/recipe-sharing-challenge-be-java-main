package net.azeti.challenge.recipe.weather;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getCurrentTemperature(String location) {
        //TODO: maybe this inside the try as well
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s", location, apiKey);
        //TODO: remove
        //TODO: fix
        try {
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);
            if (weatherResponse != null && weatherResponse.getCurrent() != null) {
                double currentTemperature = weatherResponse.getCurrent().getTemp();
                log.info("Updated weather data. Current temperature in {} : {}", location, currentTemperature);
                return currentTemperature;
            }
        }
        catch (Exception e) {
            throw new WeatherServiceException("Failed to parse weather data for location: " + location);
        }
        throw new WeatherServiceException("Failed to parse weather data for location: " + location);
    }

}

