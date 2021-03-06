package foodambulance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import foodambulance.dao.CustomerDAO;
import foodambulance.dao.ProductDAO;
import foodambulance.dao.RecipeDAO;
import foodambulance.deserialization.Grocery;
import foodambulance.deserialization.RecipeD;
import foodambulance.deserialization.StrippedCustomerProduct;
import foodambulance.deserialization.StrippedDayPlan;
import foodambulance.deserialization.StrippedRecipe;
import foodambulance.model.*;
import foodambulance.prioritizer.ComparedRecipe;
import foodambulance.prioritizer.RecipePrioritizer;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private RecipeDAO recipeDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, ProductDAO productDAO, RecipeDAO recipeDAO) {
        this.customerDAO = customerDAO;
        this.productDAO = productDAO;
        this.recipeDAO = recipeDAO;
    }

    @Override
    @Transactional
    public Set<CustomerProduct> getProductsOfCustomerOfId(Long id) {
        Customer customer = customerDAO.getCustomerOfId(id);
        Hibernate.initialize(customer.getCustomerProducts());
        return customer.getCustomerProducts();
    }

    @Override
    @Transactional
    public Set<RecipeD> getRecipesOfCustomerOfId(Long id) {
        Customer customer = customerDAO.getCustomerOfId(id);
        Hibernate.initialize(customer.getRecipes());
        Set<Recipe> recipes = customer.getRecipes();
        return recipes.stream().map(RecipeD::new).collect(Collectors.toSet());

    }

    @Override
    @Transactional
    public boolean addCustomerProductToCustomerOfId(Long id, String customerProductBody) {
        Customer customer = customerDAO.getCustomerOfId(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            StrippedCustomerProduct tempProduct = mapper.readValue(customerProductBody, StrippedCustomerProduct.class);
            CustomerProduct oldCustomerProduct = customerDAO.getCustomerProduct(id, tempProduct.getProductId());
            if (oldCustomerProduct==null) {
                System.out.println("Old customer product null");
                CustomerProduct customerProduct = new CustomerProduct();
                customerProduct.setCustomer(customer);
                customerProduct.setAmount(tempProduct.getAmount());
                Product product = productDAO.getProductOfId(tempProduct.getProductId());
                customerProduct.setProduct(product);
                customerProduct.setNewestBuyDate(LocalDateTime.now());
                customerDAO.saveCustomerProduct(customerProduct);
            }
            else{
                System.out.println("ASDASD ");
                System.out.println(oldCustomerProduct.getProduct().getId());
                customerDAO.changeCustomerProductAmount(oldCustomerProduct, tempProduct.getAmount());
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding customer product to customer of id " + id);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addRecipeToCustomerOfId(Long id, String recipeBody) {
        Customer customer = customerDAO.getCustomerOfId(id);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            StrippedRecipe strippedRecipe = mapper.readValue(recipeBody, StrippedRecipe.class);
            Map<Long, Float> ingredientsMap = IntStream.range(0, strippedRecipe.getRecipeIngredientsIds().size()).boxed()
                    .collect(Collectors.toMap(strippedRecipe.getRecipeIngredientsIds()::get,
                            strippedRecipe.getRecipeIngredientsAmount()::get));
            Recipe recipe = new Recipe();
            recipe.addCustomer(customer);
            recipe.setName(strippedRecipe.getName());
            List<Long> recipeIngredientsIds = strippedRecipe.getRecipeIngredientsIds();
            Set<RecipeIngredient> recipeIngredients = new HashSet<>();
            recipeIngredientsIds.forEach(ingredientId -> {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setProduct(productDAO.getProductOfId(ingredientId));
                recipeIngredient.setAmount(ingredientsMap.get(ingredientId));
                recipeIngredients.add(recipeIngredient);
                // TODO add isPublic setting
            });
            recipe.setIngredients(recipeIngredients);
            recipeDAO.save(recipe);
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding recipe to customer of id " + id);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addPublicRecipeToCustomerOfId(Long customerId, Long recipeId) {
        Customer customer = customerDAO.getCustomerOfId(customerId);
        Recipe recipe = recipeDAO.getRecipeOfId(recipeId);
        recipe.addCustomer(customer);
        customer.addRecipe(recipe);
        customerDAO.save(customer);
        recipeDAO.save(recipe);
        return true;
    }

    @Override
    @Transactional
    public List<ComparedRecipe> getPossibleRecipesOfCustomerOfId(Long id) {
        Customer customer = customerDAO.getCustomerOfId(id);
        Hibernate.initialize(customer.getCustomerProducts());
        Set<CustomerProduct> customerProducts = customer.getCustomerProducts();
        Set<Recipe> customerRecipes = customer.getRecipes();
        RecipePrioritizer recipePrioritizer = new RecipePrioritizer(customerProducts, customerRecipes);
        List<ComparedRecipe> comparedRecipes = new LinkedList<>(recipePrioritizer.sortRecipes());
        return comparedRecipes;
    }

    @Override
    @Transactional
    public boolean addRecipeToDayPlan(String strippedDayPlanBody){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            StrippedDayPlan strippedDayPlan = mapper.readValue(strippedDayPlanBody, StrippedDayPlan.class);
            Customer customer = customerDAO.getCustomerOfId(strippedDayPlan.getCustomerId());
            Recipe recipe = recipeDAO.getRecipeOfId(strippedDayPlan.getRecipeId());
            System.out.println("Loaded customer and recipe");
            DayPlan dayPlan = customerDAO.getDayPlanOfCustomerAndDate(customer.getId(), strippedDayPlan.getDate());
            if (dayPlan==null) {
                dayPlan = new DayPlan();
                dayPlan.setDate(strippedDayPlan.getDate());
                dayPlan.setCustomer(customer);
                customer.getDayPlans().add(dayPlan);
                System.out.println("Day plan not found");
            }
            dayPlan.getRecipes().add(recipe);
            recipe.getIngredients().forEach(ingredient ->
                customerDAO.changeCustomerProductAmount(
                        customerDAO.getCustomerProduct(
                                customer.getId(), ingredient.getProduct().getId()), -ingredient.getAmount())
            );
            System.out.println("Saving...");
            customerDAO.saveDayPlan(dayPlan);
            customerDAO.save(customer);
            recipeDAO.save(recipe);
            System.out.println("Saved");
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding product");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteRecipeFromDayPlan(String strippedDayPlanBody){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        try {
            StrippedDayPlan strippedDayPlan = mapper.readValue(strippedDayPlanBody, StrippedDayPlan.class);
            Customer customer = customerDAO.getCustomerOfId(strippedDayPlan.getCustomerId());
            Recipe recipe = recipeDAO.getRecipeOfId(strippedDayPlan.getRecipeId());
            System.out.println("Loaded customer and recipe");
            DayPlan dayPlan = customerDAO.getDayPlanOfCustomerAndDate(customer.getId(), strippedDayPlan.getDate());
            dayPlan.getRecipes().remove(recipe);
            recipe.getIngredients().forEach(ingredient ->
                    customerDAO.changeCustomerProductAmount(
                            customerDAO.getCustomerProduct(
                                    customer.getId(), ingredient.getProduct().getId()), ingredient.getAmount())
            );
            System.out.println("Saving...");
            customerDAO.saveDayPlan(dayPlan);
            customerDAO.save(customer);
            recipeDAO.save(recipe);
            System.out.println("Saved");
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during adding product");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public List<Grocery> getGroceryListOfCustomerOfId(Long id) {
        return customerDAO.getCustomerProducts(id)
                .stream()
                .filter(customerProduct -> customerProduct.getAmount()<0)
                .map(customerProduct ->
                    new Grocery()
                            .withName(customerProduct.getProduct().getName())
                            .withAmount(calculateWantedAmount(customerProduct))
                            .withBaseUnit(customerProduct.getProduct().getBaseUnit()))
                .collect(Collectors.toList());
    }

    private float calculateWantedAmount(CustomerProduct customerProduct) {
        return (float)Math.ceil(-customerProduct.getAmount()/customerProduct.getProduct().getBaseAmount().intValue())
                *customerProduct.getProduct().getBaseAmount().intValue();
    }
}
