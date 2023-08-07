--liquibase formatted sql
--changeset sergii:v0.1.0-i03
INSERT INTO myshop.customer(name, address, phone, email, discount, discount_card, date_of_birth, accept_sms_list)
VALUES ('Default Client', '', '', '', 0, '',
        null, true),
       ('Steve Jobs', '53426, Robert Koch Strasse, 5, Hamburg', '+491516157363', 'sj@gmail.com', 5, '12365g',
        date '2001-02-16', true),
       ('Bill Gates', '44112, Kollner str, 50, Bonn', '+495674533', 'nilly@gmail.com', 3, '000001', date '1979-02-28',
        true),
       ('Jack Russel', 'Gragenstr, 13, 53424, Remagen', '02642 904600', 'praxis.klumpp@t-online.de', 10, '000002',
        date '1990-01-08', false),
       ('Steve Wozniack', 'Bohum Gasse, 13, 53424, Remagen', '02563 904600', 'mr.w@gmail.de', 8, '0000034',
        date '1980-01-08', false),
       ('Elon Musk', '1234 Rocket Ave, Hawthorne, CA', '+1 123-456-7890', 'elon@musk.com', 15, '1234ABC',
        date '1971-06-28', true),
       ('Mark Zuckerberg', '1 Hacker Way, Menlo Park, CA', '+1 987-654-3210', 'mark@facebook.com', 7, 'FB5678',
        date '1984-05-14', true),
       ('Jeff Bezos', '321 Amazon St, Seattle, WA', '+1 555-123-4567', 'jeff@amazon.com', 10, 'AZ123',
        date '1964-01-12', false),
       ('Oprah Winfrey', '567 Media Blvd, Chicago, IL', '+1 111-222-3333', 'oprah@harpo.com', 12, 'OWN2023',
        date '1954-01-29', true),
       ('Warren Buffett', '999 Berkshire Blvd, Omaha, NE', '+1 888-999-0000', 'warren@berkshire.com', 5, 'BRK456',
        date '1930-08-30', false),
       ('Serena Williams', '777 Tennis Ave, Miami, FL', '+1 333-444-5555', 'serena@tennis.com', 8, 'TEN123',
        date '1981-09-26', true),
       ('Michael Jordan', '23 Slam Dunk St, Chicago, IL', '+1 777-888-9999', 'mj@airjordan.com', 10, 'AIR23',
        date '1963-02-17', true),
       ('Kylie Jenner', '123 Lip Kit Lane, Los Angeles, CA', '+1 444-555-6666', 'kylie@kyliecosmetics.com', 20, 'KK123',
        date '1997-08-10', false),
       ('Richard Branson', 'Virgin Island, British Virgin Islands', '+1 000-111-2222', 'richard@virgin.com', 13,
        'VIR001', date '1950-07-18', true),
       ('Elvis Presley', 'Graceland, Memphis, TN', '+1 777-777-7777', 'elvis@rocknroll.com', 0, 'EP001',
        date '1935-01-08', false)
;


-- rollback DELETE FROM myshop.customer WHERE name ('Steve Jobs');
