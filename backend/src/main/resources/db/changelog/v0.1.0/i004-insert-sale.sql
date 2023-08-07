--liquibase formatted sql
--changeset sergii:v0.1.0-i04
INSERT INTO myshop.sale(code, customer_id, discount, sale_condition, sum, is_active, created_at)
VALUES ('45678c', 1, 5.2, 'Wholesale invoice', 100.20, true, NOW() - INTERVAL '1 day'),
       ('45679', 1, 0, 'Shop1 sale', 10000, true, NOW() - INTERVAL '2 day'),
       ('45680', 3, 10, 'Internet sale', 3.34, false, NOW() - INTERVAL '100 day'),
       ('45681', 2, 0, '', 233688, true, NOW() - INTERVAL '10 day'),
       ('INV001', 1, 5.2, 'Wholesale invoice', 100.20, true, NOW() - INTERVAL '1 day 2 hours 30 minutes'),
       ('INV002', 1, 0, 'Shop1 sale', 10000, true, NOW() - INTERVAL '2 day'),
       ('INV003', 3, 10, 'Internet sale', 3.34, true, NOW() - INTERVAL '100 day 10 hours'),
       ('INV004', 2, 4, '', 233688, true, NOW() - INTERVAL '10 day'),
       ('INV005', 3, 5, 'Retail sale', 500.00, true, NOW() - INTERVAL '50 day 10 hours 30 minutes'),
       ('INV006', 2, 2, 'Online sale', 45.60, true, NOW() - INTERVAL '20 day 2 hours 4 minutes'),
       ('INV007', 1, 20, 'Discounted sale', 789.99, true, NOW() - INTERVAL '30 day'),
       ('INV008', 3, 15, 'Bulk sale', 2000.00, true, NOW() - INTERVAL '40 day 45 hours'),
       ('INV009', 2, 0, 'End-of-season sale', 999.50, true, NOW() - INTERVAL '60 day 1 hours 4 minutes'),
       ('INV010', 1, 8, 'Clearance sale', 123.45, true, NOW() - INTERVAL '70 day 2 hours');


-- rollback DELETE FROM myshop.sale WHERE id IN (1-14);
