--liquibase formatted sql
--changeset sergii:v0.2.0-u001 author:sergii
update myshop.product_category
set is_active= false
where id = 4;
-- rollback alter table myshop.product_category
--     drop column is_active;;
