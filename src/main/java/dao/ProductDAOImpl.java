package dao;

import model.Product;
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
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(product);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException hibernateEx) {
            hibernateEx.printStackTrace();
            return false;
        }
    }

    @Override
    public Product getProductOfId(Integer id) {
        try (Session session = this.sessionFactory.openSession()) {
            Product product = session.get(Product.class, id);
            session.close();
            return product;
        } catch (HibernateException hibernateEx) {
            hibernateEx.printStackTrace();
            return null;
        }
    }

}
