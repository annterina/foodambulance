package foodambulance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import foodambulance.dao.CustomerDAO;
import foodambulance.dao.ProductDAO;
import foodambulance.deserialization.StrippedCustomerProduct;
import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.Product;
import foodambulance.model.Recipe;
import foodambulance.prioritizer.ComparedRecipe;
import foodambulance.prioritizer.RecipePrioritizer;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerDAO customerDAO;
    private ProductDAO productDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, ProductDAO productDAO) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public Set<CustomerProduct> getProductsOfCustomerOfId(Long id) {
        Customer customer = customerDAO.getCustomerOfId(id);
        Hibernate.initialize(customer.getCustomerProducts());
        return customer.getCustomerProducts();
    }

    @Override
    @Transactional
    public boolean addCustomerProductToCustomerOfId(Long id, String customerProductBody) {
        Customer customer = customerDAO.getCustomerOfId(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            StrippedCustomerProduct tempProduct = mapper.readValue(customerProductBody, StrippedCustomerProduct.class);
            System.out.println(customerProductBody);

            CustomerProduct customerProduct = new CustomerProduct();
            customerProduct.setCustomer(customer);
            customerProduct.setAmount(tempProduct.getAmount());
            Product product = productDAO.getProductOfId(tempProduct.getProductId());
            customerProduct.setProduct(product);

            CustomerProduct oldCustomerProduct = customerDAO.getCustomerProduct(id, product.getId());
            if (oldCustomerProduct!=null) {
                customerProduct.setAmount(oldCustomerProduct.getAmount() + customerProduct.getAmount());
            }
            customerProduct.setNewestBuyDate(LocalDateTime.now());
            System.out.println(customerProduct.toString());

            customerDAO.saveCustomerProduct(customerProduct);
            System.out.println("Saved.");
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding customer product to customer of id " + id);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addRecipeToCustomerOfId(Long id, String recipeBody) {
        Customer customer = customerDAO.getCustomerOfId(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            Recipe recipe = mapper.readValue(recipeBody, Recipe.class);
            recipe.setCustomer(customer);
            customerDAO.saveRecipe(recipe);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding recipe to customer of id " + id);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public List<ComparedRecipe> getPossibleRecipesOfCustomerOfId(Long id) {
        Customer customer = customerDAO.getCustomerOfId(id);
        Hibernate.initialize(customer.getCustomerProducts());
        Set<CustomerProduct> customerProducts = customer.getCustomerProducts();
        Set<Recipe> customerRecipes = customer.getRecipes();
        RecipePrioritizer recipePrioritizer = new RecipePrioritizer(customerProducts, customerRecipes);
        List<ComparedRecipe> comparedRecipes = new LinkedList<>(recipePrioritizer.sortRecipes());
        return comparedRecipes;
    }
}
