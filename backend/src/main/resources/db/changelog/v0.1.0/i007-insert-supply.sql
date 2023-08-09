--liquibase formatted sql
--changeset sergii:v0.1.0-i07
INSERT INTO myshop.supply(code, supplier_id, additional_info, sum, is_active, created_at)
VALUES ('supply-001', 1, '1st shipment', 100, true, NOW() - INTERVAL '20 day 2 hours 4 minutes'),
       ('supply-002', 2, '2nd shipment', 104440, true, NOW() - INTERVAL '10 day 1 hours 4 minutes'),
       ('supply-003', 3, '3d shipment', 4566, true, NOW() - INTERVAL '120 day 2 hours 34 minutes'),
       ('supply-004', 6, '4th shipment', 20000, false, NOW() - INTERVAL '2 day 12 hours 4 minutes'),
       ('supply-005', 3, 'Some shipment', 1670, false, NOW() - INTERVAL '12 day 2 hours 4 minutes'),
       ('supply-006', 3, '5th shipment', 4400, false, NOW() - INTERVAL '2 hours 11 minutes'),
       ('supply-007', 7, '5th shipment', 20, false, NOW() - INTERVAL '112 day 1 hours 4 minutes'),
       ('supply-008', 8, '5th shipment', 5200, false, NOW() - INTERVAL '20 day 2 hours 10 minutes'),
       ('supply-009', 10, 'Shipment', 2400, false, NOW() - INTERVAL '1 day 4 hours 34 minutes'),
       ('supply-010', 11, 'Additional info', 2800, false, NOW() - INTERVAL '4 day 2 hours'),
       ('supply-011', 11, 'Find some products', 1300000, false, NOW() - INTERVAL '5 day 2 hours 4 minutes'),
       ('supply-012', 4, '', 58979, false, NOW() - INTERVAL '8 day 2 hours 40 minutes'),
       ('supply-013', 12, '', 400.22, false, NOW() - INTERVAL '12 day 2 hours')
;


-- rollback DELETE FROM myshop.supply WHERE id IN (1-4);
