insert into users(is_subscription, role, id, address, email, first_name, image, last_name, password, phone_number)
values (false, 'ADMIN', 1, 'Asia 7', 'davran@gmail.com', 'Davran', 'img', 'Joldoshabev',
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

insert into categories (id, title)
values (1, 'Smartphone'),
       (2, 'Laptop'),
       (3, 'Tablet'),
       (4, 'Smart watch');

insert into sub_categories (category_id, id, title)
values (1, 1, 'Apple'),
       (2, 2, 'Asus'),
       (3, 3, 'Lenovo'),
       (4, 4, 'Apple Watch');

insert into brands (id, image, name)
values (1, 'img', 'Apple'),
       (2, 'img', 'Carbon Gen'),
       (3, 'img', 'Lenovo'),
       (4, 'img', 'Sony'),
       (5, 'img', 'htc');

insert into products(guarantee, brand_id, created_at, data_of_issue, id, sub_category_id,
                     description, name, pdf,
                     video_link, category_id)
values (3, 1, '2023-07-14T12:59:00+00:00', '2023-08-14T00:00:00+00:00', 1, 1, 'The beast',
        'Iphone 14', 'img', 'link',
        1),
       (4, 2, '2023-07-29T12:59:00+00:00', '2023-08-24T00:00:00+00:00', 2, 2, 'The beast', 'ThinkPad X1 Carbon Gen 11.',
        'img', 'link', 2),
       (5, 3, '2023-07-27T12:59:00+00:00', '2023-08-30T00:00:00+00:00', 3, 3, 'The beast', 'xiaoxin pad 2022', 'img',
        'link', 3),
       (6, 1, '2023-07-26T12:59:00+00:00', '2023-09-30T00:00:00+00:00', 4, 4, 'The beast', 'apple watch series 8',
        'img', 'link', 4),
       (7, 1, '2023-07-27T12:59:00+00:00', '2023-09-30T00:00:00+00:00', 5, 1, 'The beast', 'Iphone 13 pro', 'img',
        'link', 1);

insert into banners(id)
values (1),
       (2),
       (3),
       (4),
       (5);

insert into banner_images (banner_id, images)
values (1, 'img'),
       (2, 'img'),
       (3, 'img'),
       (4, 'img'),
       (5, 'img');

insert into baskets (id, user_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

insert into sub_products (article_number, price, quantity, ram, rom, id, product_id, additional_features, code_color,
                          screen_resolution, rating)
values (322323, 78000, 1, 4, 5, 1, 1, 'asc', 'red', '2532×1170', 2.5),
       (123454, 74000, 1, 4, 5, 2, 2, 'rte', 'red', '1920x1200', 5),
       (987665, 79000, 1, 4, 5, 3, 3, 'bvcb', 'red', '2000x1200 ', 1.3);

insert into baskets_sub_products (baskets_id, sub_products_id)
values (1, 1),
       (2, 2),
       (3, 3);


insert into discounts (id, sale, start_date, finish_date, sub_product_id)
values (1, 10, '2023-07-14T12:59:00+00:00', '2023-08-14T00:00:00+00:00', 1),
       (2, 5, '2023-07-14T12:59:00+00:00', '2023-07-24T00:00:00+00:00', 2),
       (3, 3, '2023-07-14T12:59:00+00:00', '2023-07-30T00:00:00+00:00', 3);

insert into laptops (screen_size, video_memory, id, processor, purpose, sub_product_id)
values (2.0, 8, 1, 'INTEL_CORE_I3', 'FOR_WORK', 2);
--        (3.0, 10, 2, 'INTEL_CORE_I3', 'FOR_WORK', 2),
--        (4.0, 15, 3, 'INTEL_CORE_I3', 'FOR_WORK', 2);

insert into mailings(finish_date, id, start_date, description, image, title)
values ('2023-08-14T00:00:00+00:00', 1, '2023-07-14T12:59:00+00:00', 'The beast', 'img', 'mailings'),
       ('2023-07-24T00:00:00+00:00', 2, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'mel'),
       ('2023-07-30T00:00:00+00:00', 3, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'asd'),
       ('2023-07-30T00:00:00+00:00', 4, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'lkj'),
       ('2023-07-30T00:00:00+00:00', 5, '2023-07-14T12:59:00+00:00', 'The Beast', 'img', 'wer');

insert into orders(order_number, quantity, total_discount, total_price, date_of_order, id, user_id,
                   status,
                   type_delivery, type_payment)
values (1, 1, 12, 23, '2023-07-14T12:59:00+00:00', 1, 1, 'PENDING', 'PICKUP', 'CASH'),
       (2, 1, 13, 23, '2023-07-14T12:59:00+00:00', 2, 2, 'READY_FOR_DELIVERY', 'DELIVERY', 'CASH'),
       (3, 1, 14, 23, '2023-07-14T12:59:00+00:00', 3, 3, 'IN_PROCESSING', 'PICKUP', 'CASH'),
       (4, 1, 15, 23, '2023-07-14T12:59:00+00:00', 4, 4, 'RECEIVED', 'PICKUP', 'CASH'),
       (5, 1, 16, 23, '2023-07-14T12:59:00+00:00', 5, 5, 'DELIVERED', 'PICKUP', 'CASH');

insert into orders_sub_products (orders_id, sub_products_id)
values (1, 1),
       (2, 2),
       (3, 3);

insert into phones(screen_size, sim, id, sub_product_id, battery_capacity, diagonal_screen)
values (1.0, 2, 1, 1, '2400-4799mah', '0-2');
--        (1.0, 2, 2, 1, '2400-4799mah', '0-2'),
--        (1.0, 2, 3, 1, '2400-4799mah', '0-2');


insert into reviews (grade, date_creat_ad, id, sub_product_id, user_id, comment, reply_to_comment)
values (4, '2023-07-14T12:59:00+00:00', 1, 1, 1, 'comm', 'ok'),
       (2, '2023-07-14T12:59:00+00:00', 2, 2, 2, 'comm', 'ok'),
       (1, '2023-07-14T12:59:00+00:00', 3, 3, 3, 'comm', 'ok');

insert into smart_watches (display_discount, waterproof, id, sub_product_id, an_interface, gender, hull_shape,
                           housing_material, material_bracelet)
values (2.0, false, 1, 3, 'BLUETOOTH', 'FEMALE', 'SQUARE', 'ACRYLIC', 'RUBBER');
--        (3.0, true, 2, 3, 'WIFI', 'MALE', 'ROUND', 'CERAMIC', 'IMITATION_LEATHER'),
--        (4.0, false, 3, 3, 'GPS', 'FEMALE', 'OVAL', 'GLASS', 'RUBBER');

insert into sub_product_images (sub_product_id, images)
values (1, 'img'),
       (2, 'img'),
       (3, 'img');
insert into user_comparison (comparison, user_id)
values (1, 1),
       (2, 2),
       (3, 3);

insert into user_favorite (favorite, user_id)
values (1, 1),
       (2, 2),
       (3, 3);





