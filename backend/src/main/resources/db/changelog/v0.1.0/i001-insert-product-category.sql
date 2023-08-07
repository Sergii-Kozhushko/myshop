--liquibase formatted sql
--changeset sergii:v0.1.0-i01
INSERT INTO myshop.product_category (name, parent_category_id)
VALUES ('Electronics', NULL),
       ('Fashion', NULL),
       ('Home & Garden', NULL),
       ('Beauty & Health', NULL),
       ('Sports & Outdoors', NULL),
       ('Computers & Accessories', 1),
       ('Toys & Games', 1),
       ('Books & Stationery', 2),
       ('Food & Beverages', 2),
       ('Automotive', 3),
       ('Pet Supplies', 3),
       ('Music & Instruments', 4),
       ('Travel & Luggage', 4),
       ('Fitness & Exercise', 5),
       ('Jewelry & Watches', 5),
       ('Home Appliances', 6),
       ('Arts & Crafts', 6),
       ('Baby & Kids', 7),
       ('Office Supplies', 7),
       ('Party & Events', 8),
       ('Vampire Realm', 1)
;


-- rollback DELETE FROM myshop.product_category WHERE name IN ('Dell', 'Apple', 'Furniture brand1', 'Samsung');
