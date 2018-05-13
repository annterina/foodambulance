package foodambulance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CUSTOMER_PRODUCT",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ID"})})
public class CustomerProduct {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonManagedReference(value = "customer-product")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @JsonManagedReference(value = "customerProduct-product")
    private Product product;

    @Column
    private LocalDateTime newestBuyDate;

    @Column
    private Float amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getNewestBuyDate() {
        return newestBuyDate;
    }

    public void setNewestBuyDate(LocalDateTime newestBuyDate) {
        this.newestBuyDate = newestBuyDate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }


}
