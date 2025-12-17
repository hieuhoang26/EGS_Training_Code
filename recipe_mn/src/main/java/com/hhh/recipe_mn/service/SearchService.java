package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.RecipeSearchRequest;
import com.hhh.recipe_mn.dto.response.PageResponse;
import com.hhh.recipe_mn.model.Ingredient;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.model.RecipeIngredient;
import com.hhh.recipe_mn.model.User;

import com.hhh.recipe_mn.repository.RecipeRepository;
import com.hhh.recipe_mn.repository.search.RecipeSpecification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final RecipeRepository recipeRepository;
    private final RecipeSpecification recipeSpecification;

    public Page<Recipe> searchRecipes(RecipeSearchRequest request) {

        Specification<Recipe> spec = recipeSpecification.buildSpecification(request);


        Sort sort = createSort(request.getSortBy(), request.getDirection());

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        return recipeRepository.findAll(spec, pageable);
    }

    private Sort createSort(String sortBy, Sort.Direction direction) {
//        String fieldName = mapSortField(sortBy);
        return Sort.by(direction, sortBy);
    }

    public Page<Recipe> searchRecipesWithAllIngredients(RecipeSearchRequest request) {
        if (!request.hasIngredientCriteria()) {
            return searchRecipes(request);
        }

        Specification<Recipe> baseSpec = recipeSpecification.buildSpecification(request);

        Specification<Recipe> allIngredientsSpec = (root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Recipe> subRoot = subquery.from(Recipe.class);

            Join<Recipe, RecipeIngredient> recipeIngredientJoin = subRoot.join("ingredients");
            Join<RecipeIngredient, Ingredient> ingredientJoin = recipeIngredientJoin.join("ingredient");

            List<Predicate> ingredientPredicates = request.getIngredients().stream()
                    .map(ingredientName -> {
                        String pattern = "%" + ingredientName.toLowerCase() + "%";
                        return criteriaBuilder.like(
                                criteriaBuilder.lower(ingredientJoin.get("canonicalName")),
                                pattern
                        );
                    })
                    .toList();

            subquery.select(criteriaBuilder.countDistinct(ingredientJoin.get("id")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(subRoot.get("id"), root.get("id")),
                            criteriaBuilder.or(ingredientPredicates.toArray(new Predicate[0]))
                    ));

            return criteriaBuilder.greaterThanOrEqualTo(subquery, (long) request.getIngredients().size());
        };

        Specification<Recipe> finalSpec = Specification.where(baseSpec).and(allIngredientsSpec);

        Sort sort = createSort(request.getSortBy(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        return recipeRepository.findAll(finalSpec, pageable);
    }

}
