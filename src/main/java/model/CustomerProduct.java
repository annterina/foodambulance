package model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CUSTOMER_PRODUCT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"}), @UniqueConstraint(columnNames = {"PRODUCT_ID"}), @UniqueConstraint(columnNames = {"CUSTOMER_ID"})})
public class CustomerProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column
    private LocalDateTime oldestDate;

    @Column
    private Float amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getOldestDate() {
        return oldestDate;
    }

    public void setOldestDate(LocalDateTime oldestDate) {
        this.oldestDate = oldestDate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }


}
