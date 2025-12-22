package com.hhh.recipe_mn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "unit_conversions")
@Getter
@Setter
public class UnitConversion {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "from_unit")
    private String fromUnit;

    @Column(name = "to_unit")
    private String toUnit;

    @Column(name = "multiplier", precision = 10, scale = 4)
    private BigDecimal multiplier;
}