package foodambulance.prioritizer;

import foodambulance.model.Recipe;
import foodambulance.model.RecipeIngredient;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ComparedRecipe {

    private Recipe recipe;
    private Integer missingProductsNumber;
    private LocalDateTime newestBuyDate;
    private Set<RecipeIngredient> missingProducts;

    public ComparedRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.missingProductsNumber = 0;
        this.newestBuyDate = LocalDateTime.now();
        this.missingProducts = new HashSet<>();
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getMissingProductsNumber() {
        return missingProductsNumber;
    }

    public void setMissingProductsNumber(Integer missingProducts) {
        this.missingProductsNumber = missingProducts;
    }

    public LocalDateTime getNewestBuyDate() {
        return newestBuyDate;
    }

    public void setNewestBuyDate(LocalDateTime newestBuyDate) {
        this.newestBuyDate = newestBuyDate;
    }

    public Set<RecipeIngredient> getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Set<RecipeIngredient> missingProducts) {
        this.missingProducts = missingProducts;
    }
}
