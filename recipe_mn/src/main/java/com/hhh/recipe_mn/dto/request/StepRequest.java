package com.hhh.recipe_mn.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepRequest {
    @NotNull(message = "Step order is required")
    @Min(value = 1, message = "Step order must be at least 1")
    private Integer stepOrder;

    @NotBlank(message = "Instruction is required")
    @Size(min = 5, max = 2000, message = "Instruction must be between 5 and 2000 characters")
    private String instruction;
}