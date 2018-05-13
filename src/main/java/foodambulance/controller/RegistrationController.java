package foodambulance.controller;

import foodambulance.model.Customer;
import foodambulance.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String register(@RequestBody Customer customer, HttpServletResponse response) {
        Long id = registrationService.registerCustomer(customer);
        if (id == -1L) {
            return "{\"status\" : \"incorrectData\"}";
        }
        response.setStatus(200);
        return "{\"status\" : \"login\"}";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody Customer customer, HttpServletResponse response) {
        Long customerId = registrationService.loginCustomer(customer);
        if (customerId == -1L) {
            response.setStatus(200);
            return "{\"status\" : \"incorrectData\"}";
        }
        else {
            response.setStatus(200);
            return "{\"status\" : \"login\", \"customerId\" : \"" + customerId + "\"}";
        }
    }
}
