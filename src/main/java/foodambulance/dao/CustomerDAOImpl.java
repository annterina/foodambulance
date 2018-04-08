package foodambulance.dao;

import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.Recipe;
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
    public boolean saveCustomerProduct(CustomerProduct customerProduct) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(customerProduct);
        return true;
    }

    @Override
    public boolean saveRecipe(Recipe recipe) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(recipe);
        return true;
    }

    @Override
    public Customer getCustomerOfId(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Customer.class, id);
    }

    @Override
    public boolean changeCustomerProductAmount(CustomerProduct customerProduct, Float amount) {
        Session session = this.sessionFactory.getCurrentSession();
        Float newAmount = customerProduct.getAmount() + amount;
        customerProduct.setAmount(newAmount);
        session.update(customerProduct);
        return true;
    }
}
