package net.azeti.challenge.recipe.weather;

import org.springframework.web.client.ResourceAccessException;

public class WeatherServiceException extends RuntimeException {
    public WeatherServiceException(String message) {
        super(message);
    }
}
