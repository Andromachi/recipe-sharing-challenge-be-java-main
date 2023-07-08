package net.azeti.challenge.recipe.recipe.model;

public enum IngredientUnit {
    //TODO: maybe make it lowercase
    G("Gram"),
    KG("Kilogram"),
    ML("Milliliter"),
    L("Liter"),
    PC("Piece"),
    TSP("Teaspoon"),
    TBSP("Tablespoon"),
    PINCH("A dash");

    private final String description;

    IngredientUnit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    // This method is optional, it returns the enum value corresponding to a given description
    public static IngredientUnit fromDescription(String description) {
        for (IngredientUnit ingredientUnit : IngredientUnit.values()) {
            if (ingredientUnit.getDescription().equalsIgnoreCase(description)) {
                return ingredientUnit;
            }
        }
        throw new IllegalArgumentException("No enum found with description: " + description);
    }
}

