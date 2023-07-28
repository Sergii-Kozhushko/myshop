--liquibase formatted sql
--changeset sergii:v0.1.0-i04
INSERT INTO dec_invoice(
    code, customer_id, discount, sale_condition, is_active)
VALUES ('45678c', 1, -5.2, 'Wholesale invoice', 1),
('45679', 1, 0, 'Shop1 sale', 1);

-- rollback DELETE FROM dec_invoice code IN ('45678c');
