--liquibase formatted sql
--changeset sergii:v0.1.0-i07
INSERT INTO inc_invoice(
    code, supplier_id, additional_info, is_active)
VALUES ('13349', 1, '1ths shipment', 1);

-- rollback DELETE FROM inc_invoice WHERE code IN ('13349');
