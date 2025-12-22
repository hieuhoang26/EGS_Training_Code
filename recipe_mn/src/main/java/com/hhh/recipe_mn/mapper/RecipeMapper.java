package com.hhh.recipe_mn.mapper;

import com.hhh.recipe_mn.dto.response.RecipeIngredientResponse;
import com.hhh.recipe_mn.dto.response.RecipeResponse;
import com.hhh.recipe_mn.dto.response.RecipeStepResponse;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.model.RecipeImage;
import com.hhh.recipe_mn.model.RecipeIngredient;
import com.hhh.recipe_mn.model.RecipeStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
//    @Mapping(target = "name", source = "name")
//    @Mapping(target = "cuisineName", source = "cuisine.name")
//    @Mapping(target = "cuisineCode", source = "cuisine.code")
//    @Mapping(target = "steps", source = "steps")
//    @Mapping(target = "ingredients", source = "ingredients")
//    @Mapping(target = "imageUrls", source = "images")
    RecipeResponse toResponse(Recipe recipe);

    List<RecipeResponse> toResponseList(List<Recipe> recipes);


//    @Mapping(target = "ingredientName", source = "ingredient.canonicalName")
    RecipeIngredientResponse toIngredientResponse(RecipeIngredient entity);

    RecipeStepResponse toStepResponse(RecipeStep entity);



}
