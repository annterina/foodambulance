package foodambulance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
@JsonIgnoreProperties(value={ "customerProducts", "recipes", "dayPlans"}, allowGetters=true, ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column
    private String mail;

    @Column
    private String passwordHash;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer-product")
    private Set<CustomerProduct> customerProducts = new HashSet<>();

    @ManyToMany(mappedBy = "customers", fetch = FetchType.EAGER)
    private Set<Recipe> recipes = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private Set<DayPlan> dayPlans;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<CustomerProduct> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(Set<CustomerProduct> customerProducts) {
        this.customerProducts = customerProducts;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public Set<DayPlan> getDayPlans() {
        return dayPlans;
    }

    public void setDayPlans(Set<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }
}
