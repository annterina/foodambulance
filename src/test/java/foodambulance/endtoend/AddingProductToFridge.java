package foodambulance.endtoend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import foodambulance.dao.CustomerDAO;
import foodambulance.dao.ProductDAO;
import foodambulance.deserialization.StrippedCustomerProduct;
import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.Product;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dispatcher-servlet-test.xml")
@Transactional
public class AddingProductToFridge {

    long productId = -1;
    long customerProductId = -1;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    CustomerDAO customerDAO;

    @Test
    @Rollback(false)
    @Commit
    public void addingProductTest() throws IOException {
        //adding product to database
        Product product = new Product();
        product.setName("testProduct");
        product.setBaseAmount(2f);
        product.setBaseUnit("kg");
        product.setBaseExpirationDate(20);
        product.setCategoryId(2L);
        String productJson = "";
        try {
            productJson = new ObjectMapper().writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(productJson);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost("http://localhost:8080/products/new");
        StringEntity params = new StringEntity(productJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);


        //adding customer to database TODO
        Customer customer = new Customer();
        customer.setMail("ab@ab.com");
        customer.setPasswordHash("qwerty");
        customer.setCustomerProducts(new HashSet<>());
        customer.setRecipes(new HashSet<>());
        Session session = sessionFactory.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        session.save(customer);
        session.flush();
        transaction.commit();


        //Retrieving all products
        HttpGet getRequest = new HttpGet("http://localhost:8080/products");
        getRequest.addHeader("content-type", "application/json");
        HttpResponse getResponse = httpClient.execute(getRequest);

        String getResponseString = EntityUtils.toString(getResponse.getEntity());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        List<Product> retrievedProducts = new ArrayList<>();
        try {
            retrievedProducts = mapper.readValue(getResponseString,  new TypeReference<List<Product>>(){});

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Product p : retrievedProducts) {
            if (p.getName().equals("testProduct")){
                productId = p.getId();
                System.out.println("Found test product");
            }
        }
        assertNotEquals(productId, -1);

        //creating stripped product
        StrippedCustomerProduct strippedCustomerProduct = new StrippedCustomerProduct();
        strippedCustomerProduct.setProductId(productId);
        strippedCustomerProduct.setAmount(5f);

        String strippedCustomerProductJson = new ObjectMapper().writeValueAsString(strippedCustomerProduct);


        //adding product to fridge
        HttpPost addRequest = new HttpPost("http://localhost:8080/customer/" + customer.getId() + "/products/add");
        StringEntity addParams = new StringEntity(strippedCustomerProductJson);
        addRequest.addHeader("content-type", "application/json");
        addRequest.setEntity(addParams);
        HttpResponse addResponse = httpClient.execute(addRequest);

        assertEquals(addResponse.getStatusLine().getStatusCode(), 200);

        //checking if product is in fridge
        session = sessionFactory.getCurrentSession();
        String hql = "FROM CustomerProduct E WHERE E.product =" + productId;
        Query query = session.createQuery(hql);

        List<CustomerProduct> results = query.list();
        customerProductId = results.get(0).getId().intValue();
        assertNotEquals(customerProductId, -1);

        System.out.println("Got session");
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product productToDelete = session.get(Product.class, productId);
        CustomerProduct customerProductToDelete = session.get(CustomerProduct.class, customerProductId);

        session.delete(customerProductToDelete);
        session.delete(customer);
        session.delete(productToDelete);
        session.flush();
    }

    @After
    public void after(){
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
