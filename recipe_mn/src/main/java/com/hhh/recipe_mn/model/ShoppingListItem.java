package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "shopping_list_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"shopping_list_id", "ingredient_id", "unit"})
        })
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingListItem {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity", precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "is_checked")
    private Boolean isChecked = false;

    // Constructors, getters, setters
}