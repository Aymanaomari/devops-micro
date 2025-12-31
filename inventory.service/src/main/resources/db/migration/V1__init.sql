CREATE TABLE `t_inventory`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `sku_code` varchar(255) DEFAULT NULL,
    `quantity` varchar(11)  DEFAULT NULL,
    PRIMARY KEY (`id`)
)