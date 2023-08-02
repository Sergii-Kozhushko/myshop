--liquibase formatted sql
--changeset sergii:v0.1.0-i03
INSERT INTO myshop.customer(name, address, phone, email, discount, discount_card, date_of_birth, accept_sms_list)
VALUES ('Steve Jobs', '53426, Robert Koch Strasse, 5, Hamburg,', '+491516157363', 'sj@gmail.com', 5, '12365g',
        date '2001-02-16', true),
       ('Bill Gates', '44112, Kollner str, 50, Bonn', '+495674533', 'nilly@gmail.com', 3, '000001', date '1979-02-28',
        true),
       ('Jack Russel', 'Gragenstr, 13, 53424, Remagen', '02642 904600', 'praxis.klumpp@t-online.de', 10, '000002',
        date '1990-01-08', false),
       ('Steve Wozniack', 'Bohum Gasse, 13, 53424, Remagen', '02563 904600', 'mr.w@gmail.de', 8, '0000034',
        date '1980-01-08', false)
;


-- rollback DELETE FROM myshop.customer WHERE name ('Steve Jobs');
