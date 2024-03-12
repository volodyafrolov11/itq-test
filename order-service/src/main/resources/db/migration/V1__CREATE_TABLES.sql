CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    order_total_amount INTEGER,
    order_date TIMESTAMP
);

CREATE TABLE order_details (
    order_id BIGINT,
    detail_id INTEGER,
    detail_product_name VARCHAR(255),
    detail_product_quantity INTEGER,
    PRIMARY KEY (order_id, detail_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);