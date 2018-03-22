drop table if exists `credit_product`;
CREATE TABLE `credit_product` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`title`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`weight`  int(10) UNSIGNED NOT NULL ,
`price`  int(10) UNSIGNED NOT NULL ,
`stock`  int(10) UNSIGNED NOT NULL ,
`p_type`  int(10) UNSIGNED NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC