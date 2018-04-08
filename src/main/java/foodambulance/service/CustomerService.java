package foodambulance.service;

import foodambulance.model.CustomerProduct;
import foodambulance.model.Recipe;
import foodambulance.prioritizer.ComparedRecipe;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    Set<CustomerProduct> getProductsOfCustomerOfId(Long id);

    boolean addCustomerProductToCustomerOfId(Long id, String customerProductBody);

    boolean addRecipeToCustomerOfId(Long aLong, String recipeBody);

    List<ComparedRecipe> getPossibleRecipesOfCustomerOfId(Long id);
}
