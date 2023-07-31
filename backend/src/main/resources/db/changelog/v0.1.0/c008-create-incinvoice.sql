--liquibase formatted sql
--changeset sergii:v0.1.0-c008 author:sergii
CREATE TABLE IF NOT EXISTS myshop.inc_invoice
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    code varchar(10) NOT NULL,
    supplier_id int NOT NULL,
    additional_info text,
    is_active smallint NOT NULL DEFAULT 1,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT inc_invoice_pkey PRIMARY KEY (id),
    CONSTRAINT fk_sid FOREIGN KEY (supplier_id)
        REFERENCES myshop.supplier (id) MATCH SIMPLE
        ON DELETE SET NULL
        NOT VALID
    )
-- rollback DROP TABLE myshop.inc_invoice;
