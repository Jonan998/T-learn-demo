package com.example.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;

    public User() {}

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Integer getID(){return id;}
    public void setID(Integer id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
}
