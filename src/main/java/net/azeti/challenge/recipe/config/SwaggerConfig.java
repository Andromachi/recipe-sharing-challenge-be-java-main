package net.azeti.challenge.recipe.config;
import io.swagger.v3.oas.models.OpenAPI;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Recipe Sharing Platform")
                                .contact(new Contact().email("avouimta@gmail.com"))
                                .description(
                                        "The API supports user registration, login, and profile management using secure JWT-based sessions. It also supports CRUD operations on recipes, and weather based recipe recommendation.")
                                .version("0.0.1"));
    }
}
