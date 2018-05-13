package foodambulance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import foodambulance.dao.ProductDAO;
import foodambulance.dao.RecipeDAO;
import foodambulance.deserialization.RecipeD;
import foodambulance.deserialization.StrippedRecipe;
import foodambulance.model.Customer;
import foodambulance.model.Recipe;
import foodambulance.model.RecipeIngredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private RecipeDAO recipeDAO;
    private ProductDAO productDAO;

    @Autowired
    public RecipeServiceImpl(RecipeDAO recipeDAO, ProductDAO productDAO) {
        this.recipeDAO = recipeDAO;
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public boolean addRecipe(String recipeBody) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            StrippedRecipe strippedRecipe = mapper.readValue(recipeBody, StrippedRecipe.class);
            Map<Long, Float> ingredientsMap = IntStream.range(0, strippedRecipe.getRecipeIngredientsIds().size()).boxed()
                    .collect(Collectors.toMap(strippedRecipe.getRecipeIngredientsIds()::get,
                            strippedRecipe.getRecipeIngredientsAmount()::get));
            Recipe recipe = new Recipe();
            recipe.setName(strippedRecipe.getName());
            List<Long> recipeIngredientsIds = strippedRecipe.getRecipeIngredientsIds();
            Set<RecipeIngredient> recipeIngredients = new HashSet<>();
            recipeIngredientsIds.forEach(ingredientId -> {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setProduct(productDAO.getProductOfId(ingredientId));
                recipeIngredient.setAmount(ingredientsMap.get(ingredientId));
                recipeDAO.save(recipeIngredient);
                recipeIngredients.add(recipeIngredient);
            });
            recipe.setIngredients(recipeIngredients);
            recipeDAO.save(recipe);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding recipe");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public List<RecipeD> getRecipes() {
        List<Recipe> recipes = recipeDAO.getRecipes();
        return recipes.stream().map(RecipeD::new).collect(Collectors.toList());
    }
}
