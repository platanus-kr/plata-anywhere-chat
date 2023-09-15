CREATE TABLE `members` (
  `id` varchar(36) NOT NULL,
  `created` datetime(6) DEFAULT NULL,
  `updated` datetime(6) DEFAULT NULL,
  `app_role` enum('ROLE_ADMIN','ROLE_USER') DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `html_url` varchar(255) DEFAULT NULL,
  `last_activated` datetime(6) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `provider` enum('GITHUB','GOOGLE','KAKAO','NAVER','WEB') DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_idx` (`username`),
  UNIQUE KEY `provider_id_idx` (`provider_id`),
  UNIQUE KEY `email_idx` (`email`),
  UNIQUE KEY `nickname_idx` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `rooms` (
  `id` varchar(36) NOT NULL,
  `created` datetime(6) DEFAULT NULL,
  `updated` datetime(6) DEFAULT NULL,
  `capacity` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `room_public` enum('AVAILABLE','INVISIBLE','OBSERVER','PRIVATE','VOID') DEFAULT NULL,
  `room_status` enum('ALIVE','ENDED') DEFAULT NULL,
  `owner_member_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_room_public` (`room_public`,`room_status`),
  KEY `owner_member_id_idx` (`owner_member_id`),
  CONSTRAINT `owner_member_id_member_fk` FOREIGN KEY (`owner_member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `rooms_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exit_date_time` datetime(6) DEFAULT NULL,
  `join_date_time` datetime(6) DEFAULT NULL,
  `role` enum('MEMBER','OBSERVER','SYSOP') DEFAULT NULL,
  `status` enum('ALIVE','EXITED','VOID','WARNING') DEFAULT NULL,
  `void_end_date_time` datetime(6) DEFAULT NULL,
  `member_id` varchar(36) DEFAULT NULL,
  `room_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_id_room_id` (`member_id`,`room_id`),
  KEY `idx_room_id` (`room_id`),
  CONSTRAINT `room_id_member_fk` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `member_id_member_fk` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
