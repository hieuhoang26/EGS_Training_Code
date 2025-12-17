package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.ShoppingListRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingListRecipeRepository
        extends JpaRepository<ShoppingListRecipe, UUID> {
}
