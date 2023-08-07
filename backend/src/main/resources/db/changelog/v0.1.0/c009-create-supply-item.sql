--liquibase formatted sql
--changeset sergii:v0.1.0-c009 author:sergii
CREATE TABLE IF NOT EXISTS myshop.supply_item
(
    id         bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_id bigint,
    supply_id  bigint,
    quantity   int,
    price      numeric(12, 2),

    CONSTRAINT supply_item_p_pkey PRIMARY KEY (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id)
        REFERENCES myshop.product (id) MATCH SIMPLE,
    CONSTRAINT fk_supply FOREIGN KEY (supply_id)
        REFERENCES myshop.supply (id) MATCH SIMPLE
)
-- rollback myshop.supply-item
