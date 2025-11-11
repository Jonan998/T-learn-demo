package com.example.dto;

import java.time.LocalDate;

public class UserDto {
    private Integer id;
    private String name;
    private LocalDate created_at_new;
    private LocalDate created_at_repeat;
    private int limit_new;
    private int limit_repeat;
    
    public UserDto() {}

    public UserDto(String name,
                String password,
                LocalDate created_at_new,
                LocalDate created_at_repeat,
                int limit_new,
                int limit_repeat) {
        this.name = name;
        this.created_at_new = created_at_new;
        this.created_at_repeat = created_at_repeat;
        this.limit_new = limit_new;
        this.limit_repeat = limit_repeat;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getCreated_at_new() {return created_at_new;}
    public void setCreated_at_new(LocalDate created_at_new) {this.created_at_new = created_at_new;}

    public LocalDate getCreated_at_repeat() {return created_at_repeat;}
    public void setCreated_at_repeat(LocalDate created_at_repeat) {this.created_at_repeat = created_at_repeat;}

    public int getLimit_new() {return limit_new;}
    public void setLimit_new(int limit_new) {this.limit_new = limit_new;}

    public int getLimit_repeat() {return limit_repeat;}
    public void setLimit_repeat(int limit_repeat) {this.limit_repeat = limit_repeat;}
}