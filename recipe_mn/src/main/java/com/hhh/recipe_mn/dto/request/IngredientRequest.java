package com.hhh.recipe_mn.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class IngredientRequest {
    @NotNull(message = "Ingredient ID is required")
    private UUID ingredientId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
    @Digits(integer = 10, fraction = 3, message = "Quantity must have at most 3 decimal places")
    private BigDecimal quantity;

    @Size(max = 50, message = "Unit cannot exceed 50 characters")
    private String unit;
}