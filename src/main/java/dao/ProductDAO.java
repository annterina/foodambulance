package dao;

import model.Product;

public interface ProductDAO {

    boolean save(Product product);

    Product getProductOfId(Integer id);

}
