--liquibase formatted sql
--changeset sergii:v0.1.0-c002 author:sergii
CREATE TABLE IF NOT EXISTS myshop.product_category
(
    id                 int          NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name               varchar(255) NOT NULL,
    parent_category_id int,
    created_at         timestamp without time zone DEFAULT now(),
    updated_at         timestamp without time zone DEFAULT now(),
    CONSTRAINT productс_pkey PRIMARY KEY (id)
)
-- rollback DROP TABLE product;
