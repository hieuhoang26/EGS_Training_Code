package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.IngredientKey;
import com.hhh.recipe_mn.model.*;
import com.hhh.recipe_mn.repository.*;
import com.hhh.recipe_mn.service.ShoppingListService;
import com.hhh.recipe_mn.service.UserService;
import com.hhh.recipe_mn.utlis.IngredientAggregator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingListServiceImpl  implements ShoppingListService {
    private final UserService userService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListItemRepository shoppingListItemRepo;
    private final ShoppingListRecipeRepository shoppingListRecipeRepo;
    private final IngredientAggregator ingredientAggregator;
    @Override
    public ShoppingList  generateFromRecipes(UUID userId, String name, List<UUID> recipeIds) {

        User user = userService.getById(userId);
        // load recipe
        List<Recipe> recipes = recipeRepository.findByIdIn(recipeIds);

        if (recipes.isEmpty()) {
            throw new IllegalArgumentException("No recipes found");
        }
        // load recipe -  Ingredients
        List<RecipeIngredient> recipeIngredients =
                recipeIngredientRepository.findByRecipeIn(recipes);

        if (recipeIngredients.isEmpty()) {
            throw new IllegalStateException("Recipes have no ingredients");
        }

        // Aggregate
        Map<IngredientKey, BigDecimal> consolidated =
                ingredientAggregator.aggregate(recipeIngredients);

        // shopping list
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setUser(user);
        shoppingList.setName(name);

        shoppingListRepository.save(shoppingList);

        for (Recipe recipe : recipes) {
            ShoppingListRecipe link = new ShoppingListRecipe();
            link.setShoppingList(shoppingList);
            link.setRecipe(recipe);
            link.setRecipeName(recipe.getName());

            shoppingList.getShoppingListRecipes().add(link);
        }
        // shopping list items
        for (Map.Entry<IngredientKey, BigDecimal> entry : consolidated.entrySet()) {

            ShoppingListItem item = new ShoppingListItem();
            item.setShoppingList(shoppingList);
            item.setIngredient(entry.getKey().ingredient());
            item.setUnit(entry.getKey().unit());
            item.setQuantity(entry.getValue());
            item.setIsChecked(false);

            shoppingList.getItems().add(item);
        }

        return shoppingListRepository.save(shoppingList);


    }
}
