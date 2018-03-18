drop table if exists `credit_record`;
CREATE TABLE `credit_record` (
`id`  bigint(20) NOT NULL ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`r_type`  smallint(5) UNSIGNED NOT NULL ,
`title`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`amount`  int(11) NOT NULL ,
`balance`  int(10) UNSIGNED NOT NULL ,
`doll_id` int(11) not null ,
`product_id` int(11) not null ,
`account_id`  int(11) NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC