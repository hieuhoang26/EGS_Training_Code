package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, UUID> {
}
