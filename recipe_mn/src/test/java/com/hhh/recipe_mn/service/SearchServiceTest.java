package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.RecipeSearchRequest;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.repository.RecipeRepository;
import com.hhh.recipe_mn.repository.search.RecipeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeSpecification recipeSpecification;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchService = new SearchService(recipeRepository, recipeSpecification);
    }

    // searchRecipes
    @Test
    void searchRecipes_ShouldReturnPage() {
        RecipeSearchRequest request = new RecipeSearchRequest();
        request.setPage(0);
        request.setSize(10);
        request.setSortBy("name");
        request.setDirection(Sort.Direction.ASC);

        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getDirection(), request.getSortBy()));
        Page<Recipe> page = new PageImpl<>(recipes, pageable, recipes.size());

        // Mock specification + repository
        Specification<Recipe> spec = (root, query, cb) -> null;
        when(recipeSpecification.buildSpecification(request)).thenReturn(spec);
        when(recipeRepository.findAll(spec, pageable)).thenReturn(page);

        Page<Recipe> result = searchService.searchRecipes(request);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(recipeSpecification, times(1)).buildSpecification(request);
        verify(recipeRepository, times(1)).findAll(spec, pageable);
    }

    // searchRecipesWithAllIngredients - no ingredient criteria
    @Test
    void searchRecipesWithAllIngredients_NoIngredientCriteria_ShouldCallSearchRecipes() {
        RecipeSearchRequest request = mock(RecipeSearchRequest.class);
        when(request.hasIngredientCriteria()).thenReturn(false);

        // Partial mock: override searchRecipes method
        SearchService partialService = new SearchService(recipeRepository, recipeSpecification) {
            @Override
            public Page<Recipe> searchRecipes(RecipeSearchRequest req) {
                assertSame(request, req); // ensure the request is passed
                return Page.empty();
            }
        };

        Page<Recipe> result = partialService.searchRecipesWithAllIngredients(request);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
    }

    // searchRecipesWithAllIngredients - with ingredient criteria
    @Test
    void searchRecipesWithAllIngredients_WithIngredients_ShouldReturnPage() {
        RecipeSearchRequest request = new RecipeSearchRequest();
        request.setPage(0);
        request.setSize(10);
        request.setSortBy("name");
        request.setDirection(Sort.Direction.ASC);
        request.setIngredients(Arrays.asList("Flour", "Sugar"));

        Recipe recipe1 = new Recipe();
        List<Recipe> recipes = List.of(recipe1);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getDirection(), request.getSortBy()));
        Page<Recipe> page = new PageImpl<>(recipes, pageable, recipes.size());

        Specification<Recipe> baseSpec = (root, query, cb) -> null;

        when(recipeSpecification.buildSpecification(request)).thenReturn(baseSpec);
        when(recipeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<Recipe> result = searchService.searchRecipesWithAllIngredients(request);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(recipeSpecification, times(1)).buildSpecification(request); // baseSpec + used again
        verify(recipeRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
