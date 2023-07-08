package net.azeti.challenge.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class RecipeSharingChallengeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeSharingChallengeJavaApplication.class, args);
    }

}
