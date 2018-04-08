package foodambulance.dao;

import foodambulance.model.Recipe;
import foodambulance.model.Recipe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    @Override
    public List<Recipe> getRecipes() {
        Session session = this.sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = builder.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        query.select(root).where(builder.equal(root.get("public"), true));
        Query<Recipe> q = session.createQuery(query);
        List<Recipe> recipes = q.getResultList();
        return recipes;
    }
}
