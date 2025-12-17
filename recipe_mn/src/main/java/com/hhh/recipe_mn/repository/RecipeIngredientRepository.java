package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.model.RecipeIngredient;
import com.hhh.recipe_mn.service.imp.RecipeIngredientView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
    @Query("""
        SELECT 
            ri.recipe.id     AS recipeId,
            ri.ingredient.id AS ingredientId,
            i.canonicalName  AS canonicalName,
            ri.quantity      AS quantity,
            ri.unit          AS unit
        FROM RecipeIngredient ri
        JOIN ri.ingredient i
        WHERE ri.recipe.id IN :recipeIds
    """)
    List<RecipeIngredientView> findByRecipeIds(
            @Param("recipeIds") List<UUID> recipeIds
    );

    List<RecipeIngredient> findByRecipeIn(List<Recipe> recipes);
}


