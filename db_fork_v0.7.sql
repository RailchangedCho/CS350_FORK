/* BASED ON DB.FORK_v0.7 */

CREATE TABLE IF NOT EXISTS users (
    user_id varchar(255) PRIMARY KEY,
    user_name varchar(255) NOT NULL UNIQUE,
    user_password varchar(255) NOT NULL,
    user_email varchar(255),
    user_device_id varchar(255) UNIQUE,
    user_status boolean NOT NULL,
    user_default_language varchar(255) NOT NULL,
    user_register_date date NOT NULL,
    user_type int4 NOT NULL,
    user_is_authenticated boolean NOT NULL,
    user_permission int4 NOT NULL,
    user_attributes int4
);

CREATE TABLE IF NOT EXISTS facilities (
    facility_id varchar(255) PRIMARY KEY,
    facliity_business_id varchar(255) NOT NULL UNIQUE,
    facility_name varchar(255) NOT NULL,
    facility_name_eng varchar(255) NOT NULL,
    facility_description text,
    facility_description_eng text,
    facility_register_date date NOT NULL,
    facility_latitude float8,
    facility_longitude float8,
    facility_address varchar(255),
    facility_tag int4,
    facility_open int4,
    facility_max_stamp int4 NOT NULL,
    fk_users_id varchar(255) NOT NULL,
    fk_images_id varchar(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS bookmarks (
    bookmark_id varchar(255) PRIMARY KEY,
    bookmark_register_date date NOT NULL,
    fk_users_id varchar(255) NOT NULL,
    fk_facilities_id varchar(255)
);

CREATE TABLE IF NOT EXISTS stamps (
    stamp_id varchar(255) PRIMARY KEY,
    stamp_num int4 NOT NULL,
    stamp_register_date date NOT NULL,
    fk_images_id varchar(255),
    fk_users_id varchar(255) NOT NULL,
    fk_facilities_id varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews (
    review_id varchar(255) PRIMARY KEY,
    review_text text NOT NULL,
    review_score int4 NOT NULL,
    review_hashtag int4,
    review_register_date date NOT NULL,
    fk_users_id varchar(255) NOT NULL,
    fk_facilities_id varchar(255) NOT NULL,
    fk_images_id varchar(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS images (
    image_id varchar(255) PRIMARY KEY,
    image_name varchar(255) UNIQUE,
    image_file_byte_size float8,
    image_file_location varchar(255),
    image_last_used_date date,
    image_update_date date
);

CREATE TABLE IF NOT EXISTS tokens (
    token_id text PRIMARY KEY,
    token_is_valid boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS reports (
    report_id varchar(255) PRIMARY KEY,
    report_text text NOT NULL,
    report_type int4 NOT NULL,
    report_register_date date NOT NULL,
    fk_users_id varchar(255) NOT NULL,
    fk_reviews_id varchar(255) NOT NULL
);

-- 디폴트 값 세팅 예시
/*
ALTER TABLE admins ALTER admin_status_code SET DEFAULT 1;
ALTER TABLE qnas ALTER answer_date SET DEFAULT null;
ALTER TABLE qnas ALTER qna_answer SET DEFAULT null;
*/

-- FK 제약조건 세팅
ALTER TABLE facilities
    ADD CONSTRAINT fk_users_id FOREIGN KEY (fk_users_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_images_id FOREIGN KEY (fk_images_id) REFERENCES "images"(image_id);

ALTER TABLE bookmarks
    ADD CONSTRAINT fk_users_id FOREIGN KEY (fk_users_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_facilities_id FOREIGN KEY (fk_facilities_id) REFERENCES "facilities"(facility_id);

ALTER TABLE stamps
    ADD CONSTRAINT fk_users_id FOREIGN KEY (fk_users_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_facilities_id FOREIGN KEY (fk_facilities_id) REFERENCES "facilities"(facility_id),
    ADD CONSTRAINT fk_images_id FOREIGN KEY (fk_images_id) REFERENCES "images"(image_id);

ALTER TABLE reviews
    ADD CONSTRAINT fk_users_id FOREIGN KEY (fk_users_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_facilities_id FOREIGN KEY (fk_facilities_id) REFERENCES "facilities"(facility_id),
    ADD CONSTRAINT fk_images_id FOREIGN KEY (fk_images_id) REFERENCES "images"(image_id);

ALTER TABLE reports
    ADD CONSTRAINT fk_users_id FOREIGN KEY (fk_users_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_reviews_id FOREIGN KEY (fk_reviews_id) REFERENCES "reviews"(review_id);

-- DB 값 대입 예시
/*
INSERT INTO orders(order_id, delivery_charge, delivery_charge_unit, order_date, last_edit_date, complete_date, order_status_code, is_payment_completed, is_hook_arrived, location_postal_code, location_address, location_address_detail, fk__orders__branches, fk__orders__admins, fk__orders__users, fk__orders__groups, fk__orders__order_histories) VALUES
    ('e8c75402-1dca-4f43-988d-732a01cebb04', 3000.0, 'KRW', '2023-08-02T11:22:33.140Z', '2023-08-02T11:22:33.140Z', null, 1, true, true, '00000', 'order addr', 'order addr detail', 'd97d52f7-de35-4563-9419-a179615aef73', null, 'samplemasteruser0000', 'fa458f64-c283-4ccf-ba38-970859860f77', '4cd6b9bf-5c0d-4bab-b1c3-e800d3ed5b9c'),
    ('61eabe4e-ae3e-4646-b7c3-391eed923d3e', 3000.0, 'KRW', '2023-08-01T11:22:33.140Z', '2023-
*/