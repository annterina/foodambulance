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

    @RequestMapping(value = "/customer/{id}/recipes", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String getRecipesOfCustomerOfId(@PathVariable String id, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(customerService.getRecipesOfCustomerOfId(new Long(id)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }

    @RequestMapping(value = "/customer/{id}/products/add",method = RequestMethod.POST,
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

    @RequestMapping(value = "/customer/{id}/recipes/add",method = RequestMethod.POST,
            consumes = "application/json; charset=UTF-8")
    @CrossOrigin
    public void addRecipeToCustomerOfId(@PathVariable String id, @RequestBody String recipeBody,
                                        HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        boolean result = customerService.addRecipeToCustomerOfId(new Long(id), recipeBody);
        if (result) response.setStatus(HttpServletResponse.SC_OK);
        else response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @RequestMapping(value = "/customer/{customerId}/recipes/{recipeId}",method = RequestMethod.PUT)
    @CrossOrigin
    public void addPublicRecipeToCustomerOfId(@PathVariable String customerId, @PathVariable String recipeId,
                                        HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        boolean result = customerService.addPublicRecipeToCustomerOfId(new Long(customerId), new Long(recipeId));
        if (result) response.setStatus(HttpServletResponse.SC_OK);
        else response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @RequestMapping(value = "/customer/{id}/plan", method = RequestMethod.GET,
            produces = "application/json; charset=UTF-8")
    @ResponseBody
    @CrossOrigin
    public String getPossibleRecipesOfCustomerOfId(@PathVariable String id, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(customerService.getPossibleRecipesOfCustomerOfId(new Long(id)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }

    @RequestMapping(value = "/customer/{customerId}/plan",method = RequestMethod.PUT)
    @CrossOrigin
    public String addRecipeToDayPlan(@RequestBody String productBody, @PathVariable String customerId,
                                              HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(customerService.addRecipeToDayPlan(productBody));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }

    @RequestMapping(value = "/customer/{customerId}/plan/delete",method = RequestMethod.PUT)
    @CrossOrigin
    public String removeRecipeFromDayPlan(@RequestBody String productBody, @PathVariable String customerId,
                                     HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        try {
            System.out.println("deleting " + productBody);
            response.setStatus(HttpServletResponse.SC_OK);
            return new ObjectMapper().writeValueAsString(customerService.deleteRecipeFromDayPlan(productBody));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Json processing error.";
        }
    }
}
