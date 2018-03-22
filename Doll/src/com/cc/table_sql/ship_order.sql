drop table if exists `ship_order`;
CREATE TABLE `ship_order` (
`id`  bigint(20) NOT NULL ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`remark`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`status`  smallint(5) UNSIGNED NOT NULL ,
`account_id`  int(11) NOT NULL ,
`receipt_address_id`  int(11) NULL DEFAULT NULL ,
`ship_no`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`ship_way`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`credit_product_id`  int(11) NULL DEFAULT NULL ,
`o_type`  int(10) UNSIGNED NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC