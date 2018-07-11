/*
Navicat MySQL Data Transfer

Source Server         : 9.50
Source Server Version : 50711
Source Host           : 192.168.9.50:3306
Source Database       : launcher-dev

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-07-11 16:04:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for laun_font
-- ----------------------------
DROP TABLE IF EXISTS `laun_font`;
CREATE TABLE `laun_font` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `font_name` varchar(255) DEFAULT NULL COMMENT '字体名称',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题文件实体表';

-- ----------------------------
-- Records of laun_font
-- ----------------------------
INSERT INTO `laun_font` VALUES ('180603907050455', '2018-07-10 10:51:19', '2018-07-10 10:52:03', '黑体', 'group1/M00/00/5B/wKgJGVtEH3-AWHRkAJTNmPmAEF4091.ttf');
INSERT INTO `laun_font` VALUES ('180603907050466', '2018-07-10 10:51:19', '2018-07-10 10:53:12', '楷体', 'group1/M00/00/5B/wKgJGVtEH8WAGneDALPT4NfBo4M614.ttf');
INSERT INTO `laun_font` VALUES ('180603907050477', '2018-07-10 10:51:19', '2018-07-10 10:57:01', '魏碑', 'group1/M00/00/5B/wKgJGVtEH_OAOdyVAD3RBDV0zLY682.TTF');
INSERT INTO `laun_font` VALUES ('180603907050497', '2018-07-10 10:50:11', null, '宋体', 'group1/M00/00/5B/wKgJGVtEHviAHWBHAOnQQAcIHDY316.ttc');
INSERT INTO `laun_font` VALUES ('180603907050498', '2018-07-10 10:51:19', null, '隶书', 'group1/M00/00/5B/wKgJGVtEHzSAc1vAAIy7-PKWP3k249.TTF');

-- ----------------------------
-- Table structure for laun_theme_file
-- ----------------------------
DROP TABLE IF EXISTS `laun_theme_file`;
CREATE TABLE `laun_theme_file` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `theme_id` bigint(20) DEFAULT NULL COMMENT '主题主键',
  `type` int(2) DEFAULT NULL COMMENT '文件类型1:预览主图2:预览配图',
  `index` int(2) DEFAULT NULL COMMENT '下标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题文件实体表';

-- ----------------------------
-- Records of laun_theme_file
-- ----------------------------
