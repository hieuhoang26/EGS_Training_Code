-- =========================
-- CUISINES
-- =========================
CREATE TABLE cuisines
(
    id         UUID PRIMARY KEY,
    code       VARCHAR(50)  NOT NULL UNIQUE,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted    BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);

-- =========================
-- RECIPES
-- =========================
CREATE TABLE recipes
(
    id            UUID PRIMARY KEY,
    user_id       UUID         NOT NULL,
    cuisine_id    UUID,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    servings      INT,
    prep_time_min INT,
    cook_time_min INT,
    is_public     BOOLEAN,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    deleted       BOOLEAN DEFAULT FALSE,
    created_by    VARCHAR(50),
    updated_by    VARCHAR(50)
);

ALTER TABLE recipes
    ADD CONSTRAINT fk_recipes_user
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE recipes
    ADD CONSTRAINT fk_recipes_cuisine
        FOREIGN KEY (cuisine_id) REFERENCES cuisines (id);

-- =========================
-- RECIPE STEPS
-- =========================
CREATE TABLE recipe_steps
(
    id          UUID PRIMARY KEY,
    recipe_id   UUID NOT NULL,
    step_order  INT  NOT NULL,
    instruction TEXT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    deleted     BOOLEAN DEFAULT FALSE,
    created_by  VARCHAR(50),
    updated_by  VARCHAR(50),
    CONSTRAINT uq_recipe_steps UNIQUE (recipe_id, step_order)
);

ALTER TABLE recipe_steps
    ADD CONSTRAINT fk_recipes_steps
        FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- =========================
-- RECIPE IMAGES
-- =========================
CREATE TABLE recipe_images
(
    id         UUID PRIMARY KEY,
    recipe_id  UUID NOT NULL,
    image_url  TEXT,
    is_primary BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted    BOOLEAN DEFAULT FALSE,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);

ALTER TABLE recipe_images
    ADD CONSTRAINT fk_recipes_images
        FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- =========================
-- INGREDIENTS
-- =========================
CREATE TABLE ingredients
(
    id             UUID PRIMARY KEY,
    canonical_name VARCHAR(100) NOT NULL UNIQUE,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    deleted        BOOLEAN DEFAULT FALSE,
    created_by     VARCHAR(50),
    updated_by     VARCHAR(50)

);

-- =========================
-- RECIPE INGREDIENTS
-- =========================
CREATE TABLE recipe_ingredients
(
    id            UUID PRIMARY KEY,
    recipe_id     UUID NOT NULL,
    ingredient_id UUID NOT NULL,
    quantity      DECIMAL(10, 2),
    unit          VARCHAR(50),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    deleted       BOOLEAN DEFAULT FALSE,
    created_by    VARCHAR(50),
    updated_by    VARCHAR(50),
    CONSTRAINT uq_recipe_ingredients UNIQUE (recipe_id, ingredient_id, unit)
);

ALTER TABLE recipe_ingredients
    ADD CONSTRAINT fk_recipe_ingredients_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipes (id);

ALTER TABLE recipe_ingredients
    ADD CONSTRAINT fk_recipe_ingredients_ingredient
        FOREIGN KEY (ingredient_id) REFERENCES ingredients (id);

-- =========================
-- PERMISSIONS
-- =========================
INSERT INTO permissions (id, name, description)
VALUES
-- USER
(gen_random_uuid(), 'USER:READ', 'Read user'),
(gen_random_uuid(), 'USER:CREATE', 'Create user'),
(gen_random_uuid(), 'USER:UPDATE', 'Update user'),
(gen_random_uuid(), 'USER:DELETE', 'Delete user'),
(gen_random_uuid(), 'USER:ROLE_UPDATE', 'Update user roles'),

-- ROLE
(gen_random_uuid(), 'ROLE:READ', 'Read role'),
(gen_random_uuid(), 'ROLE:CREATE', 'Create role'),
(gen_random_uuid(), 'ROLE:UPDATE', 'Update role'),
(gen_random_uuid(), 'ROLE:DELETE', 'Delete role'),
(gen_random_uuid(), 'ROLE:PERMISSION_UPDATE', 'Update role permissions'),

-- PERMISSION
(gen_random_uuid(), 'PERMISSION:READ', 'Read permission'),
(gen_random_uuid(), 'PERMISSION:CREATE', 'Create permission'),
(gen_random_uuid(), 'PERMISSION:UPDATE', 'Update permission'),
(gen_random_uuid(), 'PERMISSION:DELETE', 'Delete permission'),

