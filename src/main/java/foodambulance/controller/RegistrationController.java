package foodambulance.controller;

import foodambulance.model.Customer;
import foodambulance.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
    public String register(@RequestBody Customer customer, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        System.out.println("Got request");
        Long id = registrationService.registerCustomer(customer);
        response.setStatus(HttpServletResponse.SC_OK);
        return "redirect:/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletResponse response) {
        return "login";
    }
}
