package foodambulance.deserialization;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import foodambulance.model.Customer;
import foodambulance.model.DayPlan;
import foodambulance.model.Recipe;

import java.util.HashSet;
import java.util.Set;

public class RecipeD {

    private Long id;

    private String name;

    private Set<Customer> customers;

    private boolean isPublic = true;

    private Set<IngredientD> ingredients = new HashSet<>();

    private Set<DayPlan> dayPlans;

    public RecipeD(Recipe recipe){
        this.id = recipe.getId();
        this.name = recipe.getName();
        recipe.getIngredients().forEach(ingredient -> this.ingredients.add(new IngredientD(ingredient)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<IngredientD> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientD> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<DayPlan> getDayPlans() {
        return dayPlans;
    }

    public void setDayPlans(Set<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }
}
