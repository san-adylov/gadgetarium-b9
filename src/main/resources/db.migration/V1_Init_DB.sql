create schema if not exists migration;

drop table if exists banner_images cascade;

drop table if exists banners cascade;

drop table if exists baskets_sub_products cascade;

drop table if exists baskets cascade;

drop table if exists discounts cascade;

drop table if exists laptops cascade;

drop table if exists mailings cascade;

drop table if exists orders_sub_products cascade;

drop table if exists orders cascade;

drop table if exists phones cascade;

drop table if exists reviews cascade;

drop table if exists smart_watches cascade;

drop table if exists sub_product_images cascade;

drop table if exists sub_products cascade;

drop table if exists products cascade;

drop table if exists brands cascade;

drop table if exists sub_categories cascade;

drop table if exists categories cascade;

drop table if exists user_comparison cascade;

drop table if exists user_favorite cascade;

drop table if exists user_recently_viewed_products cascade;

drop table if exists users cascade;


create sequence if not exists banner_seq start with 6 increment by 1;
create sequence if not exists basket_seq start with 6 increment by 1;
create sequence if not exists brand_seq start with 6 increment by 1;
create sequence if not exists category_seq start with 5 increment by 1;
create sequence if not exists discount_seq start with 4 increment by 1;
create sequence if not exists laptop_seq start with 2 increment by 1;
create sequence if not exists mailing_seq start with 6 increment by 1;
create sequence if not exists order_seq start with 6 increment by 1;
create sequence if not exists phone_seq start with 2 increment by 1;
create sequence if not exists product_seq start with 6 increment by 1;
create sequence if not exists review_seq start with 4 increment by 1;
create sequence if not exists smart_watch_seq start with 2 increment by 1;
create sequence if not exists sub_category_seq start with 5 increment by 1;
create sequence if not exists sub_product_seq start with 4 increment by 1;
create sequence if not exists user_seq start with 6 increment by 1;

create table users
(
    is_subscription boolean not null,
    id              bigint  not null
        primary key,
    address         varchar(255),
    email           varchar(255),
    first_name      varchar(255),
    image           varchar(255),
    last_name       varchar(255),
    password        varchar(255),
    phone_number    varchar(255),
    role            varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[]))
);

create table categories
(
    id    bigint not null
        primary key,
    title varchar(255)
);

create table sub_categories
(
    category_id bigint
        constraint fkjwy7imy3rf6r99x48ydq45otw
            references categories,
    id          bigint not null
        primary key,
    title       varchar(255)
);

create table brands
(
    id    bigint not null
        primary key,
    image varchar(255),
    name  varchar(255)
);

create table products
(
    guarantee       integer not null,
    brand_id        bigint
        constraint fka3a4mpsfdf4d2y6r8ra3sc8mv
            references brands,
    category_id     bigint
        constraint fkog2rp4qthbtt2lfyhfo32lsw9
            references categories,
    created_at      timestamp(6) with time zone,
    data_of_issue   timestamp(6) with time zone,
    id              bigint  not null
        primary key,
    sub_category_id bigint
        constraint fkno5p9kcr384tg56cbk8l9l6h2
            references sub_categories,
    description     varchar(255),
    name            varchar(255),
    pdf             varchar(255),
    video_link      varchar(255)
);

create table banners
(
    id bigint not null
        primary key
);

create table banner_images
(
    banner_id bigint not null
        constraint fk633x0a6l0tvcjj7xoeaivj92t
            references banners,
    images    varchar(255)
);

create table baskets
(
    id      bigint not null
        primary key,
    user_id bigint
        constraint fk87s17cinc4wkx0taas5nh0s8h
            references users
);

create table sub_products
(
    article_number      integer          not null,
    price               numeric(38, 2),
    quantity            integer          not null,
    ram                 integer          not null,
    rating              double precision not null,
    rom                 integer          not null,
    id                  bigint           not null
        primary key,
    product_id          bigint
        constraint fkla0cae4wuq18rmsoc8huklhdl
            references products,
    additional_features varchar(255),
    code_color          varchar(255),
    screen_resolution   varchar(255)
);

create table baskets_sub_products
(
    baskets_id      bigint not null
        constraint fkh6luxubdodm5cq48fwjfrk2n2
            references baskets,
    sub_products_id bigint not null
        constraint fkn3xilkcluvtrybn8pve98iflm
            references sub_products
);

create table discounts
(
    sale           integer not null,
    finish_date    timestamp(6) with time zone,
    id             bigint  not null
        primary key,
    start_date     timestamp(6) with time zone,
    sub_product_id bigint
        unique
        constraint fkntmec0erwr39obpdnsywsxiw2
            references sub_products
);

create table laptops
(
    screen_size    double precision not null,
    video_memory   integer          not null,
    id             bigint           not null
        primary key,
    sub_product_id bigint
        unique
        constraint fki5xan852ridcdm69ngy1pgtm6
            references sub_products,
    processor      varchar(255)
        constraint laptops_processor_check
            check ((processor)::text = ANY
                   ((ARRAY ['INTEL_CORE_I3'::character varying, 'INTEL_CORE_I5'::character varying, 'INTEL_CORE_I7'::character varying, 'INTEL_CORE_I9'::character varying, 'INTEL_CELERON'::character varying, 'INTEL_PENTIUM'::character varying, 'AMD'::character varying, 'INTEL_QUAD_CORE'::character varying, 'INTEL_DUAL_CORE'::character varying, 'AMD_RYZEN_3_3250U'::character varying, 'INTEL_CORE_I7_8565U'::character varying, 'AMD_RYZEN_7_4700U'::character varying])::text[])),
    purpose        varchar(255)
        constraint laptops_purpose_check
            check ((purpose)::text = ANY
                   ((ARRAY ['FOR_WORK'::character varying, 'MULTIMEDIA'::character varying, 'GAMING'::character varying, 'FOR_BUSINESS'::character varying, 'FOR_LEARNING'::character varying, 'OFFICE'::character varying])::text[]))
);

