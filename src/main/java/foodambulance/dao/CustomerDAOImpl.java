package foodambulance.dao;

import foodambulance.model.Customer;
import foodambulance.model.CustomerProduct;
import foodambulance.model.DayPlan;
import foodambulance.model.Recipe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.query.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public boolean saveDayPlan(DayPlan dayPlan){
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(dayPlan);
        return true;
    }

    @Override
    public Customer getCustomerOfId(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = session.get(Customer.class, id);
        return customer;
    }

    @Override
    public boolean changeCustomerProductAmount(CustomerProduct customerProduct, Float amount) {
        Session session = this.sessionFactory.getCurrentSession();
        Float newAmount = customerProduct.getAmount() + amount;
        session.evict(customerProduct);
        customerProduct.setAmount(newAmount);
        session.update(customerProduct);
        return true;
    }

    @Override
    public CustomerProduct getCustomerProduct(Long customerId, Long productId) {
        if (getCustomerOfId(customerId).getCustomerProducts()==null) return null;
        for (CustomerProduct customerProduct :
                getCustomerOfId(customerId).getCustomerProducts()) {
            if (customerProduct.getProduct().getId().equals(productId)) return customerProduct;
        }
        return null;
    }

    @Override
    public Set<CustomerProduct> getCustomerProducts(Long customerId) {
        Customer customer = getCustomerOfId(customerId);
        return customer.getCustomerProducts();
    }

    @Override
    public DayPlan getDayPlanOfCustomerAndDate(Long customerId, Date date) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from DayPlan");
        List<DayPlan> result = query.getResultList();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        for (DayPlan dayPlan : result) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(dayPlan.getDate());
            if (cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH) &&
                    cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH) &&
                    cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR) &&
                    dayPlan.getCustomer().getId().equals(customerId)) {
                return dayPlan;
            }
        }
        return null;
    }
}
