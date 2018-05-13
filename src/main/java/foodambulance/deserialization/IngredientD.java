package foodambulance.deserialization;

import foodambulance.model.RecipeIngredient;

public class IngredientD {

    private String name;

    private Float amount;

    private String baseUnit;

    public IngredientD(RecipeIngredient ingredient) {
        this.name = ingredient.getProduct().getName();
        this.amount = ingredient.getAmount();
        this.baseUnit = ingredient.getProduct().getBaseUnit();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }
}
