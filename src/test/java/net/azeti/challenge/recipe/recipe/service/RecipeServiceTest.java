package net.azeti.challenge.recipe.recipe.service;

import net.azeti.challenge.recipe.recipe.RecipeRepository;
import net.azeti.challenge.recipe.recipe.model.Recipe;
import net.azeti.challenge.recipe.recipe.RecipeService;
import net.azeti.challenge.recipe.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void testFilterRecipesBasedOnTemperature() {
        Recipe recipe1 = mock(Recipe.class);
        Recipe recipe2 = mock(Recipe.class);
        Recipe recipe3 = mock(Recipe.class);

        when(recipe1.recipeRequiresBaking()).thenReturn(true);
        when(recipe1.recipeUsesFrozenIngredients()).thenReturn(false);

        when(recipe2.recipeRequiresBaking()).thenReturn(false);
        when(recipe2.recipeUsesFrozenIngredients()).thenReturn(true);

        when(recipe3.recipeRequiresBaking()).thenReturn(false);
        when(recipe3.recipeUsesFrozenIngredients()).thenReturn(false);


        List<Recipe> allRecipes = Arrays.asList(recipe1, recipe2, recipe3);
        when(recipeService.getAllRecipes()).thenReturn(allRecipes);

        double temperature = 35.0;
        List<Recipe> recipes = recipeService.filterRecipesBasedOnTemperature(temperature);

        assertEquals(2, recipes.size());
        assertFalse(recipes.contains(recipe1));

        double temperature1 = -5.0;
        recipes = recipeService.filterRecipesBasedOnTemperature(temperature1);

        assertEquals(2, recipes.size());
        assertFalse(recipes.contains(recipe2));

        recipes = recipeService.filterRecipesBasedOnTemperature(15.0);

        assertEquals(3, recipes.size());

        List<Recipe> berlin = recipeService.getRecommendedRecipesBasedOnWeather("Berlin");
        System.out.println(berlin.size());

    }

}