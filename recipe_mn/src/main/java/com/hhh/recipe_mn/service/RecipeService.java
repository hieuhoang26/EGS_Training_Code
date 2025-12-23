package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.CreateRecipeRequest;
import com.hhh.recipe_mn.dto.request.UpdateRecipeRequest;

import java.util.UUID;

public interface RecipeService {
    UUID create(UUID userId, CreateRecipeRequest request);

    void update(UUID recipeId, UUID userId, UpdateRecipeRequest request);
}
