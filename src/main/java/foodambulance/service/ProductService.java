package foodambulance.service;

import foodambulance.model.Product;

import java.util.List;

public interface ProductService {

    boolean addProduct(String productBody);

    List<Product> getProducts();
}