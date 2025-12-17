package com.hhh.recipe_mn.service.imp;

import java.math.BigDecimal;
import java.util.UUID;

public interface RecipeIngredientView {
    UUID getRecipeId();

    UUID getIngredientId();

    String getCanonicalName();

    BigDecimal getQuantity();

    String getUnit();
}
