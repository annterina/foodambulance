package foodambulance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
@JsonIgnoreProperties(value={ "customerProducts", "recipeIngredients" }, allowGetters=true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column
    private Long categoryId;

    @Column
    private String name;

    @Column
    private Float baseAmount;

    @Column
    private String baseUnit;

    @Column
    private Integer baseExpirationDate;

    @OneToMany(mappedBy = "product")
    @JsonBackReference(value = "customerProduct-product")
    private Set<CustomerProduct> customerProducts = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Float baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Integer getBaseExpirationDate() {
        return baseExpirationDate;
    }

    public void setBaseExpirationDate(Integer baseExpirationDate) {
        this.baseExpirationDate = baseExpirationDate;
    }

    public Set<CustomerProduct> getCustomerProducts() {
        return customerProducts;
    }

    public void setCustomerProducts(Set<CustomerProduct> customerProducts) {
        this.customerProducts = customerProducts;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

}
