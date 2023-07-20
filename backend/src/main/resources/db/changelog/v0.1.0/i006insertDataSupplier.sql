--liquibase formatted sql
--changeset sergii:v0.1.0-i06
INSERT INTO supplier(
    name, address, phone, email)
VALUES ('Elephant wholesale GmbH', '53113, Schmidegasse, 5, Koblenz, Deutschland','151678956', 'ele-phant@email.de');

-- rollback DELETE FROM supplier WHERE id IN (1);
