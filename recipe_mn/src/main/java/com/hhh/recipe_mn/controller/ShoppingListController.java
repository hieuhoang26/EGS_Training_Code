package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.dto.request.GenerateShoppingListRequest;
import com.hhh.recipe_mn.model.ShoppingList;
import com.hhh.recipe_mn.service.ShoppingListService;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/shopping-lists")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<?> generate(
            @RequestBody GenerateShoppingListRequest req,
            @PathVariable UUID userId
            ) {
        ShoppingList id = shoppingListService.generateFromRecipes(
                userId,
                req.getName(),
                req.getRecipeIds()
        );
        return ResponseEntity.ok(Map.of("shoppingListId", id));
    }
}
