package foodambulance.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RECIPE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
@JsonIgnoreProperties(value={ "ingredients"}, allowGetters=true)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "CUSTOMER_RECIPE",
            joinColumns = { @JoinColumn(name = "recipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "customer_id") }
    )
    @JsonIgnore
    private Set<Customer> customers;

    @Column(name = "PUBLIC")
    private boolean isPublic = true;

    @OneToMany(mappedBy = "recipe")
    @JsonBackReference(value = "recipe-recipeIngredient")
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    @ManyToMany(mappedBy = "recipes")
    @JsonIgnore
    private Set<DayPlan> dayPlans;

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

    public Set<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomer(Set<Customer> customers) {
        this.customers = customers;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void addCustomer(Customer customer){this.customers.add(customer);}

    public Set<DayPlan> getDayPlans() {
        return dayPlans;
    }

    public void setDayPlans(Set<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }
}
