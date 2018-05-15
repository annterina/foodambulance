package foodambulance.deserialization;

import java.util.List;

public class StrippedRecipe {

    private String name;
    private Long customerId;
    private List<Long> recipeIngredientsIds;
    private List<Float> recipeIngredientsAmount;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getRecipeIngredientsIds() {
        return recipeIngredientsIds;
    }

    public void setRecipeIngredientsIds(List<Long> recipeIngredientsIds) {
        this.recipeIngredientsIds = recipeIngredientsIds;
    }

    public List<Float> getRecipeIngredientsAmount() {
        return recipeIngredientsAmount;
    }

    public void setRecipeIngredientsAmount(List<Float> recipeIngredientsAmount) {
        this.recipeIngredientsAmount = recipeIngredientsAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
