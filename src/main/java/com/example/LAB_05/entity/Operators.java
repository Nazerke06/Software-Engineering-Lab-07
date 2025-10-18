package com.example.LAB_05.entity;

import jakarta.persistence.*;

public class Operators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @ManyToMany(fetch = FetchType.LAZY)
    private String department;
}
