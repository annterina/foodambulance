package foodambulance.dao;

import foodambulance.model.Customer;

public interface RegistrationDAO {
    Long registerCustomer(Customer customer);

    Long loginCustomer(Customer customer);
}
