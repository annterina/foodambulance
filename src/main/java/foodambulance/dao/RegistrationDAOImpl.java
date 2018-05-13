package foodambulance.dao;

import foodambulance.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationDAOImpl implements RegistrationDAO {


    private final SessionFactory sessionFactory;

    @Autowired
    public RegistrationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long registerCustomer(Customer customer) {
        final Session currentSession = sessionFactory.getCurrentSession();
        return (Long) currentSession.save(customer);
    }

}
