/*
 Navicat Premium Data Transfer

 Source Server         : zn666
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : localhost:3306
 Source Schema         : car_rental

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 14/05/2025 22:37:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `userid` bigint NULL DEFAULT NULL COMMENT '所属用户ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车辆名称',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆图片',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `kindid` bigint NULL DEFAULT NULL COMMENT '种类id',
  `car_categoryid` bigint NULL DEFAULT NULL COMMENT '品牌id',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '出租状态:0-未出租,1-已出租',
  `money` bigint NOT NULL COMMENT '日租金',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userid`(`userid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '车辆信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES (1, 1, '奔驰e300l', '/images/2c020c3c-17e8-4480-888e-bebda3d9953f_E300L.jpg', '2025-03-29 16:46:36', '2025-05-12 12:24:06', 1, 1, 0, 100);
INSERT INTO `car` VALUES (2, 2, '奔驰c300', '/images/2c3a72f8-067b-4d34-b759-7c481f1a08f3_奔驰c300.jpg', '2025-05-11 14:16:34', '2025-05-12 02:09:35', 1, 1, 0, 150);
INSERT INTO `car` VALUES (3, 2, '宝马M8a', '/images/4f86164f-22ab-4b20-912e-7045a9e9ead2_宝马M8.jpg', '2025-05-11 14:17:36', '2025-05-12 12:08:02', 3, 2, 0, 200);
INSERT INTO `car` VALUES (4, 1, '小米su7', '/images/b041db43-db13-4173-a9c4-41029ae43be0_su7.jpg', '2025-05-09 20:26:41', '2025-05-12 02:10:07', 2, 3, 1, 300);

-- ----------------------------
-- Table structure for car_category
-- ----------------------------
DROP TABLE IF EXISTS `car_category`;
CREATE TABLE `car_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_category`(`category` ASC) USING BTREE COMMENT '分类名称唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '车辆分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of car_category
-- ----------------------------
INSERT INTO `car_category` VALUES (1, '奔驰', '2025-03-23 18:40:25', '2025-03-23 18:40:25');
INSERT INTO `car_category` VALUES (2, '宝马', '2025-05-09 16:01:37', '2025-05-09 16:01:37');
INSERT INTO `car_category` VALUES (3, '小米', '2025-03-25 15:30:45', '2025-05-07 20:58:55');

-- ----------------------------
-- Table structure for kinds
-- ----------------------------
DROP TABLE IF EXISTS `kinds`;
CREATE TABLE `kinds`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `kind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车辆类型',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kinds
-- ----------------------------
INSERT INTO `kinds` VALUES (1, '油车', '2025-05-07 20:28:53', '2025-05-07 20:28:53');
INSERT INTO `kinds` VALUES (2, '电车', '2025-05-07 20:41:55', '2025-05-07 20:41:55');
INSERT INTO `kinds` VALUES (3, '混动', '2025-05-07 20:42:17', '2025-05-07 20:42:17');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `car_id` bigint NOT NULL COMMENT '关联的车辆ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `days` bigint NOT NULL COMMENT '租赁时间',
  `user_id` bigint NOT NULL,
  `car_status` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_car_id`(`car_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 1, '2025-03-29 12:26:43', '2025-05-12 12:28:03', 5, 0, 1);
INSERT INTO `order` VALUES (2, 1, '2025-05-11 14:21:13', '2025-05-12 12:25:17', 9, 0, 0);
INSERT INTO `order` VALUES (4, 1, '2025-05-11 21:31:41', '2025-05-11 21:31:41', 12, 2, 0);
INSERT INTO `order` VALUES (6, 4, '2025-05-12 01:03:09', '2025-05-12 01:03:09', 11, 1, 0);
INSERT INTO `order` VALUES (8, 1, '2025-05-12 01:33:59', '2025-05-12 01:33:59', 21, 1, 0);
INSERT INTO `order` VALUES (9, 1, '2025-05-12 01:35:25', '2025-05-12 01:35:25', 2, 1, 0);
INSERT INTO `order` VALUES (11, 1, '2025-05-12 12:11:57', '2025-05-12 12:11:57', 11, 1, 0);
INSERT INTO `order` VALUES (12, 1, '2025-05-12 12:12:09', '2025-05-12 12:12:09', 22, 1, 0);
INSERT INTO `order` VALUES (13, 1, '2025-05-12 12:24:06', '2025-05-12 12:24:06', 99, 1, 0);

-- ----------------------------
-- Table structure for return
-- ----------------------------
DROP TABLE IF EXISTS `return`;
CREATE TABLE `return`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `car_id` bigint NOT NULL COMMENT '车辆ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_car_id`(`car_id` ASC) USING BTREE,
  CONSTRAINT `fk_return_car` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_return_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '车辆归还记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of return
-- ----------------------------
INSERT INTO `return` VALUES (1, 1, 1, '2025-05-12 01:34:16', '2025-05-12 01:35:07');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '555555', 'male', '15327233272', '2025-03-23 15:40:31', '2025-05-12 12:23:16');
INSERT INTO `user` VALUES (2, 'sentinel', '123456', 'female', '15327233278', '2025-03-23 16:05:49', '2025-05-11 17:29:37');
INSERT INTO `user` VALUES (8, '123', '111111', 'male', '18888888844', '2025-05-11 22:36:51', '2025-05-12 12:05:46');

SET FOREIGN_KEY_CHECKS = 1;
