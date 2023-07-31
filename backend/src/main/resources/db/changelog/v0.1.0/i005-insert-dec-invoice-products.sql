--liquibase formatted sql
--changeset sergii:v0.1.0-i05
INSERT INTO myshop.dec_invoice_products(product_id, decinvoice_id, quantity, price, discount)
VALUES (1, 1, 10, 200.45, -10.2),
       (2, 1, 1, 100.05, 5),
       (3, 2, 2, 12200.45, 0);

-- rollback DELETE FROM myshop.dec_invoice_products WHERE id IN (1,2,3);
