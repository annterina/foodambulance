package foodambulance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DAYPLAN",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class DayPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "DAYPLAN_RECIPE",
            joinColumns = { @JoinColumn(name = "dayPlan_id") },
            inverseJoinColumns = { @JoinColumn(name = "recipe_id") }
    )
    private Set<Recipe> recipes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonManagedReference
    private Customer customer;

    @Column
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
