CREATE TABLE `t_orders`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id`   VARCHAR(255),
    `product_name` VARCHAR(255),
    `quantity`     INT(11),
    `price`        DECIMAL(19, 2)
);
