package com.hhh.recipe_mn.dto.request;

import com.hhh.recipe_mn.model.Ingredient;

import java.util.UUID;

public record IngredientKey(
        Ingredient ingredient,
        String unit
) {}
