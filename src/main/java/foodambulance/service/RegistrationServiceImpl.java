package foodambulance.service;

import foodambulance.dao.RegistrationDAO;
import foodambulance.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private RegistrationDAO registrationDAO;

    @Autowired
    public RegistrationServiceImpl(RegistrationDAO registrationDAO) {
        this.registrationDAO = registrationDAO;
    }


    @Override
    @Transactional
    public Long registerCustomer(Customer customer) {
        return registrationDAO.registerCustomer(customer);
    }

    @Override
    @Transactional
    public Long loginCustomer(Customer customer) {
        return registrationDAO.loginCustomer(customer);
    }
}
