package com.hhh.recipe_mn.utlis;

import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class UnitResolver {

    private static final Map<String, String> BASE_UNITS = Map.of(
            "kg", "g",
            "g", "g",
            "mg", "g",
            "l", "ml",
            "ml", "ml",
            "tbsp", "ml",
            "tsp", "ml",
            "pcs", "pcs",
            "piece", "pcs"
    );

    public String resolveBaseUnit(String unit) {
        return BASE_UNITS.getOrDefault(unit, unit);
    }
}
