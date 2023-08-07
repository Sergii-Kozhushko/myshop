--liquibase formatted sql
--changeset sergii:v0.1.0-i008
INSERT INTO myshop.supply_item(product_id, supply_id, quantity, price)
VALUES (1, 1, 10, 200.45),
       (2, 1, 1, 100.05),
       (3, 2, 2, 12200.45),
       (4, 1, 20, 200.45),
       (3, 1, 20, 1200.45)
;

-- rollback DELETE FROM myshop.supply-item WHERE id IN (1,2,3,4,5);
