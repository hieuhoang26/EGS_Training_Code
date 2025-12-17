package com.hhh.recipe_mn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientResponse {
    private String ingredientName;
    private BigDecimal quantity;
    private String unit;
}
