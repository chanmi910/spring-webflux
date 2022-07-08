package com.example.springwebflux.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User {

    @Id
    private Long id;
    private String name;
    private int age;
    private String department;

    public User() {
    }

    public User(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public User(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }
}
