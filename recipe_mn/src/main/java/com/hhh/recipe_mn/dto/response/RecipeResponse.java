package com.hhh.recipe_mn.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;




@Data
public class RecipeResponse {
    private UUID id;
    private String name;
    private String description;
    private Integer servings;
    private Integer prepTimeMin;
    private Integer cookTimeMin;
    private boolean isPublic;
    private String cuisineName;
    private String cuisineCode;
    private List<RecipeStepResponse> steps;
    private List<RecipeIngredientResponse> ingredients;
    private List<String> imageUrls;
}