package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShoppingListRepository
        extends JpaRepository<ShoppingList, UUID> {
}
