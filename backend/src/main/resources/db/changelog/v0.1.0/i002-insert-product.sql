--liquibase formatted sql
--changeset sergii:v0.1.0-i02

-- Electronics
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('4K Smart TV', 1000, 900, true, 1, 50),
       ('Wireless Headphones', 150, 120, true, 1, 30),
       ('Smartphone', 600, 500, true, 1, 100),
       ('Laptop', 800, 700, true, 1, 40),
       ('Gaming Console', 450, 400, true, 1, 60),
       ('Bluetooth Speaker', 70, 60, true, 1, 80),
       ('Tablet', 350, 300, true, 1, 70),
       ('Digital Camera', 300, 250, true, 1, 90),
       ('Fitness Tracker', 80, 70, true, 1, 120);

-- Fashion
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Designer Handbag', 300, 270, true, 2, 30),
       ('Mens Casual Shirt', 50, 40, true, 2, 70),
       ('Womens Dress', 80, 70, true, 2, 40),
       ('Sneakers', 100, 90, true, 2, 60),
       ('Wristwatch', 200, 180, true, 2, 50),
       ('Sunglasses', 50, 40, true, 2, 80),
       ('Backpack', 40, 30, true, 2, 90),
       ('Formal Shoes', 120, 100, true, 2, 30),
       ('Earrings', 30, 25, true, 2, 120);

-- Home & Garden
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Garden Furniture Set', 400, 350, true, 3, 20),
       ('Home Decorative Pillow', 20, 15, true, 3, 50),
       ('Indoor Plant', 30, 25, true, 3, 80),
       ('Wall Clock', 25, 20, true, 3, 100),
       ('Kitchen Utensils Set', 40, 30, true, 3, 70),
       ('Bath Towels', 15, 12, true, 3, 120),
       ('Bed Sheets', 50, 45, true, 3, 60),
       ('Curtains', 35, 30, true, 3, 40),
       ('Garden Hose', 20, 15, true, 3, 90);

-- Beauty & Health
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Facial Cleanser', 25, 20, true, 4, 50),
       ('Hair Dryer', 40, 35, true, 4, 30),
       ('Body Lotion', 15, 12, true, 4, 40),
       ('Toothbrush', 10, 8, true, 4, 60),
       ('Shampoo', 20, 18, true, 4, 70),
       ('Sunscreen', 18, 15, true, 4, 80),
       ('Vitamins & Supplements', 30, 25, true, 4, 90),
       ('Massage Oil', 25, 20, true, 4, 100);

-- Sports & Outdoors
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Yoga Mat', 20, 18, true, 5, 50),
       ('Tennis Racket', 60, 55, true, 5, 30),
       ('Hiking Backpack', 45, 40, true, 5, 40),
       ('Water Bottle', 12, 10, true, 5, 60),
       ('Bicycle Helmet', 35, 30, true, 5, 70),
       ('Fishing Rod', 55, 50, true, 5, 80),
       ('Fitness Gloves', 15, 12, true, 5, 90),
       ('Ski Goggles', 40, 35, true, 5, 100);


-- Computers & Accessories
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Gaming Mouse', 25, 20, true, 6, 40),
       ('Laptop Stand', 30, 25, true, 6, 30),
       ('External Hard Drive', 80, 70, true, 6, 20),
       ('USB Flash Drive', 15, 12, true, 6, 50),
       ('Wireless Keyboard', 40, 35, true, 6, 60),
       ('Monitor Stand', 35, 30, true, 6, 70),
       ('Gaming Headset', 50, 45, true, 6, 40),
       ('Laptop Backpack', 55, 50, true, 6, 80),
       ('Graphic Drawing Tablet', 70, 65, true, 6, 20),
       ('Wi-Fi Router', 60, 55, true, 6, 50);

-- Toys & Games
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Board Game', 20, 18, true, 7, 40),
       ('Remote Control Car', 30, 25, true, 7, 30),
       ('Puzzle Set', 15, 12, true, 7, 20),
       ('Stuffed Animal', 10, 8, true, 7, 50),
       ('Building Blocks Set', 25, 20, true, 7, 60),
       ('Action Figure', 18, 15, true, 7, 70),
       ('Doll Playset', 22, 20, true, 7, 40),
       ('Toy Kitchen Set', 40, 35, true, 7, 80),
       ('Educational Toy', 35, 30, true, 7, 20),
       ('Toy Musical Instrument', 28, 25, true, 7, 50);

-- Books & Stationery
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Notebook', 5, 4, true, 8, 50),
       ('Pen Set', 10, 8, true, 8, 30),
       ('Book Set', 40, 35, true, 8, 20),
       ('Bookmark', 2, 1, true, 8, 40),
       ('Highlighter Set', 8, 6, true, 8, 60),
       ('Desk Organizer', 15, 12, true, 8, 70),
       ('Magnetic Whiteboard', 20, 18, true, 8, 40),
       ('Book Lamp', 25, 20, true, 8, 80),
       ('Calligraphy Set', 30, 25, true, 8, 20),
       ('Fountain Pen', 18, 15, true, 8, 50);

