--liquibase formatted sql
--changeset sergii:v0.1.0-c005 author:sergii
CREATE TABLE IF NOT EXISTS dec_invoice
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code varchar(10) NOT NULL,
    customer_id int,
    discount numeric(4,2),
    sale_condition varchar(255),
    is_active smallint NOT NULL DEFAULT 1,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT dec_invoice_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cid FOREIGN KEY (customer_id)
        REFERENCES customer (id) MATCH SIMPLE
    )
-- rollback DROP TABLE dec_invoice;
