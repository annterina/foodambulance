package foodambulance.dao;


import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dispatcher-servlet-test.xml")
public class CustomerDAOImplTest {

    @Autowired
    CustomerDAO customerDAO;

    @Test
    public void saveAndGetCustomerFromDatabase() {
        Customer customer = new Customer();
        customer.setMail("a@a.com");
        customer.setPasswordHash("qwerty");
        Set<CustomerProduct> customerProducts = new HashSet<>();
        CustomerProduct product = new CustomerProduct();
        product.setId(1L);
        product.setCustomer(customer);
        product.setAmount(2f);
        product.setNewestBuyDate(LocalDateTime.now());
        customer.setCustomerProducts(customerProducts);
        customerDAO.save(customer);
        Customer databaseCustomer = customerDAO.getCustomerOfId(customer.getId());
        assertNotNull(databaseCustomer);
    }
}
