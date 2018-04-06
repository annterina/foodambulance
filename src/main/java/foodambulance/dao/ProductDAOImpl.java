package foodambulance.dao;

import foodambulance.model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ProductDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(product);
        return true;
    }

    @Override
    public Product getProductOfId(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
    }

}
