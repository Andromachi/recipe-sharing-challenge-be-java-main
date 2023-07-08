package net.azeti.challenge.recipe.recipe;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.azeti.challenge.recipe.recipe.model.Recipe;
import net.azeti.challenge.recipe.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private WeatherService weatherService;

    @Operation(summary = "Create a new recipe. The username of the logged in user will be set, so any values in username field will be overriden")
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        //TODO: use DTO instead of modifying the original object to make it immutable
        recipe.setUsername(currentPrincipalName);
        return new ResponseEntity<>(recipeService.saveRecipe(recipe), HttpStatus.CREATED);    }


    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);
        return recipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @Operation(summary = "Search recipes by username, partial match returned")
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Recipe>> searchRecipesByUsername(@PathVariable String username) {
        return new ResponseEntity<>(recipeService.searchRecipesByUsername(username), HttpStatus.OK);
    }

    @Operation(summary = "Search recipes by title, partial match returned")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Recipe>> searchRecipesByTitle(@PathVariable String title) {
        return new ResponseEntity<>(recipeService.searchRecipesByTitle(title), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        Optional<Recipe> updatedRecipe = recipeService.updateRecipe(id, recipe);
        return updatedRecipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get recipe recommendations based on weather in a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "City not found"),
    })
    @GetMapping("/recommendations/{city}")
    public ResponseEntity<List<Recipe>> getRecommendationsBasedOnWeather(@PathVariable String city) {
        return new ResponseEntity<>(recipeService.getRecommendedRecipesBasedOnWeather(city), HttpStatus.OK);
    }
}

