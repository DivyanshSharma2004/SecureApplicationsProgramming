package com.secureApplication.demo.models;

import jakarta.persistence.*;


@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)//extra security to prevent objects with missing fields being inserted into the database
    private String username;
    @Column(nullable = false)
    private String password; //hashed before storing into database
    @Column(nullable = false, unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
