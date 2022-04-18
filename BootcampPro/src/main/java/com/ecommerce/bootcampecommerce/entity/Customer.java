package com.ecommerce.bootcampecommerce.entity;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "customer")
public class Customer{
    @Id
    private Long userId;

    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "userId")
    @MapsId
    private User user;
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

