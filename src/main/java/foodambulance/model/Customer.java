package foodambulance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
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
    @JsonBackReference
    private Map<Long, CustomerProduct> customerProducts = new HashMap<>();

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private Set<Recipe> recipes = new HashSet<>();

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

    public Map<Long, CustomerProduct> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(Map<Long, CustomerProduct> customerProducts) {
        this.customerProducts = customerProducts;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
