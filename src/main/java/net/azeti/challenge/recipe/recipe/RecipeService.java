package net.azeti.challenge.recipe.recipe;

import lombok.extern.slf4j.Slf4j;
import net.azeti.challenge.recipe.recipe.model.Recipe;
import net.azeti.challenge.recipe.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private WeatherService weatherService;

    private static final Double UPPER_LIMIT_TEMPERATURE = 30.0;
    private static final Double LOWER_LIMIT_TEMPERATURE = 0.0;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> searchRecipesByUsername(String username) {
        return recipeRepository.findByUsernameContainingIgnoreCase(username);
    }

    public List<Recipe> searchRecipesByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Recipe> updateRecipe(Long id, Recipe newRecipe) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(newRecipe.getTitle());
                    recipe.setDescription(newRecipe.getDescription());
                    recipe.setIngredients(newRecipe.getIngredients());
                    recipe.setInstructions(newRecipe.getInstructions());
                    recipe.setServings(newRecipe.getServings());
                    return recipeRepository.save(recipe);
                })
        ;
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getRecommendedRecipesBasedOnWeather(String city) {
        log.info("fetching weather data");
        return filterRecipesBasedOnTemperature(weatherService.getCurrentTemperature(city));
    }

    public List<Recipe> filterRecipesBasedOnTemperature(Double temperature) {
        if (temperature > UPPER_LIMIT_TEMPERATURE) {
            return getAllRecipes().stream()
                    .filter(recipe -> !recipe.recipeRequiresBaking())
                    .collect(Collectors.toList());
        } else if (temperature < LOWER_LIMIT_TEMPERATURE) {
            return getAllRecipes().stream()
                    .filter(recipe -> !recipe.recipeUsesFrozenIngredients())
                    .collect(Collectors.toList());
        } else {
            return getAllRecipes();
        }
    }
}
