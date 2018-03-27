package dao;

import model.Customer;

public interface CustomerDAO {

    boolean save(Customer customer);

    Customer getCustomerOfId(Integer id);

}
