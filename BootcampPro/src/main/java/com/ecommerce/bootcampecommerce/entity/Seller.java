package com.ecommerce.bootcampecommerce.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seller")
public class Seller{
    @Id
    private Long userId;
    private String GST;
    private String companyContact;
    private String companyName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "sellerUserId" , referencedColumnName = "userId")
    private Set<Product> products = new HashSet<>();


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addProducts(Product product) {
        if (product != null) {
            if (products == null) {
                products = new HashSet<>();
            }
            products.add(product);
        }
    }
}
