CREATE TABLE IF NOT EXISTS GYM
(
    id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(50)  NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone   VARCHAR(9)   NULL,
    email   VARCHAR(255) NULL,
    city_id BIGINT       NOT NULL
);