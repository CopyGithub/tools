drop table if exists `transaction`;
CREATE TABLE `transaction` (
`id`  bigint(20) NOT NULL ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`trans_type`  smallint(5) UNSIGNED NOT NULL ,
`title`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`amount`  int(11) NOT NULL ,
`balance`  decimal(12,2) NULL DEFAULT NULL ,
`pay_id`  bigint(20) NULL DEFAULT NULL ,
`status`  smallint(5) UNSIGNED NOT NULL ,
`account_id`  int(11) NOT NULL ,
`game_id`  bigint(20) NULL DEFAULT NULL ,
`is_win` smallint(5) UNSIGNED NOT NULL ,
`game_result` smallint(5) UNSIGNED NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC