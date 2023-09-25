INSERT INTO users(is_subscription, role, id, address, email, first_name, image, last_name, password, phone_number)
VALUES (false, 'ADMIN', 1, 'Asia 7', 'davran@gmail.com', 'Davran', 'img', 'Joldoshbaev',
        '$2a$12$5jp6EvdGIlzGIYRrmUFOx.gLH35BSm7aVUKhQq3mKHDyIcxKRyEAi', '0995665528'), --Admin123
       (true, 'USER', 2, 'Красный речка', 'salymbek@gmail.com', 'Salymbek', 'img', ' Khadzhakeldyev',
        '$2a$12$obECYf/JcKcxdFVc4w57we64ZSiU9.RfZ/uV/l3D/.oa72NerEj7y',
        '0700020206'),                                                                 --salymbek2006
       (false, 'USER', 3, 'Ибраимова 3', 'gulira@gmail.com', 'Gulira', 'img', ' Abdukerim kyzy',
        '$2a$12$KMfRpt80J70ZllO4C4thouR/NV.1QIVrEvY74VqMCS/yTZeMQgQJW',
        '0700020206'),                                                                 --gulira2002
       (true, 'USER', 4, 'Арча бешик ', 'sanjarabdymomunov@gmail.com', 'Sanzhar', 'img',
        '  Abdymomunov',
        '$2a$12$pBVBShiP3FroQBWzcZxedun38JGBzpStTDLaenL1BnGhWYANXzH.G',
        '0700020206'),                                                                 --sanzhar2003
       (false, 'USER', 5, 'Аламидин-1', 'erjan@gmail.com', 'Erjan', 'img', '  Taalaibekov',
        '$2a$12$XhWrZB5uMZcfsw8n5kZ58emB5Z6nkF3Ix94D5QaYB4ikkfyimUO66', '0700020206'); --erjan2004


INSERT INTO categories (id, title)
VALUES (1, 'Phone'),
       (2, 'Laptop'),
       (3, 'Smart Watch'),
       (4, 'Tablet');

INSERT INTO sub_categories (category_id, id, title)
VALUES (1, 1, 'Android'),
       (1, 2, 'iOS'),
       (1, 3, 'Camera Phones'),
       (1, 4, 'Gaming Phones'),
       (2, 5, 'Ultrabooks'),
       (2, 6, 'Business Laptops'),
       (2, 7, 'Touchscreen Laptops'),
       (2, 8, 'Chromebooks'),
       (2, 9, 'Gaming Laptops'),
       (3, 10, 'Fitness Trackers'),
       (3, 11, 'Sports Watches'),
       (3, 12, 'Children Smart Watches'),
       (4, 13, 'Entertainment Tablets'),
       (4, 14, 'Professional Tablets'),
       (4, 15, 'Educational Tablets');



