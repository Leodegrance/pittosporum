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
alter table store add cause varchar(4000) default null

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
	create_by varchar(25) NOT NULL COMMENT 'create user',
	create_dt datetime NOT null COMMENT 'create time',
	update_by varchar(25) NOT NULL COMMENT 'create user',
	update_dt datetime NOT null COMMENT 'updated time',
	status char(10) not null default 'AT0001'
)ENGINE=InnoDB auto_increment = 10000;

-- app_store.audit_trail_logging definition
CREATE TABLE data_patch_logging (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `operation` int(5) NOT NULL,
  `module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `function_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `before_data` varchar(8000) DEFAULT NULL,
  `relate_to` bigint(20) unsigned NOT NULL,
  `after_data` varchar(8000) DEFAULT NULL,
  `create_by` varchar(25) NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  PRIMARY KEY (`id`)
  /*foreign key(relate_to) references app_store.store(id)*/
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table app_store.master_code_category(
	id bigint(20) unsigned primary key NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
	category_name varchar(15) not null,
	desciprtion varchar(255)
)ENGINE=InnoDB auto_increment = 10000;


 create table app_store.master_code(
	id bigint(20) unsigned primary key NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
	code_key varchar(15) not null,
	description varchar(255),
	code_value varchar(255) not null,
	code_category_id bigint(20) unsigned,
	create_by varchar(25) NOT NULL COMMENT 'create user',
	create_dt datetime NOT null COMMENT 'create time',
	status char(10) not null default 'AT0001',
	foreign key(code_category_id) references app_store.master_code_category(id)
)ENGINE=InnoDB auto_increment = 10000;