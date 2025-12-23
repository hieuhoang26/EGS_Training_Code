package com.hhh.recipe_mn.mapper;

import com.hhh.recipe_mn.dto.response.ShoppingListRecipeResponse;
import com.hhh.recipe_mn.model.ShoppingListRecipe;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ShoppingListRecipeMapper {

    //    @Mapping(target = "recipeId", source = "recipe.id")
    ShoppingListRecipeResponse toResponse(ShoppingListRecipe recipe);

    List<ShoppingListRecipeResponse> toResponses(Set<ShoppingListRecipe> recipes);
}
