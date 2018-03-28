package foodambulance.dao;

import foodambulance.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(Customer customer) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(customer);
        return true;
    }

    @Override
    public Customer getCustomerOfId(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Customer.class, id);
    }
}