-- RECIPE
(gen_random_uuid(), 'RECIPE:READ', 'Read recipe'),
(gen_random_uuid(), 'RECIPE:CREATE', 'Create recipe'),
(gen_random_uuid(), 'RECIPE:UPDATE', 'Update recipe'),
(gen_random_uuid(), 'RECIPE:DELETE', 'Delete recipe'),

-- SHOPPING
(gen_random_uuid(), 'SHOPPING:READ', 'Read shopping list'),
(gen_random_uuid(), 'SHOPPING:CREATE', 'Generate shopping list');


-- =========================
-- ROLES
-- =========================
INSERT INTO roles (id, name, description)
VALUES ('11111111-1111-1111-1111-111111111111', 'ROLE_USER', 'Normal user'),
       ('22222222-2222-2222-2222-222222222222', 'ROLE_ADMIN', 'Administrator');

-- =========================
-- USERS
-- =========================
INSERT INTO users (id, first_name, last_name, email, password, status)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Admin', 'User', 'admin@recipe.app',
        '$2a$10$FJdiOHI0CnDZI/bnwRsq6OxP001NTDG4Rkv.noanWiUk7baqFf68i', 'ACTIVE'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Normal', 'User', 'user@recipe.app',
        '$2a$10$FJdiOHI0CnDZI/bnwRsq6OxP001NTDG4Rkv.noanWiUk7baqFf68i', 'ACTIVE');

-- =========================
-- USERS_ROLES
-- =========================
INSERT INTO users_roles (user_id, role_id)
VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222'),
       ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111');

-- =========================
-- CUISINES
-- =========================
INSERT INTO cuisines (id, code, name)
VALUES ('22222222-2222-2222-2222-222222222222', 'VN', 'Vietnamese'),
       ('22222222-2222-2222-2222-222222222223', 'IT', 'Italian');

-- =========================
-- INGREDIENTS
-- =========================
INSERT INTO ingredients (id, canonical_name)
VALUES ('33333333-3333-3333-3333-333333333301', 'salt'),
       ('33333333-3333-3333-3333-333333333302', 'pepper'),
       ('33333333-3333-3333-3333-333333333303', 'garlic'),
       ('33333333-3333-3333-3333-333333333304', 'onion'),
       ('33333333-3333-3333-3333-333333333305', 'ginger'),
       ('33333333-3333-3333-3333-333333333306', 'beef'),
       ('33333333-3333-3333-3333-333333333307', 'rice noodles'),
       ('33333333-3333-3333-3333-333333333308', 'fish sauce');

-- =========================
-- RECIPES
-- =========================
INSERT INTO recipes (id, user_id, cuisine_id, name,
                     servings, prep_time_min, cook_time_min, is_public)
VALUES ('44444444-4444-4444-4444-444444444444',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        '22222222-2222-2222-2222-222222222222',
        'Pho Bo',
        2, 20, 120, TRUE);

-- =========================
-- RECIPE INGREDIENTS
-- =========================
INSERT INTO recipe_ingredients (id, recipe_id, ingredient_id, quantity, unit)
VALUES ('55555555-5555-5555-5555-555555555501',
        '44444444-4444-4444-4444-444444444444',
        '33333333-3333-3333-3333-333333333306', 300, 'gram'),

       ('55555555-5555-5555-5555-555555555502',
        '44444444-4444-4444-4444-444444444444',
        '33333333-3333-3333-3333-333333333307', 200, 'gram');

-- =========================
-- RECIPE STEPS
-- =========================
INSERT INTO recipe_steps (id, recipe_id, step_order, instruction)
VALUES ('66666666-6666-6666-6666-666666666601',
        '44444444-4444-4444-4444-444444444444',
        1, 'Prepare ingredients and bones'),

       ('66666666-6666-6666-6666-666666666602',
        '44444444-4444-4444-4444-444444444444',
        2, 'Simmer broth for 2 hours');

-- =========================
-- RECIPE IMAGES
-- =========================
INSERT INTO recipe_images (id, recipe_id, image_url, is_primary)
VALUES ('77777777-7777-7777-7777-777777777701',
        '44444444-4444-4444-4444-444444444444',
        'https://cdn.demo/pho.jpg',
        TRUE);
