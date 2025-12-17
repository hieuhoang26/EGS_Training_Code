package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredient extends AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "canonical_name", nullable = false, unique = true)
    private String canonicalName;

    // recipe_ingredient
    @OneToMany(mappedBy = "ingredient")
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    // Shopping List Item
    @OneToMany(mappedBy = "ingredient")
    private List<ShoppingListItem>  shoppingListItems = new ArrayList<>();
}
