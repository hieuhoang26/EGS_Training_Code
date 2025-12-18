package com.hhh.recipe_mn.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
public class ShoppingListRecipeResponse {
    private UUID recipeId;
    private String recipeName;
}
