package foodambulance.prioritizer;

import foodambulance.model.CustomerProduct;
import foodambulance.model.Recipe;
import foodambulance.model.RecipeIngredient;

import java.time.LocalDateTime;
import java.util.*;

public class RecipePrioritizer {

    private Map<Long, CustomerProduct> customerProducts;
    private Set<ComparedRecipe> comparedRecipes;

    public RecipePrioritizer(Set<CustomerProduct> customerProducts, Set<Recipe> customerRecipes) {
        this.customerProducts = new HashMap<>();
        customerProducts.stream().forEach(customerProduct -> createCustomerProductsMap(customerProduct));
        this.comparedRecipes = new HashSet<>();
        customerRecipes.stream().forEach(customerRecipe -> createComparedRecipesSet(customerRecipe));
    }

    private void createCustomerProductsMap(CustomerProduct customerProduct) {
        customerProducts.put(customerProduct.getProduct().getId(), customerProduct);
    }

    private void createComparedRecipesSet(Recipe recipe) {
        comparedRecipes.add(new ComparedRecipe(recipe));
    }

    public PriorityQueue<ComparedRecipe> sortRecipes() {
        comparedRecipes.stream().forEach(comparedRecipe -> setMissingProductsAndOldestDate(comparedRecipe, customerProducts));
        PriorityQueue<ComparedRecipe> priorityQueue = new PriorityQueue<>(new RecipeComparator());
        comparedRecipes.stream().forEach(comparedRecipe -> priorityQueue.add(comparedRecipe));
        return priorityQueue;
    }

    public void setMissingProductsAndOldestDate(ComparedRecipe comparedRecipe, Map<Long, CustomerProduct> products) {
        comparedRecipe.getRecipe().getIngredients().forEach(recipeIngredient -> {
            if (!(products.containsKey(recipeIngredient.getProduct().getId()) &&
                recipeIngredient.getAmount() <= products.get(recipeIngredient.getProduct().getId()).getAmount())) {
                comparedRecipe.setMissingProductsNumber(comparedRecipe.getMissingProductsNumber() + 1);
                comparedRecipe.getMissingProducts().add(recipeIngredient);
            }
            else {
                LocalDateTime newestBuyDateOfProduct =
                        products.get(recipeIngredient.getProduct().getId()).getNewestBuyDate();
                if (comparedRecipe.getNewestBuyDate().isAfter(newestBuyDateOfProduct))
                    comparedRecipe.setNewestBuyDate(newestBuyDateOfProduct);
            }
        });
    }

}
