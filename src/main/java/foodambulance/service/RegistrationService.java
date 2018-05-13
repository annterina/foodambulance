package foodambulance.service;

import foodambulance.model.Customer;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    Long registerCustomer(Customer customer);

    Long loginCustomer(Customer customer);
}
