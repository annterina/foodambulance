package foodambulance.dao;

import foodambulance.model.Recipe;

import java.util.List;

public interface RecipeDAO {

    boolean save(Recipe recipe);

    Recipe getRecipeOfId(Long id);

    List<Recipe> getRecipes();
}
