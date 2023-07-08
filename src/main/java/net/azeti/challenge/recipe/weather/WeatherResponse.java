package net.azeti.challenge.recipe.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    @JsonProperty("main")
    private CurrentWeather current;

}
