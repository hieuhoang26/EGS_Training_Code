package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "cuisines")
@Getter
@Setter
public class Cuisine extends AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;


    @Column(nullable = false)
    private String name;
}