--mysql -hrm-bp1z3kjls0l9871t4po.mysql.rds.aliyuncs.com -uroot -pXyl3331996
USE stumanager;
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `expired` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 密码明文：xyl33331996，使用BCryptPasswordEncoder得到。
INSERT INTO auth_user (user_id, user_name, password, expired, locked) VALUES(1, 'root', '$2a$10$QpTpPxkd7.kZzDdj/k7rR.LqTuTxln/sHIATyL1MTah6ghy51QfqW', 0, 0);
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(100) DEFAULT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO auth_role (role_id, role_code, role_name) VALUES(1, 'root', 'ROLE_ROOT');
INSERT INTO auth_role (role_code, role_name) VALUES('faculty', 'ROLE_FACULTY');
INSERT INTO auth_role (role_code, role_name) VALUES('member', 'ROLE_MEMBER');

DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

INSERT INTO auth_user_role (id, user_id, role_code) VALUES(1, 1, 'root');

DROP TABLE IF EXISTS `uploadedResources`;
CREATE TABLE `uploadedResources` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`uploader` varchar(100) NOT NULL,
`title` varchar(100) NOT NULL,
`description` varchar(2000) NULL,
`url` varchar(200) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;