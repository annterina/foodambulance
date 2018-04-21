package foodambulance.dao;

import foodambulance.model.Product;

import java.util.List;

public interface ProductDAO {

    boolean save(Product product);

    Product getProductOfId(Long id);

    List<Product> getProducts();
}
