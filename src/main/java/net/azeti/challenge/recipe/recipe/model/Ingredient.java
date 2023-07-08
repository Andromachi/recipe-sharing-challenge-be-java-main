package net.azeti.challenge.recipe.recipe.model;

import lombok.*;

//TODO: not star
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private IngredientUnit unit;
    @NotNull
    private Double amount;
    @NotBlank
    private String type;
}