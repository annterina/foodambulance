package foodambulance.service;

import foodambulance.deserialization.RecipeD;
import foodambulance.deserialization.Grocery;
import foodambulance.deserialization.StrippedDayPlan;
import foodambulance.model.CustomerProduct;
import foodambulance.model.Recipe;
import foodambulance.prioritizer.ComparedRecipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface CustomerService {

    Set<CustomerProduct> getProductsOfCustomerOfId(Long id);

    Set<RecipeD> getRecipesOfCustomerOfId(Long id);

    boolean addCustomerProductToCustomerOfId(Long id, String customerProductBody);

    boolean addRecipeToCustomerOfId(Long id, String recipeBody);

    List<ComparedRecipe> getPossibleRecipesOfCustomerOfId(Long id);

    boolean addPublicRecipeToCustomerOfId(Long customerId, Long recipeId);

    boolean addRecipeToDayPlan(String strippedDayPlanBody);

    boolean deleteRecipeFromDayPlan(String strippedDayPlanBody);

    List<Grocery> getGroceryListOfCustomerOfId(Long id);
}
