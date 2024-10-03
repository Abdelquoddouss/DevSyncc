package com.devsync.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "users")
public class User {

    // Getters et Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "username" ,unique = true, nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "prenom" , nullable = false)
    private String prenom;

    @Column(name = "email" ,unique = true, nullable = false)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype" , nullable = false)
    private UserType userType;

    public enum UserType {
        USER, MANAGER
    }

    public User() {}

    public User(String username, String name, String prenom, String email, String password, UserType userType) {
        this.username = username;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }


}


