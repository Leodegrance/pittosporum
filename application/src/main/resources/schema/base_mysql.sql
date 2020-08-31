-- app_store.data_patch_logging definition

CREATE TABLE `data_patch_logging` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `operation` int(5) NOT NULL,
  `module` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `function_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `before_data` text,
  `relate_to` bigint(20) unsigned NOT NULL,
  `after_data` text,
  `create_by` varchar(25) NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10031 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.master_code_category definition

CREATE TABLE `master_code_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `category_name` varchar(15) NOT NULL,
  `desciprtion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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

-- app_store.qrtz_calendars definition

CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_fired_triggers definition

CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_job_details definition

CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_locks definition

CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_paused_trigger_grps definition

CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_scheduler_state definition

CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.quartz_deteail definition

CREATE TABLE `quartz_deteail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `job_name` varchar(25) NOT NULL COMMENT 'job name',
  `job_group` varchar(25) NOT NULL COMMENT 'job group',
  `start_time` varchar(20) DEFAULT NULL COMMENT 'start time',
  `cron_exp` varchar(15) NOT NULL COMMENT 'cron schedule',
  `invoke_param` varchar(50) DEFAULT NULL COMMENT 'invoke param',
  `create_by` varchar(25) NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `update_by` varchar(25) NOT NULL COMMENT 'create user',
  `update_dt` datetime NOT NULL COMMENT 'updated time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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


-- app_store.master_code definition

CREATE TABLE `master_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary_key',
  `code_key` varchar(15) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `code_value` varchar(255) NOT NULL,
  `code_category_id` bigint(20) unsigned DEFAULT NULL,
  `create_by` varchar(25) NOT NULL COMMENT 'create user',
  `create_dt` datetime NOT NULL COMMENT 'create time',
  `status` char(10) NOT NULL DEFAULT 'AT0001',
  PRIMARY KEY (`id`),
  KEY `code_category_id` (`code_category_id`),
  CONSTRAINT `master_code_ibfk_1` FOREIGN KEY (`code_category_id`) REFERENCES `master_code_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_triggers definition

CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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
  `cause` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `profile_id` (`profile_id`),
  CONSTRAINT `store_ibfk_1` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10081 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_blob_triggers definition

CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_cron_triggers definition

CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_simple_triggers definition

CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- app_store.qrtz_simprop_triggers definition

CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `properties` (
  `application` varchar(25) NOT NULL,
  `profile` varchar(100) NOT NULL,
  `label` varchar(100) NOT NULL,
  `key` varchar(510) NOT NULL,
  `value` varchar(8000) NOT NULL,
  PRIMARY KEY (`application`,`profile`,`label`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

