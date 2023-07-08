package net.azeti.challenge.recipe.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private WeatherService weatherService;

    @BeforeEach
    public void setup() {

        weatherService = new WeatherService(restTemplate);
    }

    @Test
    void shouldGetCurrentTemperature() {
        // Mock the JSON response from the external API
        WeatherResponse mockResponse = new WeatherResponse();
        CurrentWeather currentWeather = new CurrentWeather();
        double temp = 293.15;
        currentWeather.setTemp(temp);
        mockResponse.setCurrent(currentWeather);
        when(restTemplate.getForObject(anyString(), Mockito.eq(WeatherResponse.class))).thenReturn(mockResponse);

        double currentTemperature = weatherService.getCurrentTemperature("Berlin");

        assertEquals(temp, currentTemperature);
    }


    @Test
    public void shouldThrowForWeatherServiceException() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        WeatherService weatherService = new WeatherService(restTemplateMock);
        String invalidLocation = "invalidLocation";

        when(restTemplateMock.getForObject(
                "http://api.openweathermap.org/data/2.5/weather?q=" + invalidLocation + "&units=metric&appid=",
                WeatherResponse.class))
                .thenThrow(HttpClientErrorException.class);

        assertThrows(WeatherServiceException.class, () -> weatherService.getCurrentTemperature(invalidLocation));
    }
}