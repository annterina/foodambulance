package foodambulance.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import foodambulance.service.CustomerService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer/{id}/products", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String getProductsOfCustomerOfId(@PathVariable String id, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            return new ObjectMapper().writeValueAsString(customerService.getProductsOfCustomerOfId(new Integer(id)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Json processing error.";
        }
    }
}
