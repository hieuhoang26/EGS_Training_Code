package com.hhh.recipe_mn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhh.recipe_mn.dto.request.CreateRecipeRequest;
import com.hhh.recipe_mn.dto.request.ImageRequest;
import com.hhh.recipe_mn.dto.request.IngredientRequest;
import com.hhh.recipe_mn.dto.request.StepRequest;
import com.hhh.recipe_mn.security.JwtService;
import com.hhh.recipe_mn.service.RecipeService;
import com.hhh.recipe_mn.service.SearchService;
import com.hhh.recipe_mn.mapper.RecipeMapper;
import com.hhh.recipe_mn.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecipeControllerTest {

    final String URI_CREATE = "/api/v1/recipe/{userId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RecipeService recipeService;

    @MockitoBean
    private SearchService searchService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private RecipeMapper recipeMapper;
    private UUID userId;
    private UUID createdRecipeId;
    private CreateRecipeRequest validRequest;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        createdRecipeId = UUID.randomUUID();

        validRequest = new CreateRecipeRequest();
        validRequest.setName("Test Recipe");
        validRequest.setDescription("Test Description");
        validRequest.setServings(4);
        validRequest.setPrepTimeMin(30);
        validRequest.setCookTimeMin(45);
        validRequest.setIsPublic(true);
        validRequest.setCuisineId(UUID.randomUUID());

        IngredientRequest ingredient = new IngredientRequest();
        ingredient.setIngredientId(UUID.randomUUID());
        ingredient.setName("Flour");
        ingredient.setQuantity(BigDecimal.valueOf(200));

        validRequest.setIngredients(List.of(ingredient));

        StepRequest step = new StepRequest();
        step.setStepOrder(1);
        step.setInstruction("Mix all ingredients");

        validRequest.setSteps(List.of(step));

        ImageRequest image = new ImageRequest();
        image.setUrl("http://example.com/image.jpg");
        image.setIsPrimary(true);

        validRequest.setImages(List.of(image));
    }


    // SUCCESS CASE

    @Test
    void createRecipe_WithValidRequest_ShouldReturn201() throws Exception {
        when(recipeService.create(any(UUID.class), any(CreateRecipeRequest.class)))
                .thenReturn(createdRecipeId);

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(createdRecipeId.toString()))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));
    }

    //  VALIDATION CASES
    @Test
    void createRecipe_WithEmptyIngredients_ShouldReturn400() throws Exception {
        validRequest.setIngredients(Collections.emptyList());

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithEmptySteps_ShouldReturn400() throws Exception {
        validRequest.setSteps(Collections.emptyList());

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithInvalidName_ShouldReturn400() throws Exception {
        validRequest.setName("A");

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithNullServings_ShouldReturn400() throws Exception {
        validRequest.setServings(null);

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithExcessiveServings_ShouldReturn400() throws Exception {
        validRequest.setServings(101);

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithNegativeCookTime_ShouldReturn400() throws Exception {
        validRequest.setCookTimeMin(-5);

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithTooManySteps_ShouldReturn400() throws Exception {
        List<StepRequest> steps = new ArrayList<>();
        for (int i = 1; i <= 51; i++) {
            StepRequest step = new StepRequest();
            step.setStepOrder(i);
            step.setInstruction("Step " + i);
            steps.add(step);
        }
        validRequest.setSteps(steps);

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithInvalidStepOrder_ShouldReturn400() throws Exception {
        StepRequest step = new StepRequest();
        step.setStepOrder(0);
        step.setInstruction("Invalid step");

        validRequest.setSteps(List.of(step));

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithInvalidStepInstruction_ShouldReturn400() throws Exception {
        StepRequest step = new StepRequest();
        step.setStepOrder(1);
        step.setInstruction("A");

        validRequest.setSteps(List.of(step));

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRecipe_WithNullBody_ShouldReturn400() throws Exception {
        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    // SERVICE ERROR
    @Test
    void createRecipe_WhenServiceThrowsException_ShouldReturn400() throws Exception {
        when(recipeService.create(any(UUID.class), any(CreateRecipeRequest.class)))
                .thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(post(URI_CREATE, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }
}
