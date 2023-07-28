--liquibase formatted sql
--changeset sergii:v0.2.0-u001 author:sergii
alter table product_category
    add is_active boolean default true not null;
-- rollback alter table product_category
--     drop column is_active;;
