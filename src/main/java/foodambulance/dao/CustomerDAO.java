package foodambulance.dao;

import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;

public interface CustomerDAO {

    boolean save(Customer customer);

    Customer getCustomerOfId(Long id);

    boolean saveCustomerProduct(CustomerProduct customerProduct);

}
