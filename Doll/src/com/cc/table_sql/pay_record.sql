drop table if exists `pay_record`;
create table `pay_record` (
`id`  bigint(20) not null ,
`created_time`  datetime not null ,
`updated_time`  datetime not null ,
`pay_type`  smallint(5) UNSIGNED not null ,
`price`  decimal(6,2) not null ,
`amount`  int(10) UNSIGNED not null ,
`status`  smallint(5) UNSIGNED not null ,
`account_id`  int(11) not null ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC