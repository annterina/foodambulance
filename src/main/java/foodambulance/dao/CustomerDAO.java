package foodambulance.dao;

import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.DayPlan;
import foodambulance.model.Recipe;

import java.util.Date;

public interface CustomerDAO {

    boolean save(Customer customer);

    boolean saveDayPlan(DayPlan dayPlan);

    Customer getCustomerOfId(Long id);

    boolean saveCustomerProduct(CustomerProduct customerProduct);

    boolean saveRecipe(Recipe recipe);

    boolean changeCustomerProductAmount(CustomerProduct customerProduct, Float amount);

    CustomerProduct getCustomerProduct(Long customerId, Long productId);

    DayPlan getDayPlanOfCustomerAndDate(Long customerId, Date date);
}
