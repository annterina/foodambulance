package foodambulance.dao;

import foodambulance.model.Product;

public interface ProductDAO {

    boolean save(Product product);

    Product getProductOfId(Long id);

}
