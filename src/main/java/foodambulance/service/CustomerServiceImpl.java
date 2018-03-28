package foodambulance.service;

import foodambulance.dao.CustomerDAO;
import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO){this.customerDAO = customerDAO;}

    @Override
    @Transactional
    public Set<CustomerProduct> getProductsOfCustomerOfId(Integer id) {
        Customer customer = customerDAO.getCustomerOfId(id);
        Hibernate.initialize(customer.getCustomerProducts());
        return customer.getCustomerProducts();
    }
}
