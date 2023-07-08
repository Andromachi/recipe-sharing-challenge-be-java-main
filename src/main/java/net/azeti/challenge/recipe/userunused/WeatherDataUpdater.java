//package net.azeti.challenge.recipe.weather;
//
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class WeatherDataUpdater {
////TODO: remove
//    private static final Logger logger = LoggerFactory.getLogger(WeatherDataUpdater.class);
//
//    //TODO: immutable
//    private double currentTemperature;
//
//    @Autowired
//    private WeatherService weatherService;
//
//
//    @Scheduled(fixedRate = 86400000) // 24 hours in milliseconds
//    public void updateWeatherData() {
//        currentTemperature = weatherService.getCurrentTemperature("Berlin");
//        logger.info("Updated weather data. Current temperature in Berlin: {}", currentTemperature);
//
//    }
//
//    public double getCurrentTemperature() {
//        return currentTemperature;
//    }
//}