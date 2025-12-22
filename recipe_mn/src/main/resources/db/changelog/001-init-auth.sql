-- =========================
-- TABLE: users
-- =========================
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       date_of_birth DATE,
                       gender VARCHAR(20),
                       phone VARCHAR(20),
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       avatar VARCHAR(255),
                       status VARCHAR(30),
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP,
                       deleted BOOLEAN DEFAULT FALSE,
                       created_by VARCHAR(50),
                       updated_by VARCHAR(50)

);

-- =========================
-- TABLE: roles
-- =========================
CREATE TABLE roles (
                       id UUID PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE,
                       description VARCHAR(255),
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP,
                       deleted BOOLEAN DEFAULT FALSE,
                       created_by VARCHAR(50),
                       updated_by VARCHAR(50)
);

-- =========================
-- TABLE: permissions
-- =========================
CREATE TABLE permissions (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             description VARCHAR(255),
                             created_at TIMESTAMP,
                             updated_at TIMESTAMP,
                             deleted BOOLEAN DEFAULT FALSE,
                             created_by VARCHAR(50),
                             updated_by VARCHAR(50)
);

-- =========================
-- TABLE: users_roles
-- =========================
CREATE TABLE users_roles (
                             user_id UUID NOT NULL,
                             role_id UUID NOT NULL,
                             created_at TIMESTAMP,
                             updated_at TIMESTAMP,
                             deleted BOOLEAN DEFAULT FALSE,
                             created_by VARCHAR(50),
                             updated_by VARCHAR(50),
                             CONSTRAINT uq_users_roles UNIQUE (user_id, role_id)
);

-- =========================
-- TABLE: roles_permissions
-- =========================
CREATE TABLE roles_permissions (
                                   role_id UUID NOT NULL,
                                   permission_id UUID NOT NULL,
                                   created_at TIMESTAMP,
                                   updated_at TIMESTAMP,
                                   deleted BOOLEAN DEFAULT FALSE,
                                   created_by VARCHAR(50),
                                   updated_by VARCHAR(50),
                                   CONSTRAINT uq_roles_permissions UNIQUE (role_id, permission_id)
);
ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_user
        FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_role
        FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_roles_permissions_role
        FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE roles_permissions
    ADD CONSTRAINT fk_roles_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id);
