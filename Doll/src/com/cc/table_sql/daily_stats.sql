drop table if exists `daily_stats`;
CREATE TABLE `daily_stats` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`date`  date NOT NULL ,
`package_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`channel`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`platform`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`active`  int(10) UNSIGNED NOT NULL ,
`pay_count`  int(10) UNSIGNED NOT NULL ,
`pay_amount`  double NOT NULL ,
`new_pay_count`  int(10) UNSIGNED NOT NULL ,
`new_pay_amount`  double NOT NULL ,
`cost`  double NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
