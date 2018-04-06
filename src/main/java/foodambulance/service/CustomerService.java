package foodambulance.service;

import foodambulance.model.CustomerProduct;

import java.util.Set;

public interface CustomerService {

    Set<CustomerProduct> getProductsOfCustomerOfId(Long id);

    boolean addCustomerProductToCustomerOfId(Long id, String customerProductBody);

}