INSERT INTO brands (id, image, name)
VALUES (1, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692870335571_APPLE_Image.png', 'Apple'),
       (2, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692870356819_SAMSUNG_Image.png', 'Samsung'),
       (3, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692870114335_HUAWEI_Image.png', 'Huawei'),
       (4, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692870166420_XIAOMI_Image.png', 'Xiaomi'),
       (5, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692870073712_HONOR_Image.png', 'Honor');

INSERT INTO products(guarantee, brand_id, created_at, data_of_issue, id, sub_category_id, description, name, pdf,
                     video_link, category_id)
VALUES (1, 1, '2023-07-14T12:59:00+00:00', '2023-08-14T00:00:00+00:00', 1, 1, 'The beast', 'Iphone 14', 'img', 'link',
        1),
       (14, 2, '2023-07-29T12:59:00+00:00', '2023-08-24T00:00:00+00:00', 2, 5, 'The beast',
        'ThinkPad X1 Carbon Gen 11.',
        'img', 'link', 2),
       (5, 3, '2023-07-27T12:59:00+00:00', '2023-08-30T00:00:00+00:00', 3, 10, 'The beast', 'Mi Band 7 2022', 'img',
        'link', 3),
       (3, 1, '2023-07-26T12:59:00+00:00', '2023-09-30T00:00:00+00:00', 4, 15, 'The beast', 'iPad',
        'img', 'link', 4),
       (7, 1, '2023-07-27T12:59:00+00:00', '2023-09-30T00:00:00+00:00', 5, 2, 'The beast', 'Iphone 15 pro', 'img',
        'link', 1);



INSERT INTO banners(id)
VALUES (1),
       (2),
       (3),
       (4),
       (5);

INSERT INTO banner_images (banner_id, images)
VALUES (1, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692870908817_wallpaperflare.com_wallpaper.jpg'),
       (2,'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692871052182_wallpaperflare.com_wallpaper%20%281%29.jpg'),
       (3,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692871204414_wallpaperflare.com_wallpaper%20%282%29.jpg'),
       (4,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692871301118_wallpaperflare.com_wallpaper%20%283%29.jpg'),
       (5,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692871435371_wallpaperflare.com_wallpaper%20%284%29.jpg');

INSERT INTO baskets (id, user_id)
VALUES (1, 2),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);


INSERT INTO sub_products (article_number, price, quantity, ram, rom, id, product_id, additional_features, code_color,
                          screen_resolution, rating)
VALUES (322323, 78000, 10, 8, 256, 1, 2, 'asc', '#000000', '1920x1080', 4.3),
       (123454, 74000, 15, 16, 512, 2, 2, 'rte', '#034C73', '2560x1440', 4.8),
       (543210, 85000, 16, 12, 256, 3, 2, 'mnbv', '#035FE4', '1920x1080', 3.9),
       (987654, 92000, 21, 8, 512, 4, 2, 'ytrew', '#04C73', '3840x2160', 4.6),
       (678901, 67000, 31, 16, 512, 5, 2, 'lkjh', '#0A29C0', '2560x1600', 4.2),

       (987665, 79000, 17, 0, 0, 6, 3, 'bvcb', '#FF0000', NULL, 3.1),
       (654321, 120000, 41, 0, 0, 7, 3, 'vcvb', '#FF5500', NULL, 4.5),
       (123987, 95000, 24, 0, 0, 8, 3, 'qazx', '#FFA77F', NULL, 3.8),
       (789456, 110000, 29, 0, 0, 9, 3, 'wsxc', '#CD8966', NULL, 4.2),
       (456123, 83000, 19, 0, 0, 10, 3, 'edcv', '#CDAA66', NULL, 4.9),

       (234567, 60000, 34, 6, 128, 11, 1, 'bcvn', '#FFEBBE', NULL, 4.2),
       (789012, 80000, 14, 8, 256, 12, 1, 'tyio', '#FFD37F', NULL, 3.9),
       (345678, 68000, 32, 6, 256, 13, 1, 'qwerty', '#FFAA00', NULL, 4.7),
       (901234, 72000, 42, 8, 128, 14, 4, 'zxcvb', '#FFFF72', NULL, 4.1),
       (567890, 74000, 70, 8, 256, 15, 4, 'asdfg', '#E6E600', NULL, 3.6);

INSERT INTO baskets_sub_products (baskets_id, sub_products_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (4, 5),
    (5, 1),
    (5, 2),
    (5, 3);


INSERT INTO discounts (id, sale, start_date, finish_date, sub_product_id)
VALUES (1, 10, '2023-07-14T12:59:00+00:00', '2023-08-14T00:00:00+00:00', 1),
       (2, 5, '2023-07-14T12:59:00+00:00', '2023-07-24T00:00:00+00:00', 2),
       (3, 3, '2023-07-14T12:59:00+00:00', '2023-07-30T00:00:00+00:00', 3);



INSERT INTO mailings(finish_date, id, start_date, description, image, title)
VALUES ('2023-08-14T00:00:00+00:00', 1, '2023-07-14T12:59:00+00:00', 'The beast', 'img', 'mailings'),
       ('2023-07-24T00:00:00+00:00', 2, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'mel'),
       ('2023-07-30T00:00:00+00:00', 3, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'asd'),
       ('2023-07-30T00:00:00+00:00', 4, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'lkj'),
       ('2023-07-30T00:00:00+00:00', 5, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'wer');

INSERT INTO orders(order_number, quantity, total_discount, total_price, date_of_order, id, user_id,
                   status,
                   type_delivery, type_payment)
VALUES (1, 1, 12, 23, '2023-07-14T12:59:00+00:00', 1, 1, 'PENDING', 'PICKUP', 'CASH'),
       (2, 1, 13, 23, '2023-07-14T12:59:00+00:00', 2, 2, 'READY_FOR_DELIVERY', 'DELIVERY', 'CASH'),
       (3, 1, 14, 23, '2023-07-14T12:59:00+00:00', 3, 3, 'IN_PROCESSING', 'PICKUP', 'CASH'),
       (4, 1, 15, 23, '2023-07-14T12:59:00+00:00', 4, 4, 'RECEIVED', 'PICKUP', 'CASH'),
       (5, 1, 16, 23, '2023-07-14T12:59:00+00:00', 5, 5, 'DELIVERED', 'PICKUP', 'CASH');

INSERT INTO orders_sub_products (orders_id, sub_products_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (2, 4),
       (2, 3),
       (2, 7),
       (2, 11),
       (2, 15);



INSERT INTO laptops (screen_size, video_memory, id, processor, purpose, sub_product_id)
VALUES (2.0, 8, 1, 'INTEL_CORE_I3', 'FOR_WORK', 1),
       (3.4, 12, 2, 'INTEL_CORE_I7', 'GAMING', 2),
       (4.6, 2, 3, 'INTEL_PENTIUM', 'OFFICE', 3),
       (4, 4, 4, 'AMD_RYZEN_3_3250U', 'FOR_LEARNING', 4),
       (5.8, 6, 5, 'INTEL_CELERON', 'MULTIMEDIA', 5);

INSERT INTO phones(screen_size, sim, id, sub_product_id, battery_capacity, diagonal_screen)
VALUES (14, 2, 1, 11, '2400 mAh', '0-2'),
       (0, 1, 2, 12, '3000 mAh', NULL),
       (17.3, 1, 3, 13, '5000 mAh', '9-11'),
       (0, 1, 4, 14, '4200 mAh', NULL),
       (11.5, 1, 5, 15, '5500 mAh', ' 12-15');


INSERT INTO smart_watches (display_discount, waterproof, id, sub_product_id, an_interface, gender, hull_shape,
                           housing_material, material_bracelet)
VALUES (2.0, FALSE, 1, 6, 'BLUETOOTH', 'FEMALE', 'SQUARE', 'ACRYLIC', 'RUBBER'),
       (1.22, TRUE, 2, 7, 'WIFI', 'MALE', 'ROUND', 'ALUMINIUM', 'RUBBER'),
       (1.4, TRUE, 3, 8, 'WIFI', 'MALE', 'OVAL', 'CERAMIC', 'NYLON'),
       (1.54, FALSE, 4, 9, 'GPS', 'UNI', 'ROUND', 'GLASS', 'IMITATION_LEATHER'),
       (1.37, TRUE, 5, 10, 'NFC', 'UNI', 'RECTANGULAR', 'STAINLESS_STEEL', 'CERAMIC_IMITATION');


INSERT INTO reviews (grade, date_creat_ad, id, sub_product_id, user_id, comment, reply_to_comment, is_viewed)
VALUES (4, '2023-07-14T12:59:00+00:00', 1, 1, 1, 'comm', NULL, false),
       (2, '2023-07-14T12:59:00+00:00', 2, 2, 2, 'comm', 'ok', true),
       (1, '2023-07-14T12:59:00+00:00', 3, 3, 3, 'comm', 'ok', true);



INSERT INTO sub_product_images (sub_product_id, images)
VALUES (1,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868741930_laptop_with_black_screen_empty_table_by_window_copy_space_empty.jpg'),
       (1,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868787266_Acer%20Nitro%205%20AN515-58-93JE%20ctens%20%284%29-450x600.jpg'),
       (2, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868803452_147015.jpg'),
       (2, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868817148_128092.jpg'),
       (3, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868803452_147015.jpg'),
       (3,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868787266_Acer%20Nitro%205%20AN515-58-93JE%20ctens%20%284%29-450x600.jpg'),
       (4,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868741930_laptop_with_black_screen_empty_table_by_window_copy_space_empty.jpg'),
       (4, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868817148_128092.jpg'),
       (5, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868817148_128092.jpg'),
       (5,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868741930_laptop_with_black_screen_empty_table_by_window_copy_space_empty.jpg'),
       (6, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869439186_333111799_w600_h600_333111799.webp'),
       (6, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869619409_R3251545001-450x600.jpg'),
       (7, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869439186_333111799_w600_h600_333111799.webp'),
       (7, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869619409_R3251545001-450x600.jpg'),
       (8, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869619409_R3251545001-450x600.jpg'),
       (8, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869439186_333111799_w600_h600_333111799.webp'),
       (9, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869619409_R3251545001-450x600.jpg'),
       (9, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869439186_333111799_w600_h600_333111799.webp'),
       (10, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692869439186_333111799_w600_h600_333111799.webp'),
       (11,
        'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868771301_Apple_rasskajet_udalos_li_ei_povysit_spros_na_iPhone_uje_30_aprelya.jpg'),
       (11, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868932885_3-1-6.jpg'),
       (12, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868932885_3-1-6.jpg'),
       (12, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868932885_3-1-6.jpg'),
       (13, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868932885_3-1-6.jpg'),
       (15, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868817148_128092.jpg'),
       (14, 'https://gadgetariumb9.s3.eu-central-1.amazonaws.com/1692868803452_147015.jpg');


INSERT INTO user_comparison (comparison, user_id)
VALUES (4, 1),
       (4, 2),
       (4, 3);

INSERT INTO user_favorite (favorite, user_id)
VALUES (4, 1),
       (4, 2),
       (4, 3);





