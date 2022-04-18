package com.ecommerce.bootcampecommerce.entity;

import javax.persistence.*;

@Entity
public class ProductReview {
    @Id
    private Long id;
    private String review;
    private String rating;

    @OneToOne
    @JoinColumn(name = "customerUserId")
    @MapsId
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "productId")
    @MapsId
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
