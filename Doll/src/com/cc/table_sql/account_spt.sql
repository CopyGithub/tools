drop table if exists `account_spt`;
create table `account_spt` (
`account_id`  int(11) not null,
`pay`  decimal(12,2) not null,
`coin_pay`  int(11) not null,
`coin_sign`  int(11) not null,
`ship`  decimal(12,2) not null,
PRIMARY KEY (`account_id`)
)
ENGINE=InnoDB
DEFAULT character set=utf8mb4 collate=utf8mb4_general_ci
ROW_FORMAT=DYNAMIC