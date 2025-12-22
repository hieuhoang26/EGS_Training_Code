-- =========================
-- SHOPPING LISTS
-- =========================
CREATE TABLE shopping_lists (
                                id UUID PRIMARY KEY NOT NULL,
                                user_id UUID NOT NULL,
                                name VARCHAR(255),
                                created_at TIMESTAMP,
                                updated_at TIMESTAMP,
                                deleted BOOLEAN DEFAULT FALSE,
                                created_by VARCHAR(50),
                                updated_by VARCHAR(50)
);

ALTER TABLE shopping_lists
    ADD CONSTRAINT fk_shopping_lists_user
        FOREIGN KEY (user_id) REFERENCES users(id);
-- =========================
-- SHOPPING LIST ITEMS
-- =========================
CREATE TABLE shopping_list_items (
                                     id UUID PRIMARY KEY NOT NULL,
                                     shopping_list_id UUID NOT NULL,
                                     ingredient_id UUID NOT NULL,
                                     quantity DECIMAL(10,2),
                                     unit VARCHAR(50),
                                     is_checked BOOLEAN DEFAULT FALSE,
                                     created_at TIMESTAMP,
                                     updated_at TIMESTAMP,
                                     deleted BOOLEAN DEFAULT FALSE,
                                     created_by VARCHAR(50),
                                     updated_by VARCHAR(50)
);

ALTER TABLE shopping_list_items
    ADD CONSTRAINT fk_shopping_list_items_list
        FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists(id);

ALTER TABLE shopping_list_items
    ADD CONSTRAINT fk_shopping_list_items_ingredient
        FOREIGN KEY (ingredient_id) REFERENCES ingredients(id);

ALTER TABLE shopping_list_items
    ADD CONSTRAINT uc_shopping_list_items_composite
        UNIQUE (shopping_list_id, ingredient_id, unit);
-- =========================
-- SHOPPING LIST RECIPES
-- =========================
CREATE TABLE shopping_list_recipes (
                                       id UUID PRIMARY KEY NOT NULL,
                                       shopping_list_id UUID NOT NULL,
                                       recipe_id UUID NOT NULL,
                                       recipe_name VARCHAR(255),
                                       created_at TIMESTAMP,
                                       updated_at TIMESTAMP,
                                       deleted BOOLEAN DEFAULT FALSE,
                                       created_by VARCHAR(50),
                                       updated_by VARCHAR(50)
);

ALTER TABLE shopping_list_recipes
    ADD CONSTRAINT fk_shopping_list_recipes_list
        FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists(id);

ALTER TABLE shopping_list_recipes
    ADD CONSTRAINT fk_shopping_list_recipes_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipes(id);
