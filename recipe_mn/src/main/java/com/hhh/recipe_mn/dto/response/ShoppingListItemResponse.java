package com.hhh.recipe_mn.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ShoppingListItemResponse {
    private UUID id;

    private UUID ingredientId;
    private String ingredientName;

    private BigDecimal quantity;
    private String unit;
    private Boolean isChecked;
}
