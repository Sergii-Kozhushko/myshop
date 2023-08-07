--liquibase formatted sql
--changeset sergii:v0.1.0-c006 author:sergii
CREATE TABLE IF NOT EXISTS myshop.sale_item
(
    id         bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_id bigint,
    sale_id    bigint,
    quantity   int,
    price      numeric(12, 2),

    CONSTRAINT sale_item_pkey PRIMARY KEY (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id)
        REFERENCES myshop.product (id) MATCH SIMPLE,
    CONSTRAINT fk_sale FOREIGN KEY (sale_id)
        REFERENCES myshop.sale (id) MATCH SIMPLE
)
-- rollback myshop.dec_invoice_products
