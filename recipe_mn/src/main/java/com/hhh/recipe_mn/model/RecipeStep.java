package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "recipe_steps",
        uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "step_order"}))
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStep {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(name = "step_order")
    private int stepOrder;

    @Column(name = "instruction",columnDefinition = "text")
    private String instruction;

    public boolean hasValidInstruction() {
        return instruction != null && !instruction.trim().isEmpty();
    }
}
