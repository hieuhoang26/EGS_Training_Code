package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.model.ShoppingList;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {
    ShoppingList generateFromRecipes(
            UUID userId,
            String name,
            List<UUID> recipeIds
    );
    ShoppingList getDetail(UUID shoppingListId, UUID userId);
}
