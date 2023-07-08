package net.azeti.challenge.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
//TODO; create two recipes with two different users
//TODO: create with similar names, partial name match
//TODO: check tables at H2
//Document tables
//TODO: use endpoints
//TODO: test weather
//TODO: expired jwt token
//Add some details to swagger
//Write readme

public class RecipeSharingChallengeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeSharingChallengeJavaApplication.class, args);
    }

}
