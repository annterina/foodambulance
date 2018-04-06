package foodambulance.service;

import foodambulance.model.CustomerProduct;

import java.util.Set;

public interface CustomerService {

    Set<CustomerProduct> getProductsOfCustomerOfId(Integer id);

}