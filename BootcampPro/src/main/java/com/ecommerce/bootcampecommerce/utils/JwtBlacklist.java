package com.ecommerce.bootcampecommerce.utils;

import com.ecommerce.bootcampecommerce.entity.User;

import javax.persistence.*;

@Entity
@Table(name = "jwt_blacklist")
public class JwtBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;

    @OneToOne
    @JoinColumn(name = "token_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "JwtBlacklist{" +
                "_id='" + id + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
