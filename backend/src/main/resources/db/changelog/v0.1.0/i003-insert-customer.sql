--liquibase formatted sql
--changeset sergii:v0.1.0-i03
INSERT INTO customer(
    name, address, phone, email, discount, discount_card, date_of_birth, accept_sms_list)
VALUES
    ('Steve Jobs', '53426, Robert Koch Strasse, 5, Hamburg,', '+491516157363', 'sj@gmail.com', 5, '12365g', date '2001-02-16', 1),
    ('Bill Gates', '44112, Kollner str, 50, Bonn', '+495674533', 'nilly@gmail.com', 3, '000001', date '1979-02-28', 1),
    ('Jack Russel', 'Gragenstr, 13, 53424, Remagen', '02642 904600', 'praxis.klumpp@t-online.de', 10, '000002', date '1990-01-08', 0)
    ;


-- rollback DELETE FROM customer WHERE name ('Steve Jobs');
