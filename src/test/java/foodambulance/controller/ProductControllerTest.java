package foodambulance.controller;

import foodambulance.model.CustomerProduct;
import foodambulance.model.Product;
import foodambulance.service.CustomerService;
import foodambulance.service.ProductService;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class ProductControllerTest {

    ProductService productService = Mockito.mock(ProductService.class);
    ProductController productController  = new ProductController(productService);

    @Test
    public void getProductsOfCustomerOfId() {
        //given
        Product product = new Product();
        product.setName("TestProduct");
        product.setId(123L);
        Mockito.when(productService.getProducts()).thenReturn(
                Arrays.asList(product)
        );

        //when
        final String products = productController
                .getProducts(new MockHttpServletResponse());

        //then
        assertNotNull(products);
        assertEquals(products.contains("123"), true);
        assertEquals(products.contains("TestProduct"), true);
    }
}