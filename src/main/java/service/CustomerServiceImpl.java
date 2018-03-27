package service;

import dao.CustomerDAO;
import model.CustomerProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO){this.customerDAO = customerDAO;}

    @Override
    public Set<CustomerProduct> getProductsOfCustomerOfId(Integer id) {
        return customerDAO.getCustomerOfId(id).getCustomerProducts();
    }
}
