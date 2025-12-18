package com.hhh.recipe_mn.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import com.hhh.recipe_mn.dto.request.IngredientKey;
import com.hhh.recipe_mn.model.*;
import com.hhh.recipe_mn.repository.RecipeIngredientRepository;
import com.hhh.recipe_mn.repository.RecipeRepository;
import com.hhh.recipe_mn.repository.ShoppingListRepository;
import com.hhh.recipe_mn.service.imp.ShoppingListServiceImpl;
import com.hhh.recipe_mn.utlis.IngredientAggregator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


class ShoppingListServiceTest {

    @InjectMocks
    private ShoppingListServiceImpl shoppingListService;

    @Mock
    private UserService userService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @Mock
    private IngredientAggregator ingredientAggregator;

    private User user;
    private Recipe recipe;
    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("John");

        ingredient = new Ingredient();
        ingredient.setId(UUID.randomUUID());
        ingredient.setCanonicalName("Tomato");

        recipe = new Recipe();
        recipe.setId(UUID.randomUUID());
        recipe.setName("Tomato Soup");
    }

    @Test
    void generateFromRecipes_Success() {
        UUID userId = user.getId();
        String shoppingListName = "Weekly List";
        List<UUID> recipeIds = List.of(recipe.getId());

        // Mock user
        when(userService.getById(userId)).thenReturn(user);

        // Mock recipes
        when(recipeRepository.findByIdIn(recipeIds)).thenReturn(List.of(recipe));

        // Mock recipe ingredients
        RecipeIngredient ri = new RecipeIngredient();
        ri.setRecipe(recipe);
        ri.setIngredient(ingredient);
        ri.setQuantity(BigDecimal.valueOf(2));
        ri.setUnit("pcs");

        when(recipeIngredientRepository.findByRecipeIn(List.of(recipe)))
                .thenReturn(List.of(ri));

        // Mock aggregation
        IngredientKey key = new IngredientKey(ingredient, "pcs");
        Map<IngredientKey, BigDecimal> aggregated = Map.of(key, BigDecimal.valueOf(2));
        when(ingredientAggregator.aggregateV2(List.of(ri))).thenReturn(aggregated);

        // Mock saving shopping list
        when(shoppingListRepository.save(any(ShoppingList.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Call method
        ShoppingList shoppingList = shoppingListService.generateFromRecipes(userId, shoppingListName, recipeIds);

        // Assertions
        assertNotNull(shoppingList);
        assertEquals(user, shoppingList.getUser());
        assertEquals(shoppingListName, shoppingList.getName());
        assertEquals(1, shoppingList.getShoppingListRecipes().size());
        assertEquals(1, shoppingList.getItems().size());

        ShoppingListItem item = shoppingList.getItems().get(0);
        assertEquals(ingredient, item.getIngredient());
        assertEquals("pcs", item.getUnit());
        assertEquals(BigDecimal.valueOf(2), item.getQuantity());
        assertFalse(item.getIsChecked());
    }

    @Test
    void generateFromRecipes_NoRecipesFound_ShouldThrow() {
        UUID userId = user.getId();
        List<UUID> recipeIds = List.of(UUID.randomUUID());

        when(userService.getById(userId)).thenReturn(user);
        when(recipeRepository.findByIdIn(recipeIds)).thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () ->
                shoppingListService.generateFromRecipes(userId, "List", recipeIds)
        );
    }

    @Test
    void generateFromRecipes_RecipesHaveNoIngredients_ShouldThrow() {
        UUID userId = user.getId();
        List<UUID> recipeIds = List.of(recipe.getId());

        when(userService.getById(userId)).thenReturn(user);
        when(recipeRepository.findByIdIn(recipeIds)).thenReturn(List.of(recipe));
        when(recipeIngredientRepository.findByRecipeIn(List.of(recipe))).thenReturn(Collections.emptyList());

        assertThrows(IllegalStateException.class, () ->
                shoppingListService.generateFromRecipes(userId, "List", recipeIds)
        );
    }
    @Test
    void generateFromRecipes_MultipleRecipes_MultipleIngredients() {
        UUID userId = user.getId();
        String shoppingListName = "Weekly Mega List";

        // 2 recipes
        Recipe recipe1 = new Recipe();
        recipe1.setId(UUID.randomUUID());
        recipe1.setName("Tomato Soup");

        Recipe recipe2 = new Recipe();
        recipe2.setId(UUID.randomUUID());
        recipe2.setName("Tomato Salad");

        List<Recipe> recipes = List.of(recipe1, recipe2);
        List<UUID> recipeIds = Arrays.asList(recipe1.getId(), recipe2.getId());

        // 2 ingredients, nhưng tomato xuất hiện ở cả 2 recipe
        Ingredient tomato = new Ingredient();
        tomato.setId(UUID.randomUUID());
        tomato.setCanonicalName("Tomato");

        Ingredient onion = new Ingredient();
        onion.setId(UUID.randomUUID());
        onion.setCanonicalName("Onion");

        // RecipeIngredient cho mỗi recipe
        RecipeIngredient ri1 = new RecipeIngredient();
        ri1.setRecipe(recipe1);
        ri1.setIngredient(tomato);
        ri1.setQuantity(BigDecimal.valueOf(2));
        ri1.setUnit("pcs");

        RecipeIngredient ri2 = new RecipeIngredient();
        ri2.setRecipe(recipe2);
        ri2.setIngredient(tomato);
        ri2.setQuantity(BigDecimal.valueOf(3));
        ri2.setUnit("pcs");

        RecipeIngredient ri3 = new RecipeIngredient();
        ri3.setRecipe(recipe2);
        ri3.setIngredient(onion);
        ri3.setQuantity(BigDecimal.valueOf(1));
        ri3.setUnit("pcs");

        List<RecipeIngredient> recipeIngredients = List.of(ri1, ri2, ri3);

        // Mock dependencies
        when(userService.getById(userId)).thenReturn(user);
        when(recipeRepository.findByIdIn(recipeIds)).thenReturn(recipes);
        when(recipeIngredientRepository.findByRecipeIn(recipes)).thenReturn(recipeIngredients);

        // Mock aggregator gộp nguyên liệu trùng
        Map<IngredientKey, BigDecimal> aggregated = new HashMap<>();
        aggregated.put(new IngredientKey(tomato, "pcs"), BigDecimal.valueOf(5)); // 2 + 3
        aggregated.put(new IngredientKey(onion, "pcs"), BigDecimal.valueOf(1));
        when(ingredientAggregator.aggregateV2(recipeIngredients)).thenReturn(aggregated);

        // Mock save
        when(shoppingListRepository.save(any(ShoppingList.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Call method
        ShoppingList shoppingList = shoppingListService.generateFromRecipes(userId, shoppingListName, recipeIds);

        // Assertions
        assertNotNull(shoppingList);
        assertEquals(2, shoppingList.getShoppingListRecipes().size()); // 2 recipe links
        assertEquals(2, shoppingList.getItems().size()); // 2 distinct ingredients

        // Check aggregated quantities
        Map<UUID, BigDecimal> ingredientQuantities = new HashMap<>();
        for (ShoppingListItem item : shoppingList.getItems()) {
            ingredientQuantities.put(item.getIngredient().getId(), item.getQuantity());
        }
        assertEquals(BigDecimal.valueOf(5), ingredientQuantities.get(tomato.getId()));
        assertEquals(BigDecimal.valueOf(1), ingredientQuantities.get(onion.getId()));
    }

}
