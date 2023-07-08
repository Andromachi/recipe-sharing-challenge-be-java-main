package net.azeti.challenge.recipe.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.azeti.challenge.recipe.authentication.JwtAuthenticationFilter;
import net.azeti.challenge.recipe.recipe.model.Ingredient;
import net.azeti.challenge.recipe.recipe.model.IngredientUnit;
import net.azeti.challenge.recipe.recipe.model.Recipe;
import net.azeti.challenge.recipe.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = RecipeController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@ActiveProfiles("test")
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private WeatherService weatherService;


    @Test
    void shouldCreateRecipe() throws Exception {
        Recipe recipe = createValidRecipe();
        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe);

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Recipe"));
    }

    @Test
    void shouldNotCreateRecipeWithInvalidInput() throws Exception {
        Recipe recipe = createRecipeWithoutTitle();

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetRecipeById() throws Exception {
        Recipe recipe = createValidRecipe();
        recipe.setTitle("Test Recipe");
        when(recipeService.getRecipe(anyLong())).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Recipe"));
    }

    @Test
    void shouldNotGetRecipeByIdIfNotExists() throws Exception {
        when(recipeService.getRecipe(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllRecipes() throws Exception {
        List<Recipe> recipes = Arrays.asList(createValidRecipe(), createValidRecipe());
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(recipes.size()));
    }

    @Test
    void shouldSearchRecipesByUsername() throws Exception {
        List<Recipe> recipes = Arrays.asList(createValidRecipe(), createValidRecipe());
        when(recipeService.searchRecipesByUsername(anyString())).thenReturn(recipes);

        mockMvc.perform(get("/api/v1/recipes/user/testUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(recipes.size()));
    }

    @Test
    void shouldSearchRecipesByTitle() throws Exception {
        List<Recipe> recipes = Arrays.asList(createValidRecipe(), createValidRecipe());
        when(recipeService.searchRecipesByTitle(anyString())).thenReturn(recipes);

        mockMvc.perform(get("/api/v1/recipes/title/testTitle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(recipes.size()));
    }

    @Test
    void shouldGetRecommendedRecipesBasedOnWeather() throws Exception {
        when(recipeService.getRecommendedRecipesBasedOnWeather("Berlin")).thenReturn(Collections.singletonList(createValidRecipe()));
        mockMvc.perform(get("/api/v1/recipes/recommendations/Berlin"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))) ; // check there are two recipes in the response
    }

    private Recipe createValidRecipe() {
        Ingredient ingredient1 = Ingredient.builder()
                .unit(IngredientUnit.G)
                .amount(100.0)
                .type("Flour")
                .build();

        Ingredient ingredient2 = Ingredient.builder()
                .unit(IngredientUnit.ML)
                .amount(200.0)
                .type("Milk")
                .build();

        return Recipe.builder()
                .title("Test Recipe")
                .username("Test User")
                .description("Test Description")
                .instructions("Test Instructions")
                .servings(4.0)
                .ingredients(Arrays.asList(ingredient1, ingredient2))
                .build();
    }

    private Recipe createRecipeWithoutTitle() {
        Ingredient ingredient1 = Ingredient.builder()
                .unit(IngredientUnit.G)
                .amount(100.0)
                .type("Flour")
                .build();

        Ingredient ingredient2 = Ingredient.builder()
                .unit(IngredientUnit.ML)
                .amount(200.0)
                .type("Milk")
                .build();

        return Recipe.builder()
                .username("Test User")
                .description("Test Description")
                .instructions("Test Instructions")
                .servings(4.0)
                .ingredients(Arrays.asList(ingredient1, ingredient2))
                .build();
    }
}