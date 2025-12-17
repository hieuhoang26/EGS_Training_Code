package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.CreateRecipeRequest;
import com.hhh.recipe_mn.dto.request.UpdateRecipeRequest;
import com.hhh.recipe_mn.dto.response.PageResponse;
import com.hhh.recipe_mn.dto.response.RecipeResponse;
import com.hhh.recipe_mn.model.Recipe;

import java.util.UUID;

public interface RecipeService {
    UUID create(UUID userId, CreateRecipeRequest request);
    void update(UUID recipeId, UUID userId, UpdateRecipeRequest request);

//    PageResponse<?> searchWithPagingAndSorting(int pageNo, int pageSize, String sortBy, String cuisine, String... search);
}
