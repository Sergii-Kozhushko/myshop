--liquibase formatted sql
--changeset sergii:v0.1.0-c003 author:sergii
CREATE TABLE IF NOT EXISTS myshop.product
(
    id              bigint         NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name            varchar(255)   NOT NULL,
    price           numeric(12, 2) NOT NULL     DEFAULT 0,
    wholesale_price numeric(12, 2),
    is_active       boolean        NOT NULL     DEFAULT true,
    category_id     int,
    quantity        int                         DEFAULT 0,
    created_at      timestamp without time zone DEFAULT now(),
    updated_at      timestamp without time zone DEFAULT now(),
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT fk_category FOREIGN KEY (category_id)
        REFERENCES myshop.product_category (id) MATCH SIMPLE
)
-- rollback DROP TABLE myshop.product;
