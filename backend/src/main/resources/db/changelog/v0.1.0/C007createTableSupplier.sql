--liquibase formatted sql
--changeset sergii:v0.1.0-c007 author:sergii
CREATE TABLE IF NOT EXISTS supplier
(
    id int NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name varchar(255) NOT NULL,
    address varchar(255),
    phone varchar(255),
    email varchar(255),
    created_at timestamp without time zone DEFAULT now(),
    CONSTRAINT supplier_pkey PRIMARY KEY (id)
    )
-- rollback DROP TABLE customer;
