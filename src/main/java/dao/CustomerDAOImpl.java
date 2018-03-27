package dao;

import model.Customer;
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
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(customer);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException hibernateEx) {
            hibernateEx.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer getCustomerOfId(Integer id) {
        try (Session session = this.sessionFactory.openSession()) {
            Customer customer = session.get(Customer.class, id);
            session.close();
            return customer;
        } catch (HibernateException hibernateEx) {
            hibernateEx.printStackTrace();
            return null;
        }
    }
}
