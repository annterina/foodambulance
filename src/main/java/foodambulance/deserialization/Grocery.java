package foodambulance.deserialization;

public class Grocery {

    private String name;

    private Float amount;

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

    public Grocery withName(String name){
        this.name = name;
        return this;
    }

    public Grocery withAmount(Float amount){
        this.amount = amount;
        return this;
    }

}
