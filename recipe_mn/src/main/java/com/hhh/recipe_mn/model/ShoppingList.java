package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "shopping_lists")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingList extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name")
    private String name;


    @OneToMany(
            mappedBy = "shoppingList",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ShoppingListItem> items = new ArrayList<>();

    @OneToMany(
            mappedBy = "shoppingList",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ShoppingListRecipe> shoppingListRecipes = new HashSet<>();

}