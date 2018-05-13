package foodambulance.service;

import foodambulance.deserialization.RecipeD;
import foodambulance.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeService {
    
    boolean addRecipe(String recipeBody);

    List<RecipeD> getRecipes();
}
