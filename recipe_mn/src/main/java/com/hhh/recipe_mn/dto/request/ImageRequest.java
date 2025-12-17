package com.hhh.recipe_mn.dto.request;

import lombok.Data;

@Data
public class ImageRequest {
    private String url;
    private Boolean isPrimary;
}