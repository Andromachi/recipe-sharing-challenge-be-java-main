package net.azeti.challenge.recipe.recipe;

import net.azeti.challenge.recipe.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUsernameContainingIgnoreCase(String username);
    List<Recipe> findByTitleContainingIgnoreCase(String title);
}
