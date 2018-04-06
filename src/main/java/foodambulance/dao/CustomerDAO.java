package foodambulance.dao;

import foodambulance.model.Customer;

public interface CustomerDAO {

    boolean save(Customer customer);

    Customer getCustomerOfId(Integer id);

}
