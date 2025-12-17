package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CuisineRepository extends JpaRepository<Cuisine, UUID> {
}
