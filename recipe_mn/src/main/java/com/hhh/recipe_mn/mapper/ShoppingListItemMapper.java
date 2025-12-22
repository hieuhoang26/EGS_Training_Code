package com.hhh.recipe_mn.mapper;

import com.hhh.recipe_mn.dto.response.ShoppingListItemResponse;
import com.hhh.recipe_mn.model.ShoppingListItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


public interface ShoppingListItemMapper {

    @Mapping(target = "ingredientId", source = "ingredient.id")
    @Mapping(target = "ingredientName", source = "ingredient.canonicalName")
    ShoppingListItemResponse toResponse(ShoppingListItem item);

    List<ShoppingListItemResponse> toResponses(List<ShoppingListItem> items);
}
