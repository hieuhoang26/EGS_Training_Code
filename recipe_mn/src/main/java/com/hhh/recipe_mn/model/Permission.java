package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission extends AbstractEntity {

    @Id
   @GeneratedValue
   private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;




}