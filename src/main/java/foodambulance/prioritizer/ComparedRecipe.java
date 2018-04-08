package foodambulance.prioritizer;

import foodambulance.model.Recipe;

import java.time.LocalDateTime;

public class ComparedRecipe {

    private Recipe recipe;
    private Integer missingProducts;
    private LocalDateTime newestBuyDate;

    public ComparedRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.missingProducts = 0;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Integer missingProducts) {
        this.missingProducts = missingProducts;
    }

    public LocalDateTime getNewestBuyDate() {
        return newestBuyDate;
    }

    public void setNewestBuyDate(LocalDateTime newestBuyDate) {
        this.newestBuyDate = newestBuyDate;
    }
}
