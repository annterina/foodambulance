package foodambulance.dao;

import foodambulance.model.Recipe;

public interface RecipeDAO {

    boolean save(Recipe recipe);

    Recipe getRecipeOfId(Integer id);

}
