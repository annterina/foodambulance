package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    @Column
    private Integer categoryId;

    @Column
    private String name;

    @Column
    private Float baseAmount;

    @Column
    private String baseUnit;

    @Column
    private Integer baseExpirationDate;

    @OneToMany(mappedBy = "product")
    private Set<CustomerProduct> customerProducts = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
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
}
