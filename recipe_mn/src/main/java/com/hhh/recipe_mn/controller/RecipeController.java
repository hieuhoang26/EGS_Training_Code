package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.dto.request.CreateRecipeRequest;
import com.hhh.recipe_mn.dto.request.RecipeSearchRequest;
import com.hhh.recipe_mn.dto.request.UpdateRecipeRequest;
import com.hhh.recipe_mn.dto.response.PageResponse;
import com.hhh.recipe_mn.dto.response.RecipeResponse;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.ResponseError;
import com.hhh.recipe_mn.mapper.RecipeMapper;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.service.RecipeService;
import com.hhh.recipe_mn.service.SearchService;
import com.hhh.recipe_mn.utlis.PageMapper;
import com.hhh.recipe_mn.utlis.Uri;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(Uri.RECIPE)
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;
    private final SearchService searchService;
    private final RecipeMapper recipeMapper;

    @PostMapping("/{userId}")
    @PreAuthorize("hasAuthority('RECIPE:CREATE')")
    public ResponseData<?> create(@PathVariable UUID userId, @Valid @RequestBody CreateRecipeRequest request) {
        try {
            UUID rs = recipeService.create(userId, request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Success", rs);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }
    }

    @PutMapping("/{recipeId}")
    @PreAuthorize("hasAuthority('RECIPE:UPDATE')")
    public ResponseData<?> update(@PathVariable UUID recipeId, @RequestParam UUID userId, @Valid @RequestBody UpdateRecipeRequest request) {
        try {
            recipeService.update(recipeId, userId, request);
            return new ResponseData<>(HttpStatus.OK.value(), "Success");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Fail");
        }
    }

    @PostMapping("/search")
    public ResponseData<?> search(@RequestBody RecipeSearchRequest request) {
        try {
            Page<Recipe> rs = searchService.searchRecipesWithAllIngredients(request);
            PageResponse<List<RecipeResponse>> pageResponse =
                    PageMapper.toPageResponse(rs, recipeMapper::toResponse);
            return new ResponseData<>(HttpStatus.OK.value(), "Success", pageResponse);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }
    }
}