create table mailings
(
    finish_date timestamp(6) with time zone,
    id          bigint not null
        primary key,
    start_date  timestamp(6) with time zone,
    description varchar(255),
    image       varchar(255),
    title       varchar(255)
);

create table orders
(
    order_number   integer not null,
    quantity       integer not null,
    total_discount integer not null,
    total_price    numeric(38, 2),
    date_of_order  timestamp(6) with time zone,
    id             bigint  not null
        primary key,
    user_id        bigint
        constraint fk32ql8ubntj5uh44ph9659tiih
            references users,
    status         varchar(255)
        constraint orders_status_check
            check ((status)::text = ANY
                   ((ARRAY ['PENDING'::character varying, 'IN_PROCESSING'::character varying, 'READY_FOR_DELIVERY'::character varying, 'RECEIVED'::character varying, 'CANCEL'::character varying, 'COURIER_ON_THE_WAY'::character varying, 'DELIVERED'::character varying])::text[])),
    type_delivery  varchar(255)
        constraint orders_type_delivery_check
            check ((type_delivery)::text = ANY
                   ((ARRAY ['PICKUP'::character varying, 'DELIVERY'::character varying])::text[])),
    type_payment   varchar(255)
        constraint orders_type_payment_check
            check ((type_payment)::text = ANY
                   ((ARRAY ['CASH'::character varying, 'CARD_ONLINE'::character varying, 'CARD_ON_RECEIPT'::character varying])::text[]))
);

create table orders_sub_products
(
    orders_id       bigint not null
        constraint fkfetafslffow7upnxbj145safx
            references orders,
    sub_products_id bigint not null
        constraint fks95vlcf9cceobto3wc3oo1tfx
            references sub_products
);

create table phones
(
    screen_size      double precision not null,
    sim              integer          not null,
    id               bigint           not null
        primary key,
    sub_product_id   bigint
        unique
        constraint fkrgwjqn4of916f0bpp628cp2lf
            references sub_products,
    battery_capacity varchar(255),
    diagonal_screen  varchar(255)
);
create table reviews
(
    grade            integer not null,
    date_creat_ad    timestamp(6) with time zone,
    id               bigint  not null
        primary key,
    sub_product_id   bigint
        constraint fk9c8aotgs8wdt1fybgabahp56s
            references sub_products,
    user_id          bigint
        constraint fkcgy7qjc1r99dp117y9en6lxye
            references users,
    comment          varchar(255),
    image_link       varchar(255),
    reply_to_comment varchar(255)
);

create table smart_watches
(
    display_discount  double precision not null,
    waterproof        boolean          not null,
    id                bigint           not null
        primary key,
    sub_product_id    bigint
        unique
        constraint fknyei5duh2jv8gpa1w9pl20mcn
            references sub_products,
    an_interface      varchar(255)
        constraint smart_watches_an_interface_check
            check ((an_interface)::text = ANY
                   ((ARRAY ['BLUETOOTH'::character varying, 'WIFI'::character varying, 'GPS'::character varying, 'NFC'::character varying])::text[])),
    gender            varchar(255)
        constraint smart_watches_gender_check
            check ((gender)::text = ANY
                   ((ARRAY ['MALE'::character varying, 'FEMALE'::character varying, 'UNI'::character varying])::text[])),
    housing_material  varchar(255)
        constraint smart_watches_housing_material_check
            check ((housing_material)::text = ANY
                   ((ARRAY ['ACRYLIC'::character varying, 'ALUMINIUM'::character varying, 'CERAMIC'::character varying, 'PLASTIC'::character varying, 'METAL'::character varying, 'STAINLESS_STEEL'::character varying, 'GLASS'::character varying])::text[])),
    hull_shape        varchar(255)
        constraint smart_watches_hull_shape_check
            check ((hull_shape)::text = ANY
                   ((ARRAY ['SQUARE'::character varying, 'ROUND'::character varying, 'OVAL'::character varying, 'RECTANGULAR'::character varying])::text[])),
    material_bracelet varchar(255)
        constraint smart_watches_material_bracelet_check
            check ((material_bracelet)::text = ANY
                   ((ARRAY ['SILICONE'::character varying, 'LEATHER'::character varying, 'RUBBER'::character varying, 'PLASTIC'::character varying, 'NYLON'::character varying, 'IMITATION_LEATHER'::character varying, 'CERAMIC_IMITATION'::character varying])::text[]))
);

create table sub_product_images
(
    sub_product_id bigint not null
        constraint fkdnp3xjwcvct048o69lh8rbc1l
            references sub_products,
    images         varchar(255)
);

create table user_comparison
(
    comparison bigint,
    user_id    bigint not null
        constraint fk2b6ye9thqdmdohlxvhp7d8pw9
            references users
);

create table user_favorite
(
    favorite bigint,
    user_id  bigint not null
        constraint fks45qur86be72xcosxglq7vbia
            references users
);




