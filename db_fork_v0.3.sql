/* BASED ON DB.FORK_v0.3 */

CREATE TABLE IF NOT EXISTS users (
    user_id varchar(255) PRIMARY KEY,
    user_password varchar(255) NOT NULL,
    user_email varchar(255) NOT NULL UNIQUE,
    user_type int4 NOT NULL,
    user_permission int4 NOT NULL,
    user_attributes text
);

CREATE TABLE IF NOT EXISTS facilities (
    facility_id varchar(255) PRIMARY KEY,
    facliity_business_id varchar(255) NOT NULL UNIQUE,
    facility_name varchar(255) NOT NULL,
    facility_name_eng varchar(255) NOT NULL,
    facility_description text,
    facility_description_eng text,
    facility_tag varchar(255),
    facility_open int4,
    facility_max_stamp int4 NOT NULL,
    fk_user_id varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS bookmarks (
    bookmark_id varchar(255) PRIMARY KEY,
    fk_user_id varchar(255) NOT NULL,
    fk_facility_id varchar(255)
);

CREATE TABLE IF NOT EXISTS stamps (
    stamp_id varchar(255) PRIMARY KEY,
    stamp_num int4 NOT NULL,
    fk_user_id varchar(255) NOT NULL,
    fk_facility_id varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews (
    review_id varchar(255) PRIMARY KEY,
    review_text text NOT NULL,
    review_score int4 NOT NULL,
    fk_user_id varchar(255) NOT NULL,
    fk_facility_id varchar(255) NOT NULL,
    fk_image_id varchar(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS images (
    image_id varchar(255) PRIMARY KEY,
    image_name varchar(255) UNIQUE,
    image_file_byte_size float8,
    image_file_location varchar(255),
    image_last_used_date date,
    image_update_date date
);

CREATE TABLE IF NOT EXISTS token (
    token_id text PRIMARY KEY,
    token_is_valid boolean NOT NULL
);

-- 디폴트 값 세팅 예시
/*
ALTER TABLE admins ALTER admin_status_code SET DEFAULT 1;
ALTER TABLE qnas ALTER answer_date SET DEFAULT null;
ALTER TABLE qnas ALTER qna_answer SET DEFAULT null;
*/

-- FK 제약조건 세팅
ALTER TABLE facilities
    ADD CONSTRAINT fk_user_id FOREIGN KEY (fk_user_id) REFERENCES "users"(user_id);

ALTER TABLE bookmarks
    ADD CONSTRAINT fk_user_id FOREIGN KEY (fk_user_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_facility_id FOREIGN KEY (fk_facility_id) REFERENCES "facilities"(facility_id);

ALTER TABLE stamps
    ADD CONSTRAINT fk_user_id FOREIGN KEY (fk_user_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_facility_id FOREIGN KEY (fk_facility_id) REFERENCES "facilities"(facility_id);

ALTER TABLE reviews
    ADD CONSTRAINT fk_user_id FOREIGN KEY (fk_user_id) REFERENCES "users"(user_id),
    ADD CONSTRAINT fk_facility_id FOREIGN KEY (fk_facility_id) REFERENCES "facilities"(facility_id),
    ADD CONSTRAINT fk_image_id FOREIGN KEY (fk_image_id) REFERENCES "images"(image_id);

-- DB 값 대입
/*
INSERT INTO orders(order_id, delivery_charge, delivery_charge_unit, order_date, last_edit_date, complete_date, order_status_code, is_payment_completed, is_hook_arrived, location_postal_code, location_address, location_address_detail, fk__orders__branches, fk__orders__admins, fk__orders__users, fk__orders__groups, fk__orders__order_histories) VALUES
    ('e8c75402-1dca-4f43-988d-732a01cebb04', 3000.0, 'KRW', '2023-08-02T11:22:33.140Z', '2023-08-02T11:22:33.140Z', null, 1, true, true, '00000', 'order addr', 'order addr detail', 'd97d52f7-de35-4563-9419-a179615aef73', null, 'samplemasteruser0000', 'fa458f64-c283-4ccf-ba38-970859860f77', '4cd6b9bf-5c0d-4bab-b1c3-e800d3ed5b9c'),
    ('61eabe4e-ae3e-4646-b7c3-391eed923d3e', 3000.0, 'KRW', '2023-08-01T11:22:33.140Z', '2023-
*/