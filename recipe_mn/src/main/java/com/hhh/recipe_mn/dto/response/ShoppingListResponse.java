package com.hhh.recipe_mn.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ShoppingListResponse {
    private UUID id;
    private String name;

    private List<ShoppingListItemResponse> items;
    private List<ShoppingListRecipeResponse> recipes;
}
