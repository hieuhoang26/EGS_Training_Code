package com.hhh.recipe_mn.utlis;

import com.hhh.recipe_mn.dto.request.IngredientKey;
import com.hhh.recipe_mn.model.RecipeIngredient;
import com.hhh.recipe_mn.service.UnitConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IngredientAggregator {

    private final UnitResolver unitResolver;
    private final UnitConversionService unitConversionService;

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
    public Map<IngredientKey, BigDecimal> aggregateV2(
            List<RecipeIngredient> items
    ) {

        Map<IngredientKey, BigDecimal> result = new HashMap<>();

        for (RecipeIngredient item : items) {

            String baseUnit =
                    unitResolver.resolveBaseUnit(item.getUnit());

            BigDecimal normalizedQty =
                    unitConversionService.convert(
                            item.getQuantity(),
                            item.getUnit(),
                            baseUnit
                    );

            IngredientKey key = new IngredientKey(
                    item.getIngredient(),
                    baseUnit
            );

            result.merge(
                    key,
                    normalizedQty,
                    BigDecimal::add
            );
        }

        return result;
    }
}
