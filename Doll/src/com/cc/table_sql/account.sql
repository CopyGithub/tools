drop table if exists `account`;
create table `account` (
`id`  int(11) not null,
`created_time`  datetime not null,
`updated_time`  datetime not null,
`nickname`  varchar(100) character set utf8mb4 collate utf8mb4_general_ci not null,
`gender`  smallint(5) UNSIGNED NOT NULL ,
`balance`  decimal(12,2) not null,
`credit`  int(10) unsigned not null,
`phone`  varchar(20) character set utf8mb4 collate utf8mb4_general_ci not null,
`account_type`  smallint(5) unsigned not null,
`is_virtual`  tinyint(1) not null,
`status`  smallint(5) unsigned not null,
`channel`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`package_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL ,
`pltm`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`ip`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`did`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`svc` varchar(20) character set utf8mb4 collate utf8mb4_general_ci not null,
`dm` varchar(20) character set utf8mb4 collate utf8mb4_general_ci not null,
`mac`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`idfa`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`anid`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`vc`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`vn`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,
`pn`  varchar(50) character set utf8mb4 collate utf8mb4_general_ci not null,

PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT character set=utf8mb4 collate=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC
