--liquibase formatted sql
--changeset sergii:v0.1.0-i02
INSERT INTO myshop.product
(name,
 price,
 wholesale_price,
 quantity,
 category_id,
 is_active)
VALUES ('Computer', 1200.30, 800, 2, 1, true),
       ('Printer Cannon Pixma-100', 200.0, 100, 50, 1, true),
       ('Printer Samsung SPM-1000', 300.0, 200, 10, 1, true),
       ('Monitor DELL 27', 600.0, 400, 60, 1, true),
       ('Monitor LG 47', 1600.0, 1000, 2, 1, true),
       ('Lamp', 20.00, 10, 10, 2, true),
       ('Chair', 200.10, 100, 100, 2, false),
       ('Iphone 6 SE', 300.50, 260, 8, 3, true),
       ('Sport Ball Adidas original', 20.5, 8.80, 3000, 4, true),
       ('Sport equipment 1', 120.5, 100, 2, 4, true);

-- rollback DELETE FROM myshop.product WHERE first_name IN ('Computer', 'Lamp', 'Chair', 'Mobile phone');
