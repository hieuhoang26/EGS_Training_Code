package com.hhh.recipe_mn.repository;

import com.hhh.recipe_mn.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingListRepository
        extends JpaRepository<ShoppingList, UUID> {
    @Query("""
        select distinct sl
        from ShoppingList sl
        left join fetch sl.items i
        left join fetch i.ingredient
        left join fetch sl.shoppingListRecipes sr
        left join fetch sr.recipe
        where sl.id = :id
    """)
    Optional<ShoppingList> findDetailById(@Param("id") UUID id);
}
