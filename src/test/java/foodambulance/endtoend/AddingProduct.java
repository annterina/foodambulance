package foodambulance.endtoend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import foodambulance.dao.ProductDAO;
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
import sun.net.www.http.HttpClient;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dispatcher-servlet-test.xml")
@Transactional
public class AddingProduct {

    long productId = -1;

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    @Rollback(false)
    @Commit
    public void addingProductTest() throws IOException {

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


        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost("http://localhost:8080/products/new");
        StringEntity params = new StringEntity(productJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);

        System.out.println(response.getStatusLine());
        String responseString = EntityUtils.toString(response.getEntity());
        assertEquals(response.getStatusLine().getStatusCode(), 200);


        HttpGet getRequest = new HttpGet("http://localhost:8080/products");
        getRequest.addHeader("content-type", "application/json");
        HttpResponse getResponse = httpClient.execute(getRequest);

        System.out.println(getResponse.getStatusLine());
        String getResponseString = EntityUtils.toString(getResponse.getEntity());
        assertEquals(getResponse.getStatusLine().getStatusCode(), 200);

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
            }
        }
        assertNotEquals(productId, -1);

        Session session;
        session = sessionFactory.getCurrentSession();
        Product productToDelete = session.get(Product.class, productId);
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
