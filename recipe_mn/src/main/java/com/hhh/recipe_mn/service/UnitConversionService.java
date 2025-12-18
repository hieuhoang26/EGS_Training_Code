package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.model.UnitConversion;
import com.hhh.recipe_mn.repository.UnitConversionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UnitConversionService {
    private final UnitConversionRepository repository;

    public BigDecimal convert(
            BigDecimal value,
            String fromUnit,
            String toUnit
    ) {

        if (fromUnit.equalsIgnoreCase(toUnit)) {
            return value;
        }

        UnitConversion conversion = repository
                .findByFromUnitAndToUnit(fromUnit, toUnit)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No conversion from " + fromUnit + " to " + toUnit
                        )
                );

        return value.multiply(conversion.getMultiplier());
    }
}
