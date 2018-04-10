package foodambulance.dao;

import foodambulance.model.Recipe;
import foodambulance.model.RecipeIngredient;

import java.util.List;

public interface RecipeDAO {

    boolean save(Recipe recipe);

    public boolean save(RecipeIngredient recipeIngredient);

    Recipe getRecipeOfId(Long id);

    List<Recipe> getRecipes();
}
