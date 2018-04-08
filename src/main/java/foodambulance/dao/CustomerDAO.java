package foodambulance.dao;

import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.Recipe;

public interface CustomerDAO {

    boolean save(Customer customer);

    Customer getCustomerOfId(Long id);

    boolean saveCustomerProduct(CustomerProduct customerProduct);

    boolean saveRecipe(Recipe recipe);

    boolean changeCustomerProductAmount(CustomerProduct customerProduct, Float amount);
}
