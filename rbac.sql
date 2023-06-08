CREATE DATABASE IF NOT EXISTS `rbac` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user ID',
  `user_account` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user account',
  `user_phone` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'phone number',
  `user_email` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'user email',
  `user_password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user password, encoded',
  `user_status` tinyint(4) DEFAULT NULL COMMENT '0-normal, 1-disabled, -1-deleted',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk` (`user_account`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='user table';

CREATE TABLE IF NOT EXISTS `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'profile ID',
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `nick_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'nick name',
  `avatar` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'avatar',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auth ID',
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `login_type` int(11) DEFAULT NULL,
  `openid` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `access_token` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_login_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user login history ID',
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '0-login, 1-logout',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `ip_address` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip address',
  `user_agent` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'role ID',
  `role_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'role name',
  `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'permission ID',
  `perm_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'permission password',
  `type` tinyint(4) DEFAULT NULL COMMENT '0-page, 1-operation',
  `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'description',
  `path` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `role_permission` (
  `role_id` bigint(20) NOT NULL,
  `perm_id` bigint(20) NOT NULL COMMENT 'permission ID',
  PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'organization ID, unique',
  `org_name` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'organization name',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS  `user_organization` (
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `org_id` bigint(20) NOT NULL COMMENT 'organization ID',
  PRIMARY KEY (`user_id`,`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS  `data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'data ID, unique',
  `data_name` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'data name',
  `org_id` bigint(20) DEFAULT NULL COMMENT 'organization ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


TRUNCATE TABLE data;
TRUNCATE TABLE organization;
TRUNCATE TABLE permission;
TRUNCATE TABLE role;
TRUNCATE TABLE role_permission;
TRUNCATE TABLE user;
TRUNCATE TABLE user_auth;
TRUNCATE TABLE user_login_history;
TRUNCATE TABLE user_organization;
TRUNCATE TABLE user_profile;
TRUNCATE TABLE user_role;

INSERT INTO `user` (`user_account`, `user_phone`, `user_email`, `user_password`, `user_status`) VALUES
  ('admin', '13111111111', 'admin@example.com', '$2a$10$BLYxyDMlacAaAapo4D5Ar.OQ9sVhsxvvDINO0JzM9Q6Q5f7e4LUBa', 0),
  ('user', '13222222222', 'user@example.com', '$2a$10$RNST4TsduOnaPAEhyqWi2', 0);


INSERT INTO `role` (`role_name`, `description`) VALUES
  ('Admin', 'Administrator role with full access'),
  ('User', 'Standard user role with limited access');

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
  (1, 1),
  (2, 2),

INSERT INTO `role_permission` (`role_id`, `perm_id`)
VALUES
    (1,1000),
    (1,1001),
    (1,1002),
    (1,1003),
    (1,1004),
    (1,1005),
    (1,3000),
    (1,3001),
    (1,3002),
    (1,3003),
    (1,4000),
    (1,5000),
    (2,1000),
    (2,3000),
    (2,4000),
    (2,5000);

INSERT INTO `organization` (`org_name`) VALUES
  ('Head Co.'),
  ('Branch1 Co.'),
  ('Branch2 Co.');

INSERT INTO `data` (`data_name`, `org_id`)
VALUES
    ('Data 1', 1),
    ('Data 2', 1),
    ('Data 3', 1),
    ('Data 4', 1),
    ('Data 5', 2),
    ('Data 6', 2),
    ('Data 7', 3);

