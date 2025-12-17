package com.hhh.recipe_mn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchRequest {
    private List<String> ingredients;
    private String cuisine;
    private String sortBy = "name";
    private Sort.Direction direction = Sort.Direction.ASC;
    private int page = 0;
    private int size = 10;

    public boolean hasIngredientCriteria() {
        return ingredients != null && !ingredients.isEmpty();
    }

    public boolean hasCuisineCriteria() {
        return cuisine != null && !cuisine.trim().isEmpty();
    }
}
