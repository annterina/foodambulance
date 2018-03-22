package model;

import javax.persistence.*;

@Entity
@Table(name = "RECIPE_INGREDIENT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"}), @UniqueConstraint(columnNames = {"RECIPE_ID"}), @UniqueConstraint(columnNames = {"PRODUCT_ID"})})
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
