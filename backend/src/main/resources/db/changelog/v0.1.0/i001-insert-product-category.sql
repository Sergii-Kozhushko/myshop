--liquibase formatted sql
--changeset sergii:v0.1.0-i01
INSERT INTO myshop.product_category
(id, name,
 parent_category_id)
VALUES (1, 'It-technics', 0),
       (2, 'Furniture', 0),
       (3, 'Mobile phones', 0),
       (4, 'Sport', 0);
-- If parent_category_id = 0 than it is root category
INSERT INTO myshop.product_category
(id, name,
 parent_category_id)
VALUES (101, 'Dell', 1),
       (102, 'Apple', 1),
       (201, 'Furniture brand1', 2),
       (103, 'Samsung', 1);


-- rollback DELETE FROM myshop.product_category WHERE name IN ('Dell', 'Apple', 'Furniture brand1', 'Samsung');
