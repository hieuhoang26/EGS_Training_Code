package com.hhh.recipe_mn.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class ImageResponse {
    private UUID id;
    private String imageUrl;
    private Boolean isPrimary;
}