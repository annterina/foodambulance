package dao;

import model.Product;
import model.Recipe;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDAOImpl implements RecipeDAO{

    private final SessionFactory sessionFactory;

    @Autowired
    public RecipeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean save(Recipe recipe) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(recipe);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException hibernateEx) {
            hibernateEx.printStackTrace();
            return false;
        }
    }

    @Override
    public Recipe getRecipeOfId(Integer id) {
        try (Session session = this.sessionFactory.openSession()) {
            Recipe recipe = session.get(Recipe.class, id);
            session.close();
            return recipe;
        } catch (HibernateException hibernateEx) {
            hibernateEx.printStackTrace();
            return null;
        }
    }
}
