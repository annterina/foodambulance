package foodambulance.dao;

import foodambulance.model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    @Override
    public List<Product> getProducts() {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root);
        Query<Product> q = session.createQuery(query);
        List<Product> products = q.getResultList();
        return products;
    }
}
