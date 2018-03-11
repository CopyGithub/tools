drop table if exists `catch_record`;
CREATE TABLE `catch_record` (
`id`  bigint(20) NOT NULL ,
`created_time`  datetime NOT NULL ,
`updated_time`  datetime NOT NULL ,
`account_id`  int(11) NOT NULL ,
`doll_id`  int(11) NOT NULL ,
`room_id`  int(11) NOT NULL ,
`status`  smallint(5) UNSIGNED NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC