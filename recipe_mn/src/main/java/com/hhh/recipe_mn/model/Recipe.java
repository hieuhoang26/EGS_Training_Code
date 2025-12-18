package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@Access(AccessType.FIELD)
public class Recipe extends AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

//    @Version
//    @Column(name = "version", nullable = false)
//    private Long version;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "servings")
    private Integer servings;

    @Column(name = "prep_time_min")
    private Integer prepTimeMin;

    @Column(name = "cook_time_min")
    private Integer cookTimeMin;

    @Column(name = "is_public")
    private boolean isPublic;

    // recipe_user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;


    // recipe_step
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stepOrder ASC")
    private List<RecipeStep> steps = new ArrayList<>();

    // recipe_image
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeImage> images = new HashSet<>();

    // recipe_ingredient
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients = new HashSet<>();


    // Utility methods
    public void addIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient == null) {
            throw new NullPointerException("Recipe ingredient cannot be null");
        }
        if (recipeIngredient.getIngredient() == null) {
            throw new NullPointerException("Ingredient cannot be null");
        }
        boolean ingredientExists = ingredients.stream()
                .anyMatch(ri -> ri.isSameIngredient(recipeIngredient));
        if (ingredientExists) {
            throw new IllegalArgumentException(
                    "Ingredient already exists: " + recipeIngredient.getIngredient().getCanonicalName());
        }

        ingredients.add(recipeIngredient);
        recipeIngredient.setRecipe(this);
    }

    public void removeIngredient(RecipeIngredient recipeIngredient) {
        ingredients.remove(recipeIngredient);
        recipeIngredient.setRecipe(null);
    }
    public void addStep(RecipeStep step) {
        if (!step.hasValidInstruction()) {
            throw new IllegalArgumentException("Step instruction cannot be empty");
        }
        // Check duplicate
        boolean orderExists = steps.stream()
                .anyMatch(s -> s.getStepOrder() == step.getStepOrder());
        if (orderExists) {
            throw new IllegalArgumentException("Duplicate step order: " + step.getStepOrder());
        }
        steps.add(step);
        step.setRecipe(this);
    }

    public void addStep(int order, String instruction) {
        if (instruction == null || instruction.trim().isEmpty()) {
            throw new IllegalArgumentException("Instruction cannot be empty");
        }
        RecipeStep step = RecipeStep.builder()
                .stepOrder(order)
                .instruction(instruction.trim())
                .build();
        addStep(step);
    }

    public void removeStep(RecipeStep step) {
        steps.remove(step);
        step.setRecipe(null);
    }

    public void addImage(RecipeImage image) {
        images.add(image);
        image.setRecipe(this);
    }

    public void removeImage(RecipeImage image) {
        images.remove(image);
        image.setRecipe(null);
    }

    public void reorderSteps() {
        steps.sort(Comparator.comparingInt(RecipeStep::getStepOrder));
        for (int i = 0; i < steps.size(); i++) {
            steps.get(i).setStepOrder(i + 1);
        }
    }





}