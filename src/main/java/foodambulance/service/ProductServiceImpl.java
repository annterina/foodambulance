package foodambulance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import foodambulance.dao.ProductDAO;
import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public boolean addProduct(String productBody) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            Product product = mapper.readValue(productBody, Product.class);
            product.setCustomerProducts(new HashSet<>());
            product.setRecipeIngredients(new HashSet<>());
            productDAO.save(product);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding product");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public List<Product> getProducts() {
        return productDAO.getProducts();
    }
}
