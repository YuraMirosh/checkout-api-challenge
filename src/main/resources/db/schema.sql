CREATE TABLE watch
(
    id                   VARCHAR(36) PRIMARY KEY,
    watch_name           VARCHAR(36) NOT NULL,
    unit_price           INTEGER     NOT NULL,
    discount_unit_amount INTEGER,
    discount_batch_price INTEGER
);
