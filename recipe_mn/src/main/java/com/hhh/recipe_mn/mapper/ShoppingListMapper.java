package com.hhh.recipe_mn.mapper;

import com.hhh.recipe_mn.dto.response.ShoppingListResponse;
import com.hhh.recipe_mn.model.ShoppingList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                ShoppingListItemMapper.class,
                ShoppingListRecipeMapper.class
        }
)
public interface ShoppingListMapper {

//    @Mapping(target = "recipes", source = "shoppingListRecipes")
    ShoppingListResponse toResponse(ShoppingList shoppingList);

    List<ShoppingListResponse> toResponses(List<ShoppingList> shoppingLists);
}