-- Additional 10 test products for each category

-- Food & Beverages
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Coffee Beans', 12, 10, true, 9, 50),
       ('Tea Assortment', 8, 6, true, 9, 30),
       ('Chocolate Box', 15, 12, true, 9, 20),
       ('Gourmet Cookies', 10, 8, true, 9, 40),
       ('Canned Soup', 5, 4, true, 9, 60),
       ('Energy Drink', 3, 2, true, 9, 70),
       ('Premium Olive Oil', 20, 18, true, 9, 40),
       ('Dried Fruits Mix', 18, 15, true, 9, 80),
       ('Organic Honey', 22, 20, true, 9, 20),
       ('Assorted Nuts', 14, 12, true, 9, 50);

-- Automotive
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Car Air Freshener', 5, 4, true, 10, 50),
       ('Car Phone Mount', 10, 8, true, 10, 30),
       ('Car Cleaning Kit', 20, 18, true, 10, 20),
       ('Tire Pressure Gauge', 15, 12, true, 10, 40),
       ('Engine Oil', 30, 25, true, 10, 60),
       ('Windshield Wipers', 12, 10, true, 10, 70),
       ('Jump Starter', 40, 35, true, 10, 40),
       ('Car Seat Cover', 25, 20, true, 10, 80),
       ('Steering Wheel Cover', 18, 15, true, 10, 20),
       ('Car Polish', 22, 20, true, 10, 50);

-- Pet Supplies
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Dog Collar', 8, 6, true, 11, 50),
       ('Cat Tree', 30, 25, true, 11, 30),
       ('Pet Food Bowl', 12, 10, true, 11, 20),
       ('Pet Grooming Brush', 10, 8, true, 11, 40),
       ('Cat Litter Box', 20, 18, true, 11, 60),
       ('Fish Tank Decor', 5, 4, true, 11, 70),
       ('Bird Cage', 40, 35, true, 11, 40),
       ('Hamster Wheel', 15, 12, true, 11, 80),
       ('Pet Leash', 18, 15, true, 11, 20),
       ('Small Animal Bed', 22, 20, true, 11, 50);


-- Music & Instruments
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Acoustic Guitar', 150, 130, true, 12, 50),
       ('Digital Piano', 500, 480, true, 12, 30),
       ('Violin', 250, 230, true, 12, 20),
       ('Drum Set', 300, 280, true, 12, 40),
       ('Keyboard Stand', 50, 40, true, 12, 60),
       ('Microphone', 80, 70, true, 12, 70),
       ('Music Stand', 40, 30, true, 12, 40),
       ('Electric Bass Guitar', 200, 180, true, 12, 80),
       ('Mouthpiece for Woodwind Instruments', 30, 25, true, 12, 20),
       ('Tambourine', 20, 18, true, 12, 50);

-- Travel & Luggage
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Travel Backpack', 60, 50, true, 13, 50),
       ('Luggage Set', 150, 130, true, 13, 30),
       ('Travel Neck Pillow', 20, 18, true, 13, 20),
       ('Carry-On Luggage', 80, 70, true, 13, 40),
       ('Travel Organizer Set', 30, 25, true, 13, 60),
       ('Packing Cubes', 25, 20, true, 13, 70),
       ('Travel Adapter', 15, 12, true, 13, 40),
       ('Luggage Tags', 8, 6, true, 13, 80),
       ('Passport Holder', 12, 10, true, 13, 20),
       ('Travel Toiletry Bag', 18, 15, true, 13, 50);

-- Fitness & Exercise
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Dumbbell Set', 100, 90, true, 14, 50),
       ('Yoga Ball', 30, 25, true, 14, 30),
       ('Resistance Bands Set', 25, 20, true, 14, 20),
       ('Exercise Mat', 40, 35, true, 14, 40),
       ('Jump Rope', 12, 10, true, 14, 60),
       ('Kettlebell', 35, 30, true, 14, 70),
       ('Pull-Up Bar', 50, 45, true, 14, 40),
       ('Fitness Hula Hoop', 20, 18, true, 14, 80),
       ('Running Shoes', 60, 55, true, 14, 20),
       ('Fitness Tracker Watch', 70, 65, true, 14, 50);

-- Additional 10 test products for each category

