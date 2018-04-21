package foodambulance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "RECIPE_INGREDIENT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    @JsonManagedReference(value = "recipe-recipeIngredient")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @JsonManagedReference
    private Product product;

    @Column
    private Float amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

}
