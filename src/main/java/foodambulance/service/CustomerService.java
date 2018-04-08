package foodambulance.service;

import foodambulance.model.CustomerProduct;

import java.util.Map;
import java.util.Set;

public interface CustomerService {

    Map<Long, CustomerProduct> getProductsOfCustomerOfId(Long id);

    boolean addCustomerProductToCustomerOfId(Long id, String customerProductBody);

    boolean addRecipeToCustomerOfId(Long aLong, String recipeBody);
}
