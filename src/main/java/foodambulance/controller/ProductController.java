package foodambulance.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import foodambulance.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/newproduct",method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    @CrossOrigin
    public void addProduct(@RequestBody String productBody, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        boolean result = productService.addProduct(productBody);
        if (result) response.setStatus(HttpServletResponse.SC_OK);
        else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String getProducts(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(productService.getProducts());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }
}
