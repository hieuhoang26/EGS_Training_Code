-- =========================
-- UNIT CONVERSION
-- =========================
CREATE TABLE unit_conversion (
                                 id UUID PRIMARY KEY NOT NULL,
                                 from_unit VARCHAR(50) NOT NULL,
                                 to_unit VARCHAR(50) NOT NULL,
                                 multiplier DECIMAL(10,4) NOT NULL
);

ALTER TABLE unit_conversion
    ADD CONSTRAINT uk_unit_conversion_from_to
        UNIQUE (from_unit, to_unit);
-- =========================
-- UNIT CONVERSION DATA
-- =========================
INSERT INTO unit_conversion (id, from_unit, to_unit, multiplier)
VALUES
    (gen_random_uuid(), 'kg', 'g', 1000.0000),
    (gen_random_uuid(), 'g',  'g', 1.0000),
    (gen_random_uuid(), 'l',  'ml', 1000.0000),
    (gen_random_uuid(), 'ml', 'ml', 1.0000);

