package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {

    @EntityGraph(attributePaths = {"user", "cuisine", "steps", "images", "ingredients.ingredient"})
    Page<Recipe> findAll(Specification<Recipe> spec, Pageable pageable);

    List<Recipe> findByIdIn(List<UUID> ids);
}