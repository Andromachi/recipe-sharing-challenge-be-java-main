package net.azeti.challenge.recipe.recipe.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//TODO: create DTO
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 200, message = "Title cannot be more than 200 characters")
    private String title;

    //TODO: should this be the user that is loggedIn?
    //TODO: then it is nullable
    private String username;
    private String description;

    //TODO is this mandatory
    @NotEmpty(message = "At least one ingredient is required")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients;
    @NotBlank(message = "Instructions are mandatory")
    private String instructions;
    private Double servings;


    public boolean recipeRequiresBaking() {
        String instructions = this.getInstructions().toLowerCase();
        return instructions.contains("bake") || instructions.contains("oven");
    }

    public boolean recipeUsesFrozenIngredients() {
        for (Ingredient ingredient : this.getIngredients()) {
            if (ingredient.getType().toLowerCase().contains("frozen")) {
                return true;
            }
        }
        return false;
    }
}
