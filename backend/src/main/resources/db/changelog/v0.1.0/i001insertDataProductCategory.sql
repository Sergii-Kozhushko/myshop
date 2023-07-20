--liquibase formatted sql
--changeset sergii:v0.1.0-i01
INSERT INTO product_category
(
    name,
    parent_category_id
)
VALUES
    ('It-technics', 0),
    ('Furniture', 0),
    ('Mobile phones', 0),
    ('Sport', 0);
 -- If parent_category_id = 0 than it is root category
INSERT INTO product_category
(
    name,
    parent_category_id
)
VALUES
    ('Dell', 1),
    ('Apple', 1),
    ('Furniture brand1', 2),
    ('Samsung', 1);



-- rollback DELETE FROM product_category WHERE name IN ('Dell', 'Apple', 'Furniture brand1', 'Samsung');
