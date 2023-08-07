--liquibase formatted sql
--changeset sergii:v0.1.0-i07
INSERT INTO myshop.supply(code, supplier_id, additional_info, sum, is_active, created_at)
VALUES ('supply-001', 1, '1st shipment', 100, true, NOW() - INTERVAL '20 day 2 hours 4 minutes'),
       ('supply-002', 2, '2nd shipment', 104440, true, NOW() - INTERVAL '10 day 1 hours 4 minutes'),
       ('supply-003', 1, '3d shipment', 4566, true, NOW() - INTERVAL '120 day 2 hours 34 minutes'),
       ('supply-004', 1, '4th shipment', 800, false, NOW() - INTERVAL '2 day 12 hours 4 minutes');

-- rollback DELETE FROM myshop.supply WHERE id IN (1-4);
