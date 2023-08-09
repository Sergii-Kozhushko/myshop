--liquibase formatted sql
--changeset sergii:v0.1.0-c004 author:sergii
CREATE TABLE IF NOT EXISTS myshop.customer
(
    id              int          NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name            varchar(255) NOT NULL,
    address         varchar(255),
    phone           varchar(255),
    email           varchar(255),
    discount        int,
    discount_card   varchar(20)                 DEFAULT '',
    created_at      timestamp without time zone DEFAULT now(),
    updated_at      timestamp without time zone DEFAULT now(),
    date_of_birth   timestamp without time zone,
    accept_sms_list boolean      NOT NULL       DEFAULT false,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
)
-- rollback DROP TABLE myshop.customer;
