package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
