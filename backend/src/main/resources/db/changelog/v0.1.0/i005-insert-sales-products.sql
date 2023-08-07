--liquibase formatted sql
--changeset sergii:v0.1.0-i05
INSERT INTO myshop.sale_item(product_id, sale_id, quantity, price)
VALUES (1, 1, 10, 200.45),
       (2, 1, 1, 100.05),
       (3, 2, 2, 12200.45);

-- rollback DELETE FROM myshop.sale_item WHERE id IN (1,2,3);
