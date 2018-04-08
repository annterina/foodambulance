package foodambulance.dao;

import foodambulance.model.Recipe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(recipe);
        return true;
    }

    @Override
    public Recipe getRecipeOfId(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Recipe.class, id);
    }
}
