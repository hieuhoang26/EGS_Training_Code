package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.dto.request.GenerateShoppingListRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.ShoppingListResponse;
import com.hhh.recipe_mn.mapper.ShoppingListMapper;
import com.hhh.recipe_mn.model.ShoppingList;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.service.ShoppingListService;
import com.hhh.recipe_mn.utlis.Uri;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(Uri.SHOP)
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ShoppingListMapper shoppingListMapper;

    @PostMapping("/generate/{userId}")
    public ResponseData<ShoppingListResponse> generate(
            @RequestBody GenerateShoppingListRequest req,
            @PathVariable UUID userId
    ) {
        ShoppingList shoppingList = shoppingListService.generateFromRecipes(
                userId,
                req.getName(),
                req.getRecipeIds()
        );

        return
                new ResponseData<>(
                        HttpStatus.OK.value(),
                        "Generate shopping list successfully",
                        shoppingListMapper.toResponse(shoppingList)

                );
    }

    /**
     * Get shopping list detail
     */
    @GetMapping("/{id}")
    public ResponseData<ShoppingListResponse> getDetail(
            @PathVariable UUID id,
            @RequestParam UUID userId
    ) {
        ShoppingList shoppingList =
                shoppingListService.getDetail(id, userId);

        ShoppingListResponse response =
                shoppingListMapper.toResponse(shoppingList);

        return
                new ResponseData<>(
                        HttpStatus.OK.value(),
                        "Get shopping list detail successfully",
                        response

                );
    }
}
