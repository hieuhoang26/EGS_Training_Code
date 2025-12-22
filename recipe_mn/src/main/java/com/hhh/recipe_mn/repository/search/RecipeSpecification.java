package com.hhh.recipe_mn.repository.search;

import com.hhh.recipe_mn.dto.request.RecipeSearchRequest;
import com.hhh.recipe_mn.model.Cuisine;
import com.hhh.recipe_mn.model.Ingredient;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.model.RecipeIngredient;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeSpecification {
    public Specification<Recipe> buildSpecification(RecipeSearchRequest request) {
        return Specification
                .where(hasPublicStatus())
                .and(withIngredients(request.getIngredients()))
                .and(withCuisine(request.getCuisine()));
    }

    private Specification<Recipe> hasPublicStatus() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isPublic"));
    }

    private Specification<Recipe> withIngredients(List<String> ingredientNames) {
        if (ingredientNames == null || ingredientNames.isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            // Join RecipeIngredient and  Ingredient
            Join<Recipe, RecipeIngredient> recipeIngredientJoin = root.join("ingredients", JoinType.INNER);
            Join<RecipeIngredient, Ingredient> ingredientJoin = recipeIngredientJoin.join("ingredient", JoinType.INNER);

            //  list condition OR for each ingredient
            List<Predicate> predicates = new ArrayList<>();
            for (String ingredientName : ingredientNames) {
                String pattern = "%" + ingredientName.toLowerCase() + "%";
                Predicate predicate = criteriaBuilder.like(
                        criteriaBuilder.lower(ingredientJoin.get("canonicalName")),
                        pattern
                );
                predicates.add(predicate);
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<Recipe> withCuisine(String cuisineCode) {
        if (cuisineCode == null || cuisineCode.trim().isEmpty()) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            Join<Recipe, Cuisine> cuisineJoin = root.join("cuisine", JoinType.INNER);
            return criteriaBuilder.equal(cuisineJoin.get("code"), cuisineCode);
        };
    }
}
