--liquibase formatted sql
--changeset sergii:v0.1.0-c005 author:sergii
CREATE TABLE IF NOT EXISTS myshop.sale
(
    id             bigint  NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code           varchar(10),
    customer_id    int,
    discount       numeric(4, 2),
    sale_condition varchar(255),
    sum            numeric(10, 2)              DEFAULT 0,
    is_active      boolean NOT NULL            DEFAULT true,
    created_at     timestamp without time zone DEFAULT now(),
    updated_at     timestamp without time zone DEFAULT now(),
    CONSTRAINT sale_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cid FOREIGN KEY (customer_id)
        REFERENCES myshop.customer (id) MATCH SIMPLE
)
-- rollback DROP TABLE myshop.sales;
