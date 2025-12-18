package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnitConversionRepository
        extends JpaRepository<UnitConversion, UUID> {

    Optional<UnitConversion> findByFromUnitAndToUnit(
            String fromUnit,
            String toUnit
    );
}