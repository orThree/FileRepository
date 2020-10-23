/*
 Navicat Premium Data Transfer

 Source Server         : zipfile
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : zipfile

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 22/10/2020 08:56:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_configuration
-- ----------------------------
DROP TABLE IF EXISTS `file_configuration`;
CREATE TABLE `file_configuration`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `download_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定下载的文件名字',
  `download_zip_file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定生成的zip压缩包名字',
  `upload_file_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定文件上传类型',
  `tips` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提示信息',
  `upload_file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定文件上传的位置',
  `down_load_file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定要下载文件的所在路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_configuration
-- ----------------------------
INSERT INTO `file_configuration` VALUES (1, 'aExample.txt', 'aExample.zip', '.txt,.docx,.doc', '上传成功,上传失败', 'D:/Charlin_project/ZipFilePath/Upload', 'D:\\Charlin_project\\ZipFilePath\\Upload\\b3dea9a9-cbe8-4db7-bf2b-a90061406486');

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of files
-- ----------------------------
INSERT INTO `files` VALUES (4, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/b3dea9a9-cbe8-4db7-bf2b-a90061406486/aExample.txt', '2020-10-20 17:26:50');
INSERT INTO `files` VALUES (5, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/a7a2f675-f758-465d-b7d8-ac4241e6bce6/aExample.txt', '2020-10-21 09:46:56');
INSERT INTO `files` VALUES (6, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/9b1ba60d-0a60-4de1-80a5-f3cd50dca287/aExample.txt', '2020-10-21 09:49:18');
INSERT INTO `files` VALUES (7, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/6aa80cb1-05e8-4d36-a673-08aee255efc4/aExample.txt', '2020-10-21 09:53:19');
INSERT INTO `files` VALUES (8, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/4b6a7b9a-4058-454b-a43e-d0a44033c379/aExample.txt', '2020-10-21 09:57:35');
INSERT INTO `files` VALUES (9, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/2b9578af-84aa-4ffa-a4d5-b26235308f0a/aExample.txt', '2020-10-21 10:02:13');
INSERT INTO `files` VALUES (10, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/ad6f94af-408a-4519-82c2-f27fb5d39044/aExample.txt', '2020-10-21 10:04:16');
INSERT INTO `files` VALUES (11, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/76be29f3-01ad-4fc8-b6ad-82dae40a64cc/aExample.txt', '2020-10-21 10:05:10');
INSERT INTO `files` VALUES (12, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/fbaa184a-bb22-467a-b35d-d9e884a1c511/aExample.txt', '2020-10-21 10:06:00');
INSERT INTO `files` VALUES (13, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/60c5ff0d-abb3-407d-b55b-a5719187693b/aExample.txt', '2020-10-21 10:22:08');
INSERT INTO `files` VALUES (14, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/a73fea74-f620-4877-8f97-703025439074/aExample.txt', '2020-10-21 10:28:19');
INSERT INTO `files` VALUES (15, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/231f0cbf-fa2c-4c7f-a26f-d783c26564f5/aExample.txt', '2020-10-21 10:30:05');
INSERT INTO `files` VALUES (16, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/78cb3503-b217-47fd-ba63-d1cbe19b376c/aExample.txt', '2020-10-21 13:41:13');
INSERT INTO `files` VALUES (17, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/bbb1e149-dda8-4a53-b0d5-7b26a2931caa/aExample.txt', '2020-10-21 13:43:04');
INSERT INTO `files` VALUES (18, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/b0b0a2fd-7cf4-4ea2-ad68-6a8c57616c52/aExample.txt', '2020-10-21 13:43:55');
INSERT INTO `files` VALUES (19, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/fc0ec253-d46b-4fd1-935d-4668a68dda6e/aExample.txt', '2020-10-21 13:45:09');
INSERT INTO `files` VALUES (20, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/ec750fdc-82b5-4490-a338-f404cb18fd08/aExample.txt', '2020-10-21 13:51:22');
INSERT INTO `files` VALUES (21, 'aExample.txt', 'D:/Charlin_project/ZipFilePath/Upload/949f836a-2af7-44dc-ad07-3a87effaa8ed/aExample.txt', '2020-10-21 14:03:24');
INSERT INTO `files` VALUES (22, '车位地磁.txt', 'D:/Charlin_project/ZipFilePath/Upload/564f1f0a-e4ac-4036-9b1d-e43fc212ea8f/车位地磁.txt', '2020-10-21 16:14:50');
INSERT INTO `files` VALUES (23, '字段加密.txt', 'D:/Charlin_project/ZipFilePath/Upload/628200b5-689c-4d7f-bc79-7ed35344331e/字段加密.txt', '2020-10-21 16:23:11');
INSERT INTO `files` VALUES (24, 'cry.png', 'D:/Charlin_project/ZipFilePath/Upload/72170320-124d-4101-86c1-a23c774d58a5/cry.png', '2020-10-21 16:30:32');
INSERT INTO `files` VALUES (25, 'out.png', 'D:/Charlin_project/ZipFilePath/Upload/7c6d1b4f-c898-419c-bff3-c5781e6ae5e5/out.png', '2020-10-21 17:24:31');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
