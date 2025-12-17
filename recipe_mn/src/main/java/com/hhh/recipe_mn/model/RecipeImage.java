package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "recipe_images")
@Getter
@Setter
public class RecipeImage extends AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;


    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;


    @Column(name = "is_primary")
    private Boolean primary = Boolean.FALSE;
}