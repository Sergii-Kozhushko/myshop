--liquibase formatted sql
--changeset sergii:v0.1.0-c006 author:sergii
CREATE TABLE IF NOT EXISTS myshop.dec_invoice_products
(
    id            bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_id    bigint,
    decinvoice_id bigint,
    quantity      int,
    price         numeric(12, 2),

    CONSTRAINT dec_invoicep_pkey PRIMARY KEY (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id)
        REFERENCES myshop.product (id) MATCH SIMPLE,
    CONSTRAINT fk_dec_invoice FOREIGN KEY (decinvoice_id)
        REFERENCES myshop.dec_invoice (id) MATCH SIMPLE
)
-- rollback myshop.dec_invoice_products
