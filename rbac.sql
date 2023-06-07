CREATE DATABASE IF NOT EXISTS `rbac` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user ID',
  `user_account` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_phone` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_email` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_status` tinyint(4) DEFAULT NULL,
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk` (`user_account`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='user table';

CREATE TABLE IF NOT EXISTS `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'profile ID',
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `nick_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'nick name',
  `avatar` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'avatar',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auth ID',
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `login_type` int(11) DEFAULT NULL,
  `openid` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `access_token` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_login_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user login history ID',
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `login_time` datetime DEFAULT NULL COMMENT 'login time',
  `logout_time` datetime DEFAULT NULL COMMENT 'logout time',
  `ip_address` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip address',
  `user_agent` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'role ID',
  `role_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'role name',
  `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'permission ID',
  `permission_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `path` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE IF NOT EXISTS `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'organization ID, unique',
  `name` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'organization name, unique',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'createdTime',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updatedTime',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS  `user_organization` (
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `organization_id` bigint(20) NOT NULL COMMENT 'organization ID',
  PRIMARY KEY (`user_id`,`organization_id`),
  KEY `user_organization_organization_id_fk` (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS  `data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'data ID, unique',
  `name` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'data name, unique',
  `organization_id` bigint(20) DEFAULT NULL COMMENT 'organization ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;