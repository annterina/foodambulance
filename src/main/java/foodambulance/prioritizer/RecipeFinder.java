package foodambulance.prioritizer;

import foodambulance.model.CustomerProduct;
import foodambulance.model.Recipe;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class RecipePrioritizer {

    private Set<CustomerProduct> customerProducts;
    private Set<Recipe> customerRecipes;
    private Set<ComparedRecipe> comparedRecipes;

    public RecipePrioritizer(Set<CustomerProduct> customerProducts, Set<Recipe> customerRecipes) {
        this.customerProducts = customerProducts;
        this.customerRecipes = customerRecipes;
        comparedRecipes = new HashSet<>();
    }

    public List<Recipe> sortRecipes() {
        PriorityQueue<Recipe> priorityQueue = new PriorityQueue<>();
        customerRecipes.stream().forEach(customerRecipe -> createComparedRecipesSet(customerRecipe));
        comparedRecipes.stream().forEach(comparedRecipe -> setMissingProducts(comparedRecipe, customerProducts));

    }

    public Integer setMissingProducts(Recipe recipe, Set<CustomerProduct> customerProducts) {

    }

    public void createComparedRecipesSet(Recipe recipe) {
        comparedRecipes.add(new ComparedRecipe(recipe));
    }
}
