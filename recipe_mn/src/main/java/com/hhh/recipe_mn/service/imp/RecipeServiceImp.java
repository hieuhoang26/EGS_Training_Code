package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.CreateRecipeRequest;
import com.hhh.recipe_mn.dto.request.ImageRequest;
import com.hhh.recipe_mn.dto.request.IngredientRequest;
import com.hhh.recipe_mn.dto.request.StepRequest;
import com.hhh.recipe_mn.dto.request.UpdateRecipeRequest;
import com.hhh.recipe_mn.model.Cuisine;
import com.hhh.recipe_mn.model.Ingredient;
import com.hhh.recipe_mn.model.Recipe;
import com.hhh.recipe_mn.model.RecipeImage;
import com.hhh.recipe_mn.model.RecipeIngredient;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.CuisineRepository;
import com.hhh.recipe_mn.repository.IngredientRepository;
import com.hhh.recipe_mn.repository.RecipeRepository;
import com.hhh.recipe_mn.repository.UserRepository;
import com.hhh.recipe_mn.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImp implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CuisineRepository cuisineRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public UUID create(UUID userId, CreateRecipeRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        // recipe
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setName(req.getName().trim());
        recipe.setServings(req.getServings());
        recipe.setPrepTimeMin(req.getPrepTimeMin());
        recipe.setCookTimeMin(req.getCookTimeMin());
        recipe.setDescription(req.getDescription());
        recipe.setPublic(req.getIsPublic());

        // cuisine
        if (req.getCuisineId() != null) {
            Cuisine cuisine = cuisineRepository.findById(req.getCuisineId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cuisine not found with id: " + req.getCuisineId()));
            recipe.setCuisine(cuisine);
        }

        // ingredients
        addIngredientsToRecipe(recipe, req.getIngredients());

        // steps
        addStepsToRecipe(recipe, req.getSteps());

        // images
        for (ImageRequest item : req.getImages()) {
            RecipeImage image = RecipeImage.builder()
                    .imageUrl(item.getUrl())
                    .build();
            recipe.addImage(image);
        }
        recipeRepository.save(recipe);
        return recipe.getId();


    }

    private void addIngredientsToRecipe(Recipe recipe, List<IngredientRequest> ingredientRequests) {
        if (ingredientRequests == null || ingredientRequests.isEmpty()) {
            return;
        }

        for (IngredientRequest req : ingredientRequests) {
            Ingredient ingredient = ingredientRepository.findById(req.getIngredientId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Ingredient not found with id: " + req.getIngredientId()));
            RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                    .ingredient(ingredient)
                    .quantity(req.getQuantity())
                    .unit(req.getUnit())
                    .build();
            recipe.addIngredient(recipeIngredient);
        }
    }

    private void addStepsToRecipe(Recipe recipe, List<StepRequest> stepRequests) {
        if (stepRequests == null || stepRequests.isEmpty()) {
            throw new IllegalArgumentException("Recipe must have at least one step");
        }
        stepRequests.sort(Comparator.comparingInt(StepRequest::getStepOrder));
        for (StepRequest req : stepRequests) {
            recipe.addStep(req.getStepOrder(), req.getInstruction());
        }
        recipe.reorderSteps();
    }

    @Override
    @Transactional
    public void update(UUID recipeId, UUID userId, UpdateRecipeRequest req) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Recipe not found with id: " + recipeId));
        if (!recipe.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this recipe");
        }
//        recipe.setVersion(req.getVersion());
        if (req.getName() != null) {
            recipe.setName(req.getName().trim());
        }

        if (req.getDescription() != null) {
            recipe.setDescription(req.getDescription());
        }

        if (req.getServings() != null) {
            recipe.setServings(req.getServings());
        }

        if (req.getPrepTimeMin() != null) {
            recipe.setPrepTimeMin(req.getPrepTimeMin());
        }

        if (req.getCookTimeMin() != null) {
            recipe.setCookTimeMin(req.getCookTimeMin());
        }

        if (req.getIsPublic() != null) {
            recipe.setPublic(req.getIsPublic());
        }
        if (req.getCuisineId() != null) {
            Cuisine cuisine = cuisineRepository.findById(req.getCuisineId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cuisine not found with id: " + req.getCuisineId()));
            recipe.setCuisine(cuisine);
        }

        // ingredients (replace all)
        if (req.getIngredients() != null) {
            updateIngredients(recipe, req.getIngredients());
        }

        // steps (replace all)
        if (req.getSteps() != null) {
            updateSteps(recipe, req.getSteps());
        }
        // images
        if (req.getImages() != null) {
            updateImages(recipe, req.getImages());
        }
        recipeRepository.save(recipe);
    }


    private void updateIngredients(Recipe recipe, List<IngredientRequest> requests) {

        recipe.getIngredients().clear();

        if (requests.isEmpty()) {
            return;
        }

        for (IngredientRequest req : requests) {
            Ingredient ingredient = ingredientRepository.findById(req.getIngredientId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Ingredient not found with id: " + req.getIngredientId()));

            RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                    .ingredient(ingredient)
                    .quantity(req.getQuantity())
                    .unit(req.getUnit())
                    .build();
            recipe.addIngredient(recipeIngredient);
        }
    }

    private void updateSteps(Recipe recipe, List<StepRequest> requests) {

//        if (requests.isEmpty()) {
//            throw new IllegalArgumentException("Recipe must have at least one step");
//        }

        recipe.getSteps().clear();

        requests.sort(Comparator.comparingInt(StepRequest::getStepOrder));

        for (StepRequest req : requests) {
            recipe.addStep(req.getStepOrder(), req.getInstruction());
        }
        recipe.reorderSteps();
    }

    private void updateImages(Recipe recipe, List<ImageRequest> reqImages) {
        // id : img
        Map<UUID, RecipeImage> currentImages = recipe.getImages().stream()
                .collect(Collectors.toMap(RecipeImage::getId, Function.identity()));

        Set<RecipeImage> updatedImages = new HashSet<>();

        for (ImageRequest req : reqImages) {

            // old img
            if (req.getId() != null) {
                RecipeImage image = currentImages.get(req.getId());

                if (image == null) {
                    throw new EntityNotFoundException(
                            "Image not found with id: " + req.getId());
                }

                image.setImageUrl(req.getUrl().trim());
                image.setPrimary(Boolean.TRUE.equals(req.getIsPrimary()));
                updatedImages.add(image);
            }
            // new img
            else {
                RecipeImage newImage = new RecipeImage();
                newImage.setImageUrl(req.getUrl().trim());
                newImage.setPrimary(Boolean.TRUE.equals(req.getIsPrimary()));
                newImage.setRecipe(recipe);
                updatedImages.add(newImage);
            }
        }

        // clear + add lại -> orphanRemoval tự xoá ảnh bị remove
        recipe.getImages().clear();
        recipe.getImages().addAll(updatedImages);
    }


}
