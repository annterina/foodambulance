package foodambulance.controller;

import foodambulance.model.CustomerProduct;
import foodambulance.model.Product;
import foodambulance.service.CustomerService;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class CustomerControllerTest {

    CustomerService customerServiceMock = Mockito.mock(CustomerService.class);
    CustomerController customerController = new CustomerController(customerServiceMock);

    @Test
    public void getProductsOfCustomerOfId() {
        //given
        CustomerProduct customerProduct = new CustomerProduct();
        customerProduct.setId(144L);
        customerProduct.setAmount(123f);
        customerProduct.setProduct(new Product());
        customerProduct.setNewestBuyDate(LocalDateTime.now());
        Mockito.when(customerServiceMock.getProductsOfCustomerOfId(Mockito.any())).thenReturn(
                new HashSet<>(Arrays.asList(customerProduct))
        );

        //when
        final String productsOfCustomerOfId = customerController
                .getProductsOfCustomerOfId("1", new MockHttpServletResponse());

        //then
        assertNotNull(productsOfCustomerOfId);
        assertEquals(productsOfCustomerOfId.contains("123"), true);
        assertEquals(productsOfCustomerOfId.contains("144"), true);
    }
}