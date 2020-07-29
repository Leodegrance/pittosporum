drop table profile
drop table store
drop table user_ent

-- app_store.profile definition

CREATE TABLE `profile` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `profile_name` varchar(25) NOT NULL COMMENT 'database name',
  `create_by` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `update_by` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'create user',
  `update_dt` datetime NOT NULL COMMENT 'updated time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- app_store.store definition

CREATE TABLE `store` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `execute_sql` varchar(255) NOT NULL COMMENT 'run sql',
  `execute_result` char(10) NOT NULL COMMENT 'run result',
  `profile_id` bigint(20) unsigned DEFAULT NULL,
  `create_by` varchar(25) NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `update_by` varchar(25) NOT NULL COMMENT 'create user',
  `update_dt` datetime NOT NULL COMMENT 'updated time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  `run_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `profile_id` (`profile_id`),
  CONSTRAINT `store_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10054 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.user_ent definition

CREATE TABLE `user_ent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `name` varchar(25) NOT NULL COMMENT 'user name',
  `pwd` varchar(25) DEFAULT NULL COMMENT 'user pwd',
  `email` varchar(25) DEFAULT NULL COMMENT 'user email',
  `mobile_number` varchar(25) DEFAULT NULL COMMENT 'user email',
  `create_by` varchar(25) NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `update_by` varchar(25) NOT NULL COMMENT 'create user',
  `update_dt` datetime NOT NULL COMMENT 'updated time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


create table app_store.quartz_deteail(
	id bigint(20) unsigned primary key NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
	job_name varchar(25) not null COMMENT 'job name',
	job_group varchar(25) not null  COMMENT 'job group',
	start_time varchar(20)  COMMENT 'start time',
	cron_exp varchar(15) not null COMMENT 'cron schedule',
	invoke_param varchar(50)  COMMENT 'invoke param',
	status char(10) not null default 'AT0001'
)ENGINE=InnoDB auto_increment = 10000;
