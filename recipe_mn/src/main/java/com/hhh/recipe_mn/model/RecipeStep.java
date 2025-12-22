package com.hhh.recipe_mn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
