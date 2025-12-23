package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.IngredientKey;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.model.RecipeIngredient;
import com.hhh.recipe_mn.model.ShoppingList;
import com.hhh.recipe_mn.model.ShoppingListItem;
import com.hhh.recipe_mn.model.ShoppingListRecipe;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.RecipeIngredientRepository;
import com.hhh.recipe_mn.repository.RecipeRepository;
import com.hhh.recipe_mn.repository.ShoppingListRepository;
import com.hhh.recipe_mn.service.ShoppingListService;
import com.hhh.recipe_mn.service.UserService;
import com.hhh.recipe_mn.utlis.IngredientAggregator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ShoppingListServiceImpl implements ShoppingListService {
    private final UserService userService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final IngredientAggregator ingredientAggregator;

    @Override
    public ShoppingList generateFromRecipes(UUID userId, String name, List<UUID> recipeIds) {

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
                ingredientAggregator.aggregateV2(recipeIngredients);

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

    @Override
    @Transactional(readOnly = true)
    public ShoppingList getDetail(UUID shoppingListId, UUID userId) {
        ShoppingList shoppingList = shoppingListRepository
                .findDetailById(shoppingListId)
                .orElseThrow(() ->
                        new EntityNotFoundException("ShoppingList not found"));
        if (!shoppingList.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }

        return shoppingList;
    }
}
