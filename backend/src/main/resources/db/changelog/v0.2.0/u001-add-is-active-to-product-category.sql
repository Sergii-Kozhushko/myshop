--liquibase formatted sql
--changeset sergii:v0.2.0-u001 author:sergii
alter table myshop.product_category
    add is_active boolean default true not null;
-- rollback alter table myshop.product_category
--     drop column is_active;;
