package com.hhh.recipe_mn.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GenerateShoppingListRequest {
    private String name;
    private List<UUID> recipeIds;
}