-- Jewelry & Watches
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Diamond Earrings', 200, 180, true, 15, 50),
       ('Gold Bracelet', 180, 160, true, 15, 30),
       ('Silver Necklace', 90, 80, true, 15, 20),
       ('Stainless Steel Watch', 120, 100, true, 15, 40),
       ('Pearl Ring', 150, 130, true, 15, 60),
       ('Leather Strap Watch', 70, 60, true, 15, 70),
       ('Gemstone Pendant', 100, 90, true, 15, 40),
       ('Cubic Zirconia Stud Earrings', 50, 45, true, 15, 80),
       ('Analog Wristwatch', 80, 70, true, 15, 20),
       ('Silver Hoop Earrings', 40, 35, true, 15, 50);

-- Home Appliances
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Air Purifier', 150, 130, true, 16, 50),
       ('Robotic Vacuum Cleaner', 250, 230, true, 16, 30),
       ('Blender', 80, 70, true, 16, 20),
       ('Coffee Maker', 100, 90, true, 16, 40),
       ('Microwave Oven', 120, 100, true, 16, 60),
       ('Electric Kettle', 40, 35, true, 16, 70),
       ('Toaster', 30, 25, true, 16, 40),
       ('Food Processor', 70, 60, true, 16, 80),
       ('Juicer', 60, 55, true, 16, 20),
       ('Handheld Vacuum Cleaner', 50, 45, true, 16, 50);

-- Arts & Crafts
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Watercolor Paint Set', 25, 20, true, 17, 50),
       ('Sketchbook', 15, 12, true, 17, 30),
       ('Acrylic Paint Set', 20, 18, true, 17, 20),
       ('Canvas Panels', 10, 8, true, 17, 40),
       ('Oil Paint Set', 30, 25, true, 17, 60),
       ('Coloring Pencils', 12, 10, true, 17, 70),
       ('Craft Scissors', 8, 6, true, 17, 40),
       ('Craft Glue', 5, 4, true, 17, 80),
       ('Craft Paper Pack', 18, 15, true, 17, 20),
       ('Wooden Craft Kit', 22, 20, true, 17, 50);

-- Additional 10 test products for each category

-- Baby & Kids
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Baby Stroller', 200, 180, true, 18, 50),
       ('Kids Bicycle', 150, 130, true, 18, 30),
       ('Baby Car Seat', 180, 160, true, 18, 20),
       ('Kids Scooter', 80, 70, true, 18, 40),
       ('Baby High Chair', 100, 90, true, 18, 60),
       ('Childrens Books Set', 50, 45, true, 18, 70),
       ('Kids Puzzle', 20, 18, true, 18, 40),
       ('Educational Toy Set', 60, 55, true, 18, 80),
       ('Baby Monitor', 70, 65, true, 18, 20),
       ('Kids Art Easel', 40, 35, true, 18, 50);

-- Office Supplies
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Desk Chair', 100, 90, true, 19, 50),
       ('Writing Desk', 120, 100, true, 19, 30),
       ('File Cabinet', 80, 70, true, 19, 20),
       ('Ballpoint Pen', 5, 4, true, 19, 40),
       ('Notebook Set', 15, 12, true, 19, 60),
       ('Stapler', 10, 8, true, 19, 70),
       ('Paper Clips', 2, 1, true, 19, 40),
       ('Desk Organizer', 18, 15, true, 19, 80),
       ('Dry Erase Board', 25, 20, true, 19, 20),
       ('Document Holder', 12, 10, true, 19, 50);

-- Party & Events
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Party Balloons', 5, 4, true, 20, 50),
       ('Party Hats', 3, 2, true, 20, 30),
       ('Birthday Candles', 2, 1, true, 20, 20),
       ('Party Tableware Set', 12, 10, true, 20, 40),
       ('Party Decorations Kit', 15, 12, true, 20, 60),
       ('Confetti', 1, 1, true, 20, 70),
       ('Party Banner', 8, 6, true, 20, 40),
       ('Party Invitations', 10, 8, true, 20, 80),
       ('Party Favors Pack', 6, 5, true, 20, 20),
       ('Pinata', 18, 15, true, 20, 50);

-- Vampire
INSERT INTO myshop.product (name, price, wholesale_price, is_active, category_id, quantity)
VALUES ('Vampire Hunting Kit', 200, 180, true, 21, 50),
       ('Silver Dagger', 150, 130, true, 21, 30),
       ('Holy Water', 50, 45, true, 21, 20),
       ('Vampire Repellent Spray', 80, 70, true, 21, 40),
       ('Wooden Stake', 30, 25, true, 21, 60),
       ('Cross Necklace', 25, 20, true, 21, 70),
       ('Garlic Bulbs', 10, 8, true, 21, 40),
       ('Vampire Hunting Guidebook', 20, 18, true, 21, 80),
       ('Silver Bullets', 100, 90, true, 21, 20),
       ('Vampire Trap', 120, 100, true, 21, 50);


-- rollback DELETE FROM myshop.product;
