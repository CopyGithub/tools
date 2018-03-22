drop table if exists `doll`;
CREATE TABLE `doll` (
`id`  int(11) NOT NULL ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`title`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`stock`  int(10) UNSIGNED NOT NULL ,
`price`  decimal(6,2) NOT NULL ,
`d_type`  int(10) UNSIGNED NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC