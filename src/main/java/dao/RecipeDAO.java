package dao;

import model.Recipe;

public interface RecipeDAO {

    boolean save(Recipe recipe);

    Recipe getRecipeOfId(Integer id);

}
