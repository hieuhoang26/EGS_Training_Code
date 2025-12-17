package com.hhh.recipe_mn.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CreateRecipeRequest {
    @NotBlank(message = "Recipe name is required")
    @Size(min = 3, max = 100, message = "Recipe name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Servings is required")
    @Min(value = 1, message = "Servings must be at least 1")
    @Max(value = 100, message = "Servings cannot exceed 100")
    private Integer servings;

    @Min(value = 0, message = "Preparation time cannot be negative")
    @Max(value = 1440, message = "Preparation time cannot exceed 24 hours")
    private Integer prepTimeMin;

    @Min(value = 0, message = "Cooking time cannot be negative")
    @Max(value = 1440, message = "Cooking time cannot exceed 24 hours")
    private Integer cookTimeMin;

    private Boolean isPublic = true;

    private UUID cuisineId;

    @Valid
    @NotEmpty(message = "At least one ingredient is required")
    private List<IngredientRequest> ingredients = new ArrayList<>();

    @Valid
    @NotEmpty(message = "At least one step is required")
    @Size(min = 1, max = 50, message = "Recipe must have between 1 and 50 steps")
    private List<StepRequest> steps = new ArrayList<>();

    private List<ImageRequest> images;
}