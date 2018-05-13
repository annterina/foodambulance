package foodambulance.dao;

import foodambulance.model.Customer;
import foodambulance.model.DayPlan;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

        Query query = currentSession.createQuery("from Customer");
        List<Customer> result = query.getResultList();
        for (Customer registeredCustomer : result) {
            if (registeredCustomer.getMail().equals(customer.getMail())) return -1L;
        }
        return (Long) currentSession.save(customer);
    }

    @Override
    public Long loginCustomer(Customer customer) {
        final Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("from Customer");
        List<Customer> result = query.getResultList();

        for (Customer registeredCustomer : result) {
            if (matchingCustomers(customer, registeredCustomer)) return registeredCustomer.getId();
        }
        return -1L;
    }

    private boolean matchingCustomers(Customer customer, Customer registeredCustomer) {
        return registeredCustomer.getMail().equals(customer.getMail())
                && registeredCustomer.getPasswordHash().equals(customer.getPasswordHash());
    }

}
