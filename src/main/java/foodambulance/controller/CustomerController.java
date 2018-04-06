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

    @RequestMapping(value = "/customer/{id}/products", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String getProductsOfCustomerOfId(@PathVariable String id, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(customerService.getProductsOfCustomerOfId(new Long(id)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }

    @RequestMapping(value = "/customer/{id}/addproduct",method = RequestMethod.POST,
            consumes = "application/json; charset=UTF-8")
    @CrossOrigin
    public void addCustomerProductToCustomerOfId(@PathVariable String id,
                                                 @RequestBody String customerProductBody,
                                                 HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        boolean result = customerService.addCustomerProductToCustomerOfId(new Long(id), customerProductBody);
        if (result) response.setStatus(HttpServletResponse.SC_OK);
        else response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
