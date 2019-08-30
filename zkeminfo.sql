/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : localhost:3306
 Source Schema         : zkeminfo

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 30/08/2019 10:17:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NULL DEFAULT NULL COMMENT '人员编号',
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人员名称',
  `Time` datetime(0) NULL DEFAULT NULL COMMENT '考勤时间串',
  `Year` int(4) NULL DEFAULT NULL COMMENT '考勤时间：年',
  `Month` int(2) NULL DEFAULT NULL COMMENT '考勤时间：月',
  `Day` int(2) NULL DEFAULT NULL COMMENT '考勤时间：日',
  `Hour` int(2) NULL DEFAULT NULL COMMENT '考勤时间：时',
  `Minute` int(2) NULL DEFAULT NULL COMMENT '考勤时间：分',
  `Second` int(2) NULL DEFAULT NULL COMMENT '考勤时间：秒',
  `VerifyMode` int(2) NULL DEFAULT NULL COMMENT '验证方式',
  `InoutMode` int(5) NULL DEFAULT NULL COMMENT '考勤状态',
  `passWord` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `enable` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否启用',
  `privilege` int(11) NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cron
-- ----------------------------
DROP TABLE IF EXISTS `cron`;
CREATE TABLE `cron`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务名称',
  `cron` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for machineinfo
-- ----------------------------
DROP TABLE IF EXISTS `machineinfo`;
CREATE TABLE `machineinfo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `machineCode` int(11) NULL DEFAULT NULL,
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `port` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
