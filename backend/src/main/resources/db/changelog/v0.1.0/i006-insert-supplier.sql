--liquibase formatted sql
--changeset sergii:v0.1.0-i06
INSERT INTO myshop.supplier(name, address, phone, email)
VALUES ('Default Supplier', '', '', ''),
       ('Elephant Wholesale GmbH', '53113, Schmidegasse, 5, Koblenz, Deutschland', '0151678956', 'ele-phant@email.de'),
       ('QualityGoods GmbH', '50667, Domkloster 4, Köln, Deutschland', '+491701234567', 'qualitygoods@gmail.com'),
       ('GlobalTraders AG', '60549, Flughafenstraße 10, Frankfurt, Deutschland', '+491702345678',
        'globaltraders@yahoo.com'),
       ('SupplyLink Ltd.', '10117, Brandenburger Tor 1, Berlin, Deutschland', '+491703456789',
        'supplylink@hotmail.com'),
       ('EasySupplies', '20095, Speersort 1, Hamburg, Deutschland', '+491704567890', 'easysupplies@web.de'),
       ('Max Imports GmbH', '80539, Odeonsplatz 1, München, Deutschland', '+491705678901', 'maximports@outlook.com'),
       ('EuroExports', '60313, Hauptwache 5, Frankfurt, Deutschland', '+491706789012', 'euroexports@example.com'),
       ('VistaImports', '70173, Königstraße 10, Stuttgart, Deutschland', '+491707890123', 'vistaimports@example.net'),
       ('GlobeTraders GmbH', '01067, Zwinger 1, Dresden, Deutschland', '+491708901234', 'globetraders@company.de'),
       ('TradeSolutions', '04109, Augustusplatz 1, Leipzig, Deutschland', '+491709012345',
        'tradesolutions@supplier.eu'),
       ('SunriseImports', '30159, Marktkirche 2, Hannover, Deutschland', '+491701234567',
        'sunriseimports@exporter.org');

-- rollback DELETE FROM myshop.supplier WHERE id IN (1);
