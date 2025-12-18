package com.hhh.recipe_mn.utlis;

import com.hhh.recipe_mn.dto.request.IngredientKey;
import com.hhh.recipe_mn.model.RecipeIngredient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IngredientAggregator {

    public Map<IngredientKey, BigDecimal> aggregate(
            List<RecipeIngredient> items
    ) {

        Map<IngredientKey, BigDecimal> result = new HashMap<>();

        for (RecipeIngredient item : items) {
            IngredientKey key = new IngredientKey(
                    item.getIngredient(),
                    item.getUnit()
            );

            result.merge(
                    key,
                    item.getQuantity(),
                    BigDecimal::add
            );
        }

        return result;
    }
}
