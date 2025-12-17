package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "shopping_list_recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListRecipe extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(name = "recipe_name")
    private String recipeName;

    // Constructors, getters, setters
}
