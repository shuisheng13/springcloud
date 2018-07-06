/*
Navicat MySQL Data Transfer

Source Server         : 9.50
Source Server Version : 50711
Source Host           : 192.168.9.50:3306
Source Database       : launcher-test

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-07-05 09:44:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for laun_adver_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_adver_statistics`;
CREATE TABLE `laun_adver_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `advertising_expenses` bigint(20) unsigned DEFAULT NULL COMMENT '广告费用',
  `adver_agent` varchar(255) DEFAULT NULL COMMENT '代理',
  `adver_click` bigint(20) DEFAULT NULL COMMENT '点击次数',
  `adver_clickdevice` bigint(20) DEFAULT NULL COMMENT '展示设备次数',
  `adver_display` varchar(255) DEFAULT NULL COMMENT '点击率',
  `adver_display_num` bigint(20) DEFAULT NULL COMMENT '展示次数',
  `adver_hour` datetime DEFAULT NULL COMMENT '日期小时',
  `adver_main` varchar(255) DEFAULT NULL COMMENT '广告主',
  `adver_position` varchar(255) DEFAULT NULL COMMENT '广告位',
  `adver_title` varchar(255) DEFAULT NULL COMMENT '广告标题',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告统计表';

-- ----------------------------
-- Records of laun_adver_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_application
-- ----------------------------
DROP TABLE IF EXISTS `laun_application`;
CREATE TABLE `laun_application` (
  `id` bigint(20) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `channel_id` varchar(255) DEFAULT NULL COMMENT '渠道ID',
  `logo_path` varchar(255) DEFAULT NULL COMMENT '应用图标地址',
  `name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `package_id` varchar(255) DEFAULT NULL COMMENT '应用id',
  `package_name` varchar(255) DEFAULT NULL COMMENT '应用包名',
  `package_path` varchar(255) DEFAULT NULL COMMENT '应用安装包地址',
  `start_count_ratio` varchar(255) DEFAULT NULL COMMENT '启动次数占比',
  `yesterday_start_count` int(11) DEFAULT NULL COMMENT '应用昨日启动次数',
  `end_time` datetime DEFAULT NULL COMMENT '海报结束时间',
  `start_time` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL COMMENT '上传包名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用实体';

-- ----------------------------
-- Records of laun_application
-- ----------------------------

-- ----------------------------
-- Table structure for laun_application_poster
-- ----------------------------
DROP TABLE IF EXISTS `laun_application_poster`;
CREATE TABLE `laun_application_poster` (
  `id` bigint(20) NOT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL COMMENT '应用ID',
  `poster_path` varchar(255) DEFAULT NULL COMMENT '海报图片地址',
  `width` int(11) DEFAULT NULL COMMENT '海报宫格宽度',
  `height` int(11) DEFAULT NULL COMMENT '海报宫格高度',
  `type` int(11) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `application_id` (`application_id`),
  CONSTRAINT `laun_application_poster_ibfk_1` FOREIGN KEY (`application_id`) REFERENCES `laun_application` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用海报';

-- ----------------------------
-- Records of laun_application_poster
-- ----------------------------

-- ----------------------------
-- Table structure for laun_application_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_application_statistics`;
CREATE TABLE `laun_application_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL COMMENT '应用ID',
  `application_time` datetime DEFAULT NULL COMMENT '日期',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `start_up_num` bigint(20) DEFAULT NULL COMMENT '启动次数',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用统计表';

-- ----------------------------
-- Records of laun_application_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_attribute
-- ----------------------------
DROP TABLE IF EXISTS `laun_attribute`;
CREATE TABLE `laun_attribute` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `attribute_key` varchar(255) DEFAULT NULL COMMENT 'version-版本',
  `attribute_key_index` int(5) DEFAULT NULL COMMENT '下标',
  `attribute_status` int(11) DEFAULT NULL COMMENT '0：不可用；1：可用',
  `attribute_value` varchar(255) DEFAULT NULL COMMENT '值',
  `des` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Launcher版本表';

-- ----------------------------
-- Records of laun_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for laun_car_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_car_statistics`;
CREATE TABLE `laun_car_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `add_up_num` bigint(20) DEFAULT NULL COMMENT '累计车辆数量',
  `car_active` bigint(20) DEFAULT NULL COMMENT '活跃车辆',
  `car_avg_time` varchar(255) DEFAULT NULL COMMENT '平均活跃时长',
  `car_num` bigint(20) DEFAULT NULL COMMENT '每日新增车辆',
  `car_start` bigint(20) DEFAULT NULL COMMENT '启动次数',
  `car_time` datetime DEFAULT NULL COMMENT '日期',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `up_grade_num` bigint(20) DEFAULT NULL COMMENT '升级车辆数量',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  `start_up_num` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆统计表';

-- ----------------------------
-- Records of laun_car_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_channel
-- ----------------------------
DROP TABLE IF EXISTS `laun_channel`;
CREATE TABLE `laun_channel` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `channel_id` varchar(255) DEFAULT NULL COMMENT '渠道ID',
  `logo` varchar(255) DEFAULT NULL COMMENT '渠道LOGO',
  `name` varchar(255) DEFAULT NULL COMMENT '渠道名称',
  `user_id` bigint(20) DEFAULT NULL,
  `channel_status` int(11) NOT NULL DEFAULT '0' COMMENT '渠道状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道实体';

-- ----------------------------
-- Records of laun_channel
-- ----------------------------

-- ----------------------------
-- Table structure for laun_custom_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_custom_statistics`;
CREATE TABLE `laun_custom_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `custom_name` varchar(255) DEFAULT NULL COMMENT '事件名称',
  `custom_num` bigint(20) DEFAULT NULL COMMENT '事件消息数量',
  `custom_param_name` varchar(255) DEFAULT NULL COMMENT '参数统计',
  `custom_time` varchar(255) DEFAULT NULL COMMENT '消息平均时长',
  `time` datetime DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义事件统计表';

-- ----------------------------
-- Records of laun_custom_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_custom_widget
-- ----------------------------
DROP TABLE IF EXISTS `laun_custom_widget`;
CREATE TABLE `laun_custom_widget` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `category` bigint(20) DEFAULT NULL COMMENT '自定义widget类型',
  `name` varchar(255) DEFAULT NULL COMMENT '自定义widget名称',
  `prew_image` varchar(255) DEFAULT NULL COMMENT '图片',
  `size` varchar(255) DEFAULT NULL COMMENT '默认尺寸',
  `version` varchar(255) DEFAULT NULL COMMENT '自定义widget版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义widget';

-- ----------------------------
-- Records of laun_custom_widget
-- ----------------------------

-- ----------------------------
-- Table structure for laun_event_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_event_statistics`;
CREATE TABLE `laun_event_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `event_id` bigint(20) DEFAULT NULL COMMENT '事件id',
  `event_name` varchar(255) DEFAULT NULL COMMENT '事件名称',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件统计表';

-- ----------------------------
-- Records of laun_event_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_permissions
-- ----------------------------
DROP TABLE IF EXISTS `laun_permissions`;
CREATE TABLE `laun_permissions` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `introducs` varchar(255) DEFAULT NULL COMMENT '权限的描述',
  `levels` int(11) DEFAULT NULL COMMENT '级别',
  `name` varchar(255) DEFAULT NULL COMMENT '权限的名字',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '权限的父级',
  `resources` varchar(255) DEFAULT NULL COMMENT '资源路径',
  `status` int(11) DEFAULT NULL COMMENT '权限的状态',
  `sub_persion` varchar(255) DEFAULT NULL COMMENT '权限的子集',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of laun_permissions
-- ----------------------------
INSERT INTO `laun_permissions` VALUES ('1', null, null, '主题管理', '1', '主题管理', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('2', null, null, '应用管理', '1', '应用管理', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('3', null, null, 'widget管理', '1', 'widget管理', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('4', null, null, '渠道管理', '1', '渠道管理', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('5', null, null, '账号授权', '1', '账号授权', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6', null, null, '统计分析', '1', '统计分析', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('7', null, null, '运维日志', '1', '运维日志', '0', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('1001', null, null, '主题列表', '2', '主题列表', '1', null, '0', '4996211860,1707604985');
INSERT INTO `laun_permissions` VALUES ('1002', null, null, '新建主题', '2', '新建主题', '1', null, '0', '9082200581,1180491418,1666470425,1806793400,4996211860,5249622290,9972807561,9972807560,1562726080,1188410346');
INSERT INTO `laun_permissions` VALUES ('1003', null, null, '编辑主题', '2', '编辑主题', '1', null, '0', '1180491418,1666470425,1806793400,4996211860,7558259550,8893210392,9972807561,9972807560,1562726080,1188410346');
INSERT INTO `laun_permissions` VALUES ('1004', null, null, '删除主题', '2', '删除主题', '1', null, '0', '5914387660');
INSERT INTO `laun_permissions` VALUES ('1005', null, null, '预览主题', '2', '预览主题', '1', null, '0', '7558259550');
INSERT INTO `laun_permissions` VALUES ('1006', null, null, '主题上下架', '2', '主题上下架', '1', null, '0', '8687866230');
INSERT INTO `laun_permissions` VALUES ('1007', null, null, '创建副本', '2', '创建副本', '1', null, '0', '9082200581,1180491418,1666470425,1806793400,4996211860,5249622290,8893210392,1632618509,9972807561,9972807560,1562726080,1188410346');
INSERT INTO `laun_permissions` VALUES ('2001', null, null, '应用列表', '2', '应用查询', '2', null, '0', '8627445320,1632618509');
INSERT INTO `laun_permissions` VALUES ('2002', null, null, '应用添加', '2', '应用添加', '2', null, '0', '1632618509,9510964700,8625436050');
INSERT INTO `laun_permissions` VALUES ('2003', null, null, '海报配置', '2', '海报配置', '2', null, '0', '1915942550,5868656040,6053125680');
INSERT INTO `laun_permissions` VALUES ('2004', null, null, '自定义宫格', '2', '自定义宫格', '2', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('2005', null, null, '应用修改', '2', '应用修改', '2', null, '0', '1037653575,9485845500');
INSERT INTO `laun_permissions` VALUES ('3001', null, null, 'widget列表', '2', 'widget列表', '3', null, '0', '2041973659');
INSERT INTO `laun_permissions` VALUES ('3002', null, null, '新建widget', '2', '新建widget', '3', null, '0', '1445351717,1581222263,1457474698,1188410346,1219598444,7639963500,1445351717,1562726080,1678440560,1741343204,3232962970,5151033190,1632618509,9972807560,9972807561');
INSERT INTO `laun_permissions` VALUES ('3003', null, null, 'widget编辑', '2', 'widget编辑', '3', null, '0', '1445351717,1581222263,1457474698,1188410346,1219598444,7639963500,1445351717,1562726080,1678440560,1741343204,3232962970,5151033190,1632618509,9972807560,9972807561');
INSERT INTO `laun_permissions` VALUES ('3004', null, null, '删除widget', '2', '删除widget', '3', null, '0', '1647424112,3003142190');
INSERT INTO `laun_permissions` VALUES ('3005', null, null, '下载widget', '2', '下载widget', '3', null, '0', '5021892450');
INSERT INTO `laun_permissions` VALUES ('3006', null, null, '替换widget', '2', '替换widget', '3', null, '0', '2710375000');
INSERT INTO `laun_permissions` VALUES ('3007', null, null, '创建副本widget', '2', '创建副本widget', '3', null, '0', '1445351717,1581222263,1457474698,1188410346,1219598444,7639963500,1445351717,1562726080,1678440560,1741343204,3232962970,5151033190,1632618509,9972807560,9972807561');
INSERT INTO `laun_permissions` VALUES ('3008', null, null, '上传基础widget', '2', '上传基础widget', '3', '', '0', '1260718981');
INSERT INTO `laun_permissions` VALUES ('3009', null, null, '预览widget', '2', '预览widget', '3', null, '0', '1869625653');
INSERT INTO `laun_permissions` VALUES ('4001', null, null, '渠道列表', '2', '渠道列表', '4', null, '0', '1437431123,2166353730');
INSERT INTO `laun_permissions` VALUES ('4002', null, null, '添加渠道', '2', '添加渠道', '4', null, '0', '2127226155,2099060458,9972807560,1101534907,1455215066,2166353730');
INSERT INTO `laun_permissions` VALUES ('4003', null, null, '编辑渠道', '2', '编辑渠道', '4', null, '0', '2127226155,2099060458,9972807560,1101534907,1455215066,2166353730,1437714975');
INSERT INTO `laun_permissions` VALUES ('4004', null, null, '删除渠道', '2', '删除渠道', '4', null, '0', '9287219170');
INSERT INTO `laun_permissions` VALUES ('4005', null, null, '编辑渠道权限', '2', '编辑渠道权限', '4', null, '0', '8740434850');
INSERT INTO `laun_permissions` VALUES ('5001', null, null, '成员列表', '2', '成员列表', '5', null, '0', '2065589661');
INSERT INTO `laun_permissions` VALUES ('5002', null, null, '添加成员', '2', '添加成员', '5', null, '0', '1455215066,5043000890,1944277276,8768030480,2166353730,4657146780,1188410346');
INSERT INTO `laun_permissions` VALUES ('5003', null, null, '编辑成员', '2', '编辑成员', '5', null, '0', '1455215066,5043000890,1944277276,8768030480,2166353730,4657146780,1188410346');
INSERT INTO `laun_permissions` VALUES ('5004', null, null, '删除成员', '2', '删除成员', '5', null, '0', '4087072910');
INSERT INTO `laun_permissions` VALUES ('5005', null, null, '编辑成员权限', '2', '编辑成员权限', '5', null, '0', '1455215066,5043000890,1944277276,8768030480,2166353730,1188410346');
INSERT INTO `laun_permissions` VALUES ('6001', null, null, '车辆趋势', '2', '车辆趋势', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6002', null, null, '应用分布', '2', '应用分布', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6003', null, null, '版本分布', '2', '版本分布', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6004', null, null, 'widget统计', '2', 'widget统计', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6005', null, null, '主题统计', '2', '主题统计', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6006', null, null, '事件管理', '2', '事件管理', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('6007', null, null, '自定义事件管理', '2', '自定义事件管理', '6', null, '0', null);
INSERT INTO `laun_permissions` VALUES ('7001', null, null, '日志查询', '2', '日志查询', '7', null, '0', '7000000000,6463574260,5120579920');
INSERT INTO `laun_permissions` VALUES ('1037653575', null, null, '根据应用id获取应该详情', '3', '根据应用id获取应该详情', null, '/application/findById', '0', null);
INSERT INTO `laun_permissions` VALUES ('1101534907', null, null, '查询重复渠道', '3', '查询重复渠道', null, '/channel/checkRepeatChannel', '0', null);
INSERT INTO `laun_permissions` VALUES ('1180491418', null, null, '添加分类', '3', '添加分类', null, '/theme/addType', '0', null);
INSERT INTO `laun_permissions` VALUES ('1188410346', null, null, '查询所有widget类型列表', '3', '查询所有widget类型列表', null, '/findWidgetCategory', '0', null);
INSERT INTO `laun_permissions` VALUES ('1219598444', null, null, '查询所有widget版本列表', '3', '查询所有widget版本列表', null, '/findWidgetVersion', '0', null);
INSERT INTO `laun_permissions` VALUES ('1231680346', null, null, '根据条件去查询车辆统计', '3', '根据条件去查询车辆统计', null, '/statistics/carStatistics', '0', null);
INSERT INTO `laun_permissions` VALUES ('1260718981', null, null, '上传基础widget压缩包', '3', '上传基础widget压缩包', null, '/uploadBaseWidget', '0', null);
INSERT INTO `laun_permissions` VALUES ('1288240611', null, null, 'widget使用情况-概览列表', '3', 'widget使用情况-概览列表', null, '/statistics/findOverViewStatic', '0', null);
INSERT INTO `laun_permissions` VALUES ('1437431123', null, null, '查询渠道列表', '3', '查询渠道列表', null, '/channel/findChannelList', '0', null);
INSERT INTO `laun_permissions` VALUES ('1437714975', null, null, '根据主键查询渠道', '3', '根据主键查询渠道', null, '/channel/findChannelById', '0', null);
INSERT INTO `laun_permissions` VALUES ('1445351717', null, null, '查询widget所属渠道ID', '3', '查询widget所属渠道ID', null, '/getWidgetChannels', '0', null);
INSERT INTO `laun_permissions` VALUES ('1455215066', null, null, '模糊查询用户', '3', '模糊查询用户', null, '/accountNumber/findUserByLike', '0', null);
INSERT INTO `laun_permissions` VALUES ('1457474698', null, null, '查询所有widget尺寸列表', '3', '查询所有widget尺寸列表', null, '/findWidgetDefaultSize', '0', null);
INSERT INTO `laun_permissions` VALUES ('1562726080', null, null, '根据widgetId获取基本信息及其文件信息', '3', '根据widgetId获取基本信息及其文件信息', null, '/getGoupWidgetById', '0', null);
INSERT INTO `laun_permissions` VALUES ('1581222263', null, null, '根据widgetId查询该widget所有的图片路径', '3', '根据widgetId查询该widget所有的图片路径', null, '/getImgPathBywidthId', '0', '');
INSERT INTO `laun_permissions` VALUES ('1632618509', null, null, '根据name模糊查询渠道/无分页', '3', '根据name模糊查询渠道/无分页', null, '/channel/findAll', '0', null);
INSERT INTO `laun_permissions` VALUES ('1647424112', null, null, '根据主键删除widget', '3', '根据主键删除widget', null, '/deleteWidgetById', '0', '3003142190');
INSERT INTO `laun_permissions` VALUES ('1647961560', null, null, '根据主键id删除widget类型', '3', '根据主键id删除widget类型', null, '/deleteWidgetType', '0', null);
INSERT INTO `laun_permissions` VALUES ('1666470425', null, null, '查询所有的版本', '3', '查询所有的版本', null, '/theme/selectVersion', '0', null);
INSERT INTO `laun_permissions` VALUES ('1678440560', null, null, '创建widget变体', '3', '创建widget变体', null, '/saveWidget', '0', null);
INSERT INTO `laun_permissions` VALUES ('1707604985', null, null, '根据条件去查询主题列表', '3', '根据条件去查询主题列表', null, '/theme/selectByCount', '0', null);
INSERT INTO `laun_permissions` VALUES ('1741343204', null, null, '修改widget变体', '3', '修改widget变体', null, '/updateWidget', '0', null);
INSERT INTO `laun_permissions` VALUES ('1758698667', null, null, 'widget使用情况-图表展示', '3', 'widget使用情况-图表展示', null, '/statistics/findWisdgetStatic', '0', null);
INSERT INTO `laun_permissions` VALUES ('1806793400', null, null, '根据id去删除分类', '3', '根据id去删除分类', null, '/theme/delectTypeById', '0', null);
INSERT INTO `laun_permissions` VALUES ('1869625653', null, null, '预览widget', '3', '预览widget', null, '/widgetpreview', '0', null);
INSERT INTO `laun_permissions` VALUES ('1903143093', null, null, '查询渠道列表', '3', '查询渠道列表', null, '/statistics/findChannelList', '0', null);
INSERT INTO `laun_permissions` VALUES ('1915942550', null, null, '根据应用id获取海报详情', '3', '根据应用id获取海报详情', null, '/application/findPosterByAppId', '0', null);
INSERT INTO `laun_permissions` VALUES ('1944277276', null, null, '账号批量更新', '3', '账号批量更新', null, '/accountNumber/updateAccount', '0', null);
INSERT INTO `laun_permissions` VALUES ('2004381462', null, null, '应用分布-详情列表', '3', '应用分布-详情列表', null, '/statistics/findAppDetailStatic', '0', null);
INSERT INTO `laun_permissions` VALUES ('2041973659', null, null, '查询widget列表', '3', '查询widget列表', null, '/findWidgetsList', '0', null);
INSERT INTO `laun_permissions` VALUES ('2065589661', null, null, '列表展示', '3', '列表展示', null, '/accountNumber/accountList', '0', null);
INSERT INTO `laun_permissions` VALUES ('2099060458', null, null, '获取渠道id', '3', '获取渠道id', null, '/channel/findChannelId', '0', null);
INSERT INTO `laun_permissions` VALUES ('2127226155', null, null, '添加渠道或更新渠道', '3', '添加渠道或更新渠道', null, '/channel/saveChannel', '0', null);
INSERT INTO `laun_permissions` VALUES ('2166353730', null, null, '查询当前用户的权限（不包含子权限）', '3', '查询当前用户的权限（不包含子权限）', null, '/permissions/findPermissionsByUser', '0', null);
INSERT INTO `laun_permissions` VALUES ('2710375000', null, null, '替换基础widget压缩包', '3', '替换基础widget压缩包', null, '/replaceBaseWidget', '0', null);
INSERT INTO `laun_permissions` VALUES ('3003142190', null, null, '查询widget在主题内使用情况', '3', '查询widget在主题内使用情况', null, '/findWidgetUseNum', '0', null);
INSERT INTO `laun_permissions` VALUES ('3232962970', null, null, '保存自定义widget', '3', '保存自定义widget', null, '/saveGroupWidget', '0', null);
INSERT INTO `laun_permissions` VALUES ('3624562890', null, null, '更新渠道', '3', '更新渠道', null, '/channel/updateChannel', '0', null);
INSERT INTO `laun_permissions` VALUES ('4087072910', null, null, '根据id去删除权限', '3', '根据id去删除权限', null, '/accountNumber/deleteById', '0', null);
INSERT INTO `laun_permissions` VALUES ('4613401320', null, null, '查询widget类型列表', '3', '查询widget类型列表', null, '/findWidgetTypeList', '0', null);
INSERT INTO `laun_permissions` VALUES ('4657146780', null, null, '根据id去查看修改人的权限', '3', '根据id去查看修改人的权限', null, '/accountNumber/selectById', '0', null);
INSERT INTO `laun_permissions` VALUES ('4996211860', null, null, '查询所有的分类', '3', '查询所有的分类', null, '/theme/selectByType', '0', null);
INSERT INTO `laun_permissions` VALUES ('5021892450', null, null, '文件下载', '3', '文件下载', null, '/dowloadwidgetFile', '0', null);
INSERT INTO `laun_permissions` VALUES ('5043000890', null, null, '账号授权的渠道列表', '3', '账号授权的渠道列表', null, '/accountNumber/selectAccountList', '0', null);
INSERT INTO `laun_permissions` VALUES ('5120579920', null, null, '根据渠道id查询用户', '3', '根据渠道id查询用户', null, '/channel/findUserByChannelId', '0', null);
INSERT INTO `laun_permissions` VALUES ('5151033190', null, null, '自定义widget回显', '3', '自定义widget回显', null, '/showGroupWidget', '0', null);
INSERT INTO `laun_permissions` VALUES ('5249622290', null, null, '保存主题', '3', '保主题', null, '/theme/saveTheme', '0', null);
INSERT INTO `laun_permissions` VALUES ('5462341360', null, null, '列表', '3', '列表', null, '/statistics/carListStatistics', '0', null);
INSERT INTO `laun_permissions` VALUES ('5868656040', null, null, '上传海报图片', '3', '上传海报图片', null, '/application/uploadPoster', '0', null);
INSERT INTO `laun_permissions` VALUES ('5914387660', null, null, '根据id去删除', '3', '根据id去删除', null, '/theme/delectById', '0', null);
INSERT INTO `laun_permissions` VALUES ('6053125680', null, null, '保存海报详情', '3', '保存海报详情', null, '/application/savePoster', '0', null);
INSERT INTO `laun_permissions` VALUES ('6463574260', null, null, '查询所有的渠道', '3', '查询所有的渠道', null, '/channel/findChannelAll', '0', null);
INSERT INTO `laun_permissions` VALUES ('6612497290', null, null, '文件下载', '3', '文件下载', null, '/dowloadFile', '0', null);
INSERT INTO `laun_permissions` VALUES ('7000000000', null, null, '日志查询', '3', '日志查询', null, '/findStoreBrowseLogs', '0', null);
INSERT INTO `laun_permissions` VALUES ('7558259550', null, null, '根据id去查看主题', '3', '根据id去查看主题', null, '/theme/selectById', '0', null);
INSERT INTO `laun_permissions` VALUES ('7639963500', null, null, '根据widgetId查询widget的信息，基础或变体回显使用', '3', '根据widgetId查询widget的信息，基础或变体回显使用', null, '/findWidgetById', '0', null);
INSERT INTO `laun_permissions` VALUES ('7970807280', null, null, '应用分布-图表，概览列表', '3', '应用分布-图表，概览列表', null, '/statistics/findApplicationStatic', '0', null);
INSERT INTO `laun_permissions` VALUES ('8528693840', null, null, '获取当前登录人的渠道', '3', '获取当前登录人的渠道', null, '/accountNumber/', '0', null);
INSERT INTO `laun_permissions` VALUES ('8625436050', null, null, '添加应用', '3', '添加应用', null, '/application/save', '0', null);
INSERT INTO `laun_permissions` VALUES ('8627445320', null, null, '获取应用列表', '3', '获取应用列表', null, '/application/list', '0', null);
INSERT INTO `laun_permissions` VALUES ('8687866230', null, null, '根据id去修改上下架状态', '3', '根据id去修改上下架状态', null, '/theme/modifyStatus', '0', null);
INSERT INTO `laun_permissions` VALUES ('8740434850', null, null, '批量更新渠道权限', '3', '批量更新渠道权限', null, '/channel/updateChannelList', '0', null);
INSERT INTO `laun_permissions` VALUES ('8768030480', null, null, '账号添加', '3', '账号添加', null, '/accountNumber/saveAccount', '0', null);
INSERT INTO `laun_permissions` VALUES ('8893210390', null, null, '主题相关的统计', '3', '主题相关的统计', null, '/statistics/themeStatistics', '0', null);
INSERT INTO `laun_permissions` VALUES ('8893210392', null, null, '修改主题', '3', '修改主题', null, '/theme/updateThem', '0', null);
INSERT INTO `laun_permissions` VALUES ('9082200580', null, null, '添加Widget类型', '3', '添加Widget类型', null, '/insertWidgetType', '0', null);
INSERT INTO `laun_permissions` VALUES ('9082200581', null, null, '查询海报列表', '3', '查询海报列表', null, '/application/select', '0', null);
INSERT INTO `laun_permissions` VALUES ('9287219170', null, null, '删除渠道', '3', '删除渠道', null, '/channel/deleteChannel', '0', null);
INSERT INTO `laun_permissions` VALUES ('9485845500', null, null, '更新应用信息', '3', '更新应用信息', null, '/application/update', '0', null);
INSERT INTO `laun_permissions` VALUES ('9510964700', null, null, '上传应用', '3', '上传应用', null, '/application/upload', '0', null);
INSERT INTO `laun_permissions` VALUES ('9972807560', null, null, '文件上传', '3', '文件上传', null, '/fileUpload', '0', null);
INSERT INTO `laun_permissions` VALUES ('9972807561', null, null, '用于管理员创建主题或widget时的下拉列表', '3', '用于管理员创建主题或widget时的下拉列表', null, '/findWidgetsPullList', '0', null);

-- ----------------------------
-- Table structure for laun_role
-- ----------------------------
DROP TABLE IF EXISTS `laun_role`;
CREATE TABLE `laun_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `introducs` varchar(255) DEFAULT NULL COMMENT 'm描述',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名字',
  `status` int(11) DEFAULT NULL COMMENT '角色状态',
  `levels` int(11) DEFAULT NULL COMMENT '角色级别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of laun_role
-- ----------------------------
INSERT INTO `laun_role` VALUES ('180700432528353', null, null, null, '28', null, null);
INSERT INTO `laun_role` VALUES ('180701012347392', null, null, null, '29', null, null);
INSERT INTO `laun_role` VALUES ('180701384434186', null, null, null, '3', null, null);
INSERT INTO `laun_role` VALUES ('180701519926540', null, null, null, '5', null, null);
INSERT INTO `laun_role` VALUES ('180702003089638', null, null, null, '15', null, null);
INSERT INTO `laun_role` VALUES ('180703804230206', null, null, null, '10', null, null);
INSERT INTO `laun_role` VALUES ('180703864057760', null, null, null, '30', null, null);
INSERT INTO `laun_role` VALUES ('180703892374827', null, null, null, '16', null, null);
INSERT INTO `laun_role` VALUES ('180704044937106', null, null, null, '17', null, null);
INSERT INTO `laun_role` VALUES ('180704230639977', null, null, null, '18', null, null);
INSERT INTO `laun_role` VALUES ('180704450300829', null, null, null, '13', null, null);
INSERT INTO `laun_role` VALUES ('180705296521436', null, null, null, '14', null, null);
INSERT INTO `laun_role` VALUES ('180705800702227', null, null, null, '6', null, null);

-- ----------------------------
-- Table structure for laun_role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `laun_role_permissions`;
CREATE TABLE `laun_role_permissions` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `permissions_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of laun_role_permissions
-- ----------------------------
INSERT INTO `laun_role_permissions` VALUES ('180701001966359', null, null, '3006', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701009133063', null, null, '4001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701018470922', null, null, '6005', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701054354755', null, null, '3002', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180701055591696', null, null, '2004', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701078224005', null, null, '3002', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701081477859', null, null, '3008', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701107558170', null, null, '3002', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701124748547', null, null, '3001', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180701135137435', null, null, '3003', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180701163579880', null, null, '1007', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701164342962', null, null, '1005', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701182121483', null, null, '6005', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701211578967', null, null, '5001', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180701226083748', null, null, '2004', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701230006047', null, null, '4002', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701231851162', null, null, '1006', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701251516218', null, null, '6006', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701267447286', null, null, '4002', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180701274561266', null, null, '3002', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701280810790', null, null, '2004', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701286857388', null, null, '5002', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701286966978', null, null, '6003', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701294102992', null, null, '5002', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180701310450708', null, null, '1004', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701364688026', null, null, '7001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701416699297', null, null, '6002', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701483810966', null, null, '3004', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701487928716', null, null, '1002', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701496632220', null, null, '3006', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180701515164620', null, null, '1004', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701515888372', null, null, '4004', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701548565144', null, null, '3005', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180701584691110', null, null, '3003', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701589418439', null, null, '4002', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701590031008', null, null, '3006', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701593203184', null, null, '3003', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701599686700', null, null, '5003', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180701607433836', null, null, '3009', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701624523699', null, null, '5003', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701625496324', null, null, '1003', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701632497577', null, null, '2002', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701680152971', null, null, '1002', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180701683409303', null, null, '3004', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701684708172', null, null, '5003', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180701685905018', null, null, '1001', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180701690779404', null, null, '1003', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701692621677', null, null, '6001', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701698723277', null, null, '6007', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701698930931', null, null, '1003', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701716621843', null, null, '4003', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180701728607895', null, null, '2003', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701729083407', null, null, '4003', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701730779923', null, null, '7001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701756434606', null, null, '6004', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180701778026309', null, null, '1004', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701803522721', null, null, '5002', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701814070598', null, null, '4005', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701821705223', null, null, '3009', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701842083540', null, null, '4001', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701883436023', null, null, '5003', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701887347780', null, null, '3006', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701896018242', null, null, '6006', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180701902892122', null, null, '6004', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701924775835', null, null, '5003', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180701959455657', null, null, '4004', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180701961369352', null, null, '6006', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180701966192005', null, null, '6006', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180701974138472', null, null, '2002', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180701980955855', null, null, '3003', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180702014704826', null, null, '1006', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702025824324', null, null, '3001', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180702029229459', null, null, '1006', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702047874205', null, null, '6002', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702105593044', null, null, '3007', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180702106401211', null, null, '6006', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180702107020209', null, null, '4005', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180702113991075', null, null, '5005', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702124889747', null, null, '4002', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702145948210', null, null, '6002', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702147545331', null, null, '1001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702159714231', null, null, '3004', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180702169431599', null, null, '6004', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702186249381', null, null, '2003', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702186913623', null, null, '3002', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180702219331465', null, null, '5005', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180702237285806', null, null, '5003', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702237414296', null, null, '5002', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702257735411', null, null, '6006', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180702266453363', null, null, '5004', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702271034112', null, null, '6003', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180702279281412', null, null, '4003', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180702288758591', null, null, '1004', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702295354520', null, null, '5001', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180702304115709', null, null, '1002', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702317015058', null, null, '1001', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702327992928', null, null, '4005', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702345121532', null, null, '1001', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180702350644381', null, null, '1004', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702361503245', null, null, '6003', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702371600764', null, null, '3003', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702387978963', null, null, '4002', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702398247203', null, null, '5004', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180702402455456', null, null, '3009', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180702403502620', null, null, '1002', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702423461573', null, null, '3002', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180702424828705', null, null, '4005', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180702437162534', null, null, '5005', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702438213808', null, null, '1002', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702452564040', null, null, '5001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180702475947525', null, null, '1002', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180702511975929', null, null, '4001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702519931915', null, null, '5001', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180702524650066', null, null, '4004', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702529923013', null, null, '3001', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702533762812', null, null, '3007', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180702590960050', null, null, '5005', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180702635278190', null, null, '5004', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180702661028207', null, null, '6003', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702666102696', null, null, '3003', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180702689479131', null, null, '3005', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180702699811989', null, null, '2001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180702717547260', null, null, '1004', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702735311634', null, null, '3007', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702738320926', null, null, '5002', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180702744971479', null, null, '2002', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702762178618', null, null, '3007', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180702764260980', null, null, '3009', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702764427224', null, null, '3008', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702817485093', null, null, '4005', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180702821672869', null, null, '5003', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180702823966840', null, null, '2005', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180702836920029', null, null, '3007', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702839159315', null, null, '4005', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702841987970', null, null, '1006', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180702857335408', null, null, '5001', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180702870408437', null, null, '3004', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702877874012', null, null, '3002', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702890893698', null, null, '3008', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702896722109', null, null, '2001', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180702920272764', null, null, '3007', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180702923558674', null, null, '4003', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702925617395', null, null, '5004', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180702943377658', null, null, '3004', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180702962151372', null, null, '2001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180702981544168', null, null, '3007', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180702988393202', null, null, '1003', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180703005134991', null, null, '5005', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180703017100872', null, null, '4001', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180703018015307', null, null, '5003', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180703022618099', null, null, '2004', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703030450034', null, null, '4002', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180703064164414', null, null, '3001', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703079453901', null, null, '6002', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703097010262', null, null, '1006', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703123225430', null, null, '4005', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180703143364064', null, null, '3006', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703188312604', null, null, '3006', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180703197179633', null, null, '5002', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180703206279313', null, null, '6004', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703216976665', null, null, '6005', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703277373018', null, null, '6004', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703277564971', null, null, '5004', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180703278634473', null, null, '3005', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180703294797191', null, null, '5001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703300419128', null, null, '1001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180703302255449', null, null, '5005', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180703367237591', null, null, '6002', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703379637204', null, null, '3005', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180703387890663', null, null, '1005', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703404266067', null, null, '3009', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703420161000', null, null, '4001', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703435293619', null, null, '5005', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703436781552', null, null, '6002', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180703443524788', null, null, '5004', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180703449741632', null, null, '4005', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703454656220', null, null, '6005', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703488577500', null, null, '3009', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180703503397462', null, null, '6005', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703505751619', null, null, '1007', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703529256402', null, null, '3004', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180703531300417', null, null, '5003', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180703532763191', null, null, '6002', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703540638961', null, null, '3005', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703544444796', null, null, '6006', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703551575712', null, null, '4001', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703553820953', null, null, '2001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703565677685', null, null, '1007', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180703570875422', null, null, '5002', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180703598350233', null, null, '3008', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703599353212', null, null, '5004', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180703607812130', null, null, '3002', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180703616198818', null, null, '3003', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703618462531', null, null, '1003', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180703628595358', null, null, '6001', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703658781282', null, null, '1005', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703690046459', null, null, '4003', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703691252284', null, null, '6007', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703695486361', null, null, '3002', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180703728382078', null, null, '7001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703736324581', null, null, '3004', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703736854303', null, null, '3002', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180703744694571', null, null, '5004', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703759096932', null, null, '1005', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703782436565', null, null, '4005', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703786948301', null, null, '3005', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180703787436771', null, null, '1001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703794617678', null, null, '1007', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703799431197', null, null, '1007', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703810429050', null, null, '1006', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703819585395', null, null, '4003', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703836068260', null, null, '5004', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180703870757920', null, null, '5005', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180703885565344', null, null, '1003', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703897259062', null, null, '3005', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703903248520', null, null, '2005', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703922948274', null, null, '1004', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180703924646256', null, null, '2005', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180703928800594', null, null, '6002', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180703931697452', null, null, '1001', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180703957782458', null, null, '6003', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180703970137323', null, null, '6001', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180703972689237', null, null, '5001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180703980289316', null, null, '3001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180703999163954', null, null, '5003', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180704074639336', null, null, '1002', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180704079890789', null, null, '2003', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704113175717', null, null, '1007', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704122260293', null, null, '2003', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704126589119', null, null, '2004', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704129135634', null, null, '6005', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704130364485', null, null, '3003', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704153195905', null, null, '1006', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704153404699', null, null, '3006', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180704159306921', null, null, '4004', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704187914749', null, null, '5002', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180704190623876', null, null, '6001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704193159437', null, null, '6006', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704194487330', null, null, '4004', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704246969848', null, null, '3004', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180704256388544', null, null, '4001', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180704257330101', null, null, '3001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704261660498', null, null, '5004', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180704271335252', null, null, '3004', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180704276978526', null, null, '6003', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704279175049', null, null, '5001', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180704289543595', null, null, '4004', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704301756792', null, null, '3003', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180704303555556', null, null, '3004', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180704318549510', null, null, '3008', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180704373262991', null, null, '3001', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180704386481396', null, null, '6001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180704418667725', null, null, '3007', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180704422335431', null, null, '2002', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704423440302', null, null, '3007', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180704423480769', null, null, '5004', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704448198469', null, null, '3005', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180704513327580', null, null, '1001', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180704517339868', null, null, '4002', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704523387812', null, null, '3006', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180704524823434', null, null, '3005', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180704531151983', null, null, '1002', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180704550732143', null, null, '4004', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180704552569321', null, null, '5004', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704553477308', null, null, '1006', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180704558261706', null, null, '4003', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704596867748', null, null, '5003', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704621703220', null, null, '5002', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704626829237', null, null, '6003', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704648790475', null, null, '1007', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180704688231585', null, null, '3007', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704705330855', null, null, '5002', '180700432528353');
INSERT INTO `laun_role_permissions` VALUES ('180704734843969', null, null, '2001', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704740730586', null, null, '5001', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180704746577521', null, null, '3005', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180704757487823', null, null, '3008', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180704774109704', null, null, '1005', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180704779642983', null, null, '6004', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180704793855302', null, null, '5005', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180704796853403', null, null, '1007', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704811188576', null, null, '6003', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704816593699', null, null, '3001', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180704819826103', null, null, '6007', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180704825557112', null, null, '6005', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704826081725', null, null, '6007', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704838674824', null, null, '5003', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704845795471', null, null, '2005', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704846162451', null, null, '3001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704869853729', null, null, '3006', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704880377940', null, null, '3009', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180704883973452', null, null, '4004', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180704886099949', null, null, '1002', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704908097668', null, null, '6005', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180704922034244', null, null, '1003', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180704938735297', null, null, '6007', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704948761226', null, null, '6007', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704962959330', null, null, '1005', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180704964493112', null, null, '1003', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180704967719109', null, null, '3006', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180704981477101', null, null, '6007', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180705009401464', null, null, '3008', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180705030309344', null, null, '6001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705039839794', null, null, '3008', '180704044937106');
INSERT INTO `laun_role_permissions` VALUES ('180705053365420', null, null, '3009', '180701519926540');
INSERT INTO `laun_role_permissions` VALUES ('180705054717994', null, null, '3008', '180704230639977');
INSERT INTO `laun_role_permissions` VALUES ('180705061276288', null, null, '4003', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705082150725', null, null, '3009', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180705083752725', null, null, '5005', '180701384434186');
INSERT INTO `laun_role_permissions` VALUES ('180705097146992', null, null, '3009', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705108589632', null, null, '5005', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705120547538', null, null, '6007', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705139874999', null, null, '1004', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705150230893', null, null, '5001', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180705168899465', null, null, '1005', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180705211847830', null, null, '3003', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180705223258463', null, null, '3007', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180705226954958', null, null, '3004', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180705234696998', null, null, '6001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180705242579896', null, null, '4004', '180703892374827');
INSERT INTO `laun_role_permissions` VALUES ('180705255186466', null, null, '3008', '180702003089638');
INSERT INTO `laun_role_permissions` VALUES ('180705283904453', null, null, '5001', '180703864057760');
INSERT INTO `laun_role_permissions` VALUES ('180705283946932', null, null, '1006', '180704450300829');
INSERT INTO `laun_role_permissions` VALUES ('180705309949008', null, null, '3005', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705346669348', null, null, '4001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705352104679', null, null, '6004', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705360962735', null, null, '2003', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705408674494', null, null, '3001', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180705411639752', null, null, '4001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705428206631', null, null, '2002', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705469655556', null, null, '3002', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705475282350', null, null, '4002', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180705486242029', null, null, '3009', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180705508902432', null, null, '1003', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705522687892', null, null, '5001', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180705537447591', null, null, '1005', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705537574461', null, null, '1001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180705660190761', null, null, '5002', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705720774366', null, null, '3006', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180705790263324', null, null, '2005', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180705820635911', null, null, '1004', '180705800702227');
INSERT INTO `laun_role_permissions` VALUES ('180705878379830', null, null, '1005', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180706026717615', null, null, '3003', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180706068927217', null, null, '7001', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180706087628008', null, null, '3001', '180703804230206');
INSERT INTO `laun_role_permissions` VALUES ('180706132449645', null, null, '4003', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180706177461662', null, null, '5002', '180705296521436');
INSERT INTO `laun_role_permissions` VALUES ('180706225729753', null, null, '3008', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180706242126992', null, null, '4002', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180706246641865', null, null, '1007', '180701012347392');
INSERT INTO `laun_role_permissions` VALUES ('180706254989940', null, null, '5005', '180705296521436');

-- ----------------------------
-- Table structure for laun_theme_administration
-- ----------------------------
DROP TABLE IF EXISTS `laun_theme_administration`;
CREATE TABLE `laun_theme_administration` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `background` varchar(255) DEFAULT NULL COMMENT '主题背景',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `file_size` bigint(20) DEFAULT NULL COMMENT '主题字节大小',
  `fm_app_img_url` varchar(255) DEFAULT NULL COMMENT 'FM',
  `folat_point` varchar(255) DEFAULT NULL COMMENT '新建尺寸浮动',
  `level` int(3) DEFAULT NULL COMMENT '会员等级1:普通会员',
  `long_palace` int(11) DEFAULT NULL COMMENT '主题宫格长',
  `long_resolution` bigint(10) DEFAULT NULL COMMENT '主题分辨率长',
  `music_app_img_url` varchar(255) DEFAULT NULL COMMENT '音乐App',
  `phone_app_img_url` varchar(255) DEFAULT NULL COMMENT '电话App',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `title` varchar(255) DEFAULT NULL COMMENT '主题名称',
  `type_id` bigint(20) DEFAULT NULL COMMENT '主题分类的id',
  `version` varchar(20) DEFAULT NULL COMMENT '主题版本',
  `weather_app_img_url` varchar(255) DEFAULT NULL COMMENT '天气App',
  `wide_palace` int(5) DEFAULT NULL COMMENT '主题宫格宽',
  `wide_resolution` int(10) DEFAULT NULL COMMENT '主题分辨率宽',
  `width_or_higth_ratio` varchar(255) DEFAULT NULL COMMENT '新建尺寸长宽比',
  `font` varchar(255) DEFAULT NULL COMMENT '主题使用字体',
  `config_json` text COMMENT '静态资源json',
  `creator_channel_id` bigint(20) DEFAULT NULL COMMENT '拥有渠道id',
  `basic_json` text COMMENT '主题底屏json',
  `widget_json` text COMMENT '主题信息组件json',
  `theme_json` text,
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者(0:管理员)',
  `zip_url` varchar(255) DEFAULT NULL COMMENT 'zip包保存地址',
  `isPush` int(3) DEFAULT NULL COMMENT '是否强推主题 1：是',
  `status` int(3) DEFAULT '0' COMMENT '主题状态0暂存1:未上架2:已上架3:已下架',
  `preview_path` varchar(255) DEFAULT NULL COMMENT 'api接口主图',
  `urls` varchar(1500) DEFAULT NULL COMMENT 'api接口图片urls',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题实体表';

-- ----------------------------
-- Records of laun_theme_administration
-- ----------------------------

-- ----------------------------
-- Table structure for laun_theme_classification
-- ----------------------------
DROP TABLE IF EXISTS `laun_theme_classification`;
CREATE TABLE `laun_theme_classification` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `classification_name` varchar(255) DEFAULT NULL COMMENT '新建主题分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题分类实体';

-- ----------------------------
-- Records of laun_theme_classification
-- ----------------------------

-- ----------------------------
-- Table structure for laun_theme_config
-- ----------------------------
DROP TABLE IF EXISTS `laun_theme_config`;
CREATE TABLE `laun_theme_config` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `config_name` varchar(255) DEFAULT NULL COMMENT '配置名称',
  `height` varchar(255) DEFAULT NULL COMMENT '纵向高',
  `height_wieghts` varchar(255) DEFAULT NULL COMMENT '纵向高权重',
  `laun_theme_id` bigint(20) DEFAULT NULL COMMENT '主题id',
  `relative_layout` int(11) NOT NULL COMMENT '是否使用相对布局1:是2:否',
  `type` int(11) NOT NULL COMMENT '配置分类1:widget2:海报',
  `widget_id` bigint(20) DEFAULT NULL COMMENT '根据type，1 widgetid ，2 海报id',
  `width` varchar(255) DEFAULT NULL COMMENT '横向宽',
  `width_to_height` varchar(255) DEFAULT NULL COMMENT '是否锁定宽高比',
  `width_wieghts` varchar(255) DEFAULT NULL COMMENT '横向宽权重',
  `lattice` varchar(255) DEFAULT NULL COMMENT 'left , top, right，bottom宫格坐标（左、上、右、下）',
  `property` text COMMENT 'json串',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父节点id',
  `code_id` varchar(255) DEFAULT NULL COMMENT 'widget标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题配置实体';

-- ----------------------------
-- Records of laun_theme_config
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题文件实体表';

-- ----------------------------
-- Records of laun_theme_file
-- ----------------------------

-- ----------------------------
-- Table structure for laun_theme_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_theme_statistics`;
CREATE TABLE `laun_theme_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `avg_time` varchar(255) DEFAULT NULL COMMENT '平均时长',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `count` bigint(20) DEFAULT NULL COMMENT '使用次数',
  `count_car` bigint(20) DEFAULT NULL COMMENT '使用车量次数',
  `effe_theme` bigint(20) DEFAULT NULL COMMENT '有效主题数',
  `num_start_time` datetime DEFAULT NULL COMMENT '数据产生时间',
  `theme_id` bigint(20) DEFAULT NULL COMMENT '主题id',
  `theme_prop` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题统计表';

-- ----------------------------
-- Records of laun_theme_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_theme_width_to_hight
-- ----------------------------
DROP TABLE IF EXISTS `laun_theme_width_to_hight`;
CREATE TABLE `laun_theme_width_to_hight` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `align` varchar(255) DEFAULT NULL COMMENT '对齐方式',
  `base_align` bigint(20) DEFAULT NULL COMMENT '目标idlaunThemeConfig的id',
  `distance` bigint(20) DEFAULT NULL COMMENT '边距',
  `theme_config_id` bigint(20) DEFAULT NULL COMMENT 'widgetId',
  `type` int(11) NOT NULL COMMENT '类型1:横向2:纵向',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题配置widgth实体';

-- ----------------------------
-- Records of laun_theme_width_to_hight
-- ----------------------------

-- ----------------------------
-- Table structure for laun_user
-- ----------------------------
DROP TABLE IF EXISTS `laun_user`;
CREATE TABLE `laun_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `login_date` datetime DEFAULT NULL COMMENT '登陆时间',
  `pass_word` varchar(255) DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `sex` int(11) NOT NULL COMMENT '性别',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道主键',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型：0-四维管理员，1-渠道管理员，2-普通用户',
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of laun_user
-- ----------------------------
INSERT INTO `laun_user` VALUES ('1', '2018-06-01 11:10:39', '2018-06-01 11:12:00', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'root', null, '0', null);
INSERT INTO `laun_user` VALUES ('2', '2018-06-01 11:13:53', '2018-06-03 14:45:11', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'admin1@system.com', null, null, '超管下普通管理员');
INSERT INTO `laun_user` VALUES ('3', '2018-05-13 11:59:11', '2018-07-03 15:35:20', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin2@system.com', '180700731341175', '1', '超管下普通管理员2');
INSERT INTO `laun_user` VALUES ('5', '2018-07-03 10:15:14', '2018-07-03 15:35:45', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'admin3@system.com', null, '2', 'admin3');
INSERT INTO `laun_user` VALUES ('6', '2018-05-13 11:59:11', '2018-06-03 17:36:20', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin4@system.com', '180705080344493', '1', '3333');
INSERT INTO `laun_user` VALUES ('7', '2018-05-13 11:59:11', '2018-06-03 17:36:40', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin5@system.com', null, null, '55');
INSERT INTO `laun_user` VALUES ('8', '2018-05-30 15:16:32', '2018-06-03 17:37:25', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin6@system.com', null, null, '333');
INSERT INTO `laun_user` VALUES ('9', '2018-05-13 11:59:11', '2018-06-03 17:45:06', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin7@system.com', null, null, '123456');
INSERT INTO `laun_user` VALUES ('10', '2018-05-13 11:59:11', '2018-06-03 17:46:10', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin8@system.com', '180703737054841', '1', '123456');
INSERT INTO `laun_user` VALUES ('11', '2018-06-07 13:54:57', '2018-06-07 13:54:57', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin9@system.com', null, null, '12');
INSERT INTO `laun_user` VALUES ('12', '2018-05-13 11:59:11', '2018-06-03 18:00:12', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'admin10@system.com', null, null, '333');
INSERT INTO `laun_user` VALUES ('13', '2018-06-01 11:06:48', '2018-06-03 16:00:39', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher1@channel.com', '180701409383132', '1', '科技公司A1');
INSERT INTO `laun_user` VALUES ('14', '2018-05-13 11:59:11', '2018-06-03 14:28:05', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher2@channel.com', '180701902739556', '1', '渠道1');
INSERT INTO `laun_user` VALUES ('15', '2018-07-03 15:20:49', '2018-07-03 15:28:24', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher3@channel.com', '180701409383132', null, 'launchier1下的账号');
INSERT INTO `laun_user` VALUES ('16', '2018-05-30 15:47:13', '2018-06-03 18:00:53', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher4@channel.com', '180705176082297', '1', '2222');
INSERT INTO `laun_user` VALUES ('17', '2018-07-03 15:45:29', '2018-07-03 15:45:29', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher5@channel.com', '180705176082297', null, '测试渠道A下的A1');
INSERT INTO `laun_user` VALUES ('18', '2018-07-03 15:53:31', '2018-07-03 15:53:31', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher6@channel.com', '180701902739556', null, '测试渠道B下的B1');
INSERT INTO `laun_user` VALUES ('19', '2018-06-07 13:54:06', '2018-06-28 15:38:02', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher7@channel.com', null, null, '测试');
INSERT INTO `laun_user` VALUES ('20', '2018-07-03 15:21:00', '2018-07-03 15:21:00', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher8@channel.com', null, null, '戚薇');
INSERT INTO `laun_user` VALUES ('21', '2018-05-13 11:59:11', '2018-06-08 11:13:02', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'launcher9@channel.com', null, null, '测试添加账号日志记录');
INSERT INTO `laun_user` VALUES ('22', '2018-05-23 20:14:21', '2018-05-23 20:14:23', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'guowei', null, '0', null);
INSERT INTO `laun_user` VALUES ('23', '2018-05-23 20:39:38', '2018-05-23 20:39:40', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'zhangchong', null, '0', null);
INSERT INTO `laun_user` VALUES ('24', '2018-05-25 10:59:24', '2018-05-25 10:59:31', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'wangchao', null, '0', null);
INSERT INTO `laun_user` VALUES ('25', '2018-05-26 14:25:57', '2018-05-26 14:25:59', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'juanjuan', null, '0', null);
INSERT INTO `laun_user` VALUES ('26', '2018-05-26 18:14:43', '2018-05-26 18:14:45', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'anyao', null, '0', null);
INSERT INTO `laun_user` VALUES ('27', '2018-05-28 11:41:32', '2018-05-28 11:41:34', null, '$2a$10$Ecj7aY66UfvgRmZTjNPyyO1yveRzrEWIJQqBD1jruyw4p5MHEMP2q', null, null, '0', 'll', null, '0', null);
INSERT INTO `laun_user` VALUES ('28', '2018-07-02 14:11:15', '2018-07-02 14:11:15', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test1', null, '2', '备注信息');
INSERT INTO `laun_user` VALUES ('29', '2018-07-03 10:10:41', '2018-07-04 13:50:40', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test2', null, '2', 'test2');
INSERT INTO `laun_user` VALUES ('30', '2018-07-02 17:33:40', '2018-07-03 15:10:55', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test3', null, '2', 'test3');
INSERT INTO `laun_user` VALUES ('31', '2018-05-28 14:26:22', '2018-05-28 14:26:26', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'zhaoxingyan', null, null, '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('32', '2018-05-29 18:16:11', '2018-05-28 16:26:40', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'daiwei', null, '0', '123');
INSERT INTO `laun_user` VALUES ('33', '2018-05-29 10:27:00', '2018-05-29 10:27:02', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'liuxun', null, '0', null);
INSERT INTO `laun_user` VALUES ('34', '2018-06-29 12:33:02', '2018-06-29 12:33:02', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'dawei', null, '0', '1234567');
INSERT INTO `laun_user` VALUES ('36', '2018-05-28 14:26:22', '2018-05-28 14:26:26', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'Mapbar_tester', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('37', '2018-05-28 14:26:22', '2018-05-28 14:26:26', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'Mapbar_developer', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('38', '2018-05-28 14:26:22', '2018-05-28 14:26:26', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'Mapbar_designer', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('39', '2018-05-28 14:26:22', '2018-05-28 14:26:26', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'Mapbar_product', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('40', '2018-06-14 18:29:46', '2018-06-14 18:29:46', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test11', null, null, 'weqwqeqwe');
INSERT INTO `laun_user` VALUES ('41', '2018-06-28 15:14:52', '2018-06-29 14:13:49', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test_aiyo', null, null, '测试账号授权功能');
INSERT INTO `laun_user` VALUES ('42', '2018-06-14 15:30:23', '2018-06-14 15:30:23', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test_shitu', null, null, '渠道B');
INSERT INTO `laun_user` VALUES ('43', '2018-06-28 10:49:52', '2018-06-28 10:49:52', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test_beifen', null, null, '测试账号授权');
INSERT INTO `laun_user` VALUES ('44', '2018-06-14 16:47:15', '2018-06-14 18:43:29', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'test_baobiao', null, null, '测试添加账号授权12');
INSERT INTO `laun_user` VALUES ('45', '2018-05-28 14:26:22', '2018-05-28 14:26:26', '2018-06-27 10:14:38', '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'Mapbar_tester02', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('46', '2018-05-28 14:26:22', '2018-05-28 14:26:26', '2018-06-27 10:14:38', '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'Mapbar_tester03', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('47', '2018-05-28 14:26:22', '2018-05-28 14:26:26', '2018-06-27 10:14:38', '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', '', '', '0', 'Mapbar_tester04', null, '0', '阿斯顿发过火');
INSERT INTO `laun_user` VALUES ('48', '2018-06-28 14:07:52', '2018-06-27 14:07:55', null, '$2a$10$IQ7crn/H13ir4AsWt0DNLOqiGl7S78zDI2gPZSGRYCntGIXeMLbKK', null, null, '0', 'lzp', null, '0', null);
INSERT INTO `laun_user` VALUES ('49', '2018-07-02 14:55:01', '2018-07-02 14:55:03', null, '$2a$10$L56QHd4BUQ1Ena5VGl5TXOD2XePpIm0cUfKJSFH/jh6iNoU9Lnske', null, null, '0', 'jira', null, null, null);

-- ----------------------------
-- Table structure for laun_user_role
-- ----------------------------
DROP TABLE IF EXISTS `laun_user_role`;
CREATE TABLE `laun_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键 ',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户主键 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of laun_user_role
-- ----------------------------
INSERT INTO `laun_user_role` VALUES ('180701356556512', null, null, '180701384434186', '3');
INSERT INTO `laun_user_role` VALUES ('180701377534564', null, null, '180703864057760', '30');
INSERT INTO `laun_user_role` VALUES ('180701928640325', null, null, '180702003089638', '15');
INSERT INTO `laun_user_role` VALUES ('180703450150940', null, null, '180705800702227', '6');
INSERT INTO `laun_user_role` VALUES ('180703895526431', null, null, '180704230639977', '18');
INSERT INTO `laun_user_role` VALUES ('180703919378049', null, null, '180704044937106', '17');
INSERT INTO `laun_user_role` VALUES ('180704184303844', null, null, '180700432528353', '28');
INSERT INTO `laun_user_role` VALUES ('180704409801907', null, null, '180703892374827', '16');
INSERT INTO `laun_user_role` VALUES ('180704435547954', null, null, '180701519926540', '5');
INSERT INTO `laun_user_role` VALUES ('180704775982090', null, null, '180704450300829', '13');
INSERT INTO `laun_user_role` VALUES ('180705024679040', null, null, '180703804230206', '10');
INSERT INTO `laun_user_role` VALUES ('180705162431227', null, null, '180701012347392', '29');
INSERT INTO `laun_user_role` VALUES ('180705821970134', null, null, '180705296521436', '14');

-- ----------------------------
-- Table structure for laun_widget
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget`;
CREATE TABLE `laun_widget` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `category` bigint(20) NOT NULL COMMENT '分类id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `name` varchar(20) NOT NULL COMMENT 'Widget名称',
  `prew_image` varchar(255) DEFAULT NULL COMMENT '图片',
  `default_size` varchar(255) DEFAULT NULL COMMENT '默认尺寸',
  `tag` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `type` int(11) DEFAULT NULL COMMENT 'widget类型 0:基础;1:变体;2:组合;3:组合基础变体',
  `version` varchar(10) NOT NULL COMMENT '版本（key--version：laun版本）',
  `code_id` varchar(255) DEFAULT NULL,
  `gproperty` text COMMENT '外层widget的json',
  `property` text COMMENT 'json中property属性',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '自定义基础变体，自定义变体的父节点，其他类型默认为0',
  `lattice` varchar(255) DEFAULT NULL COMMENT '坐标',
  `root_id` bigint(20) DEFAULT NULL COMMENT '组合变体、组合体的根节点，废弃字段',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',
  `createway` int(2) DEFAULT NULL COMMENT '1:管理员创建 2:渠道创建',
  `channelway` int(1) DEFAULT '1' COMMENT '用于查询widget的使用渠道，默认为1代表全部渠道，0关联laun_widget_channel表',
  `belong_id` bigint(20) DEFAULT NULL COMMENT '自定义变体的所属id,其他类型默认为0',
  `custom_widget_Json` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget实体';

-- ----------------------------
-- Records of laun_widget
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_channel
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_channel`;
CREATE TABLE `laun_widget_channel` (
  `widget_id` bigint(20) NOT NULL,
  `channel_id` bigint(20) NOT NULL,
  PRIMARY KEY (`channel_id`,`widget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员创建的自定义widget和渠道的关联关系表';

-- ----------------------------
-- Records of laun_widget_channel
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_config
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_config`;
CREATE TABLE `laun_widget_config` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `config_name` varchar(255) DEFAULT NULL COMMENT '配置名称',
  `height` varchar(255) DEFAULT NULL COMMENT '纵向高',
  `height_wieghts` varchar(255) DEFAULT NULL COMMENT '纵向高权重',
  `laun_parant_id` bigint(20) DEFAULT NULL COMMENT '父Widget的id',
  `widget_id` bigint(20) DEFAULT NULL COMMENT 'widgetId',
  `width` varchar(255) DEFAULT NULL COMMENT '横向宽',
  `width_to_height` varchar(255) DEFAULT NULL COMMENT '是否锁定宽高比',
  `width_wieghts` varchar(255) DEFAULT NULL COMMENT '横向宽权重',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget配置实体';

-- ----------------------------
-- Records of laun_widget_config
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_file
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_file`;
CREATE TABLE `laun_widget_file` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件原名字',
  `path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `user_id` bigint(20) DEFAULT NULL COMMENT '上传人',
  `widget_id` bigint(20) DEFAULT NULL COMMENT 'widget主键',
  `type` int(2) DEFAULT NULL COMMENT '1:封面 2:压缩包 3:压缩包内图片 4:other',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget文件';

-- ----------------------------
-- Records of laun_widget_file
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_min_property
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_min_property`;
CREATE TABLE `laun_widget_min_property` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `complete_type` varchar(255) DEFAULT NULL COMMENT '是否为同一数组，',
  `data_type` int(11) NOT NULL COMMENT '数据类型 0单一数据，1多个数据， 2代表数组（String），3代表数组（json对象）',
  `name` varchar(255) DEFAULT NULL COMMENT '属性名字',
  `parant_id` bigint(20) DEFAULT NULL COMMENT '父级属性id',
  `value_data` varchar(255) DEFAULT NULL COMMENT '属性值',
  `widget_property_id` bigint(20) DEFAULT NULL COMMENT '父级标示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget最小属性';

-- ----------------------------
-- Records of laun_widget_min_property
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_property
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_property`;
CREATE TABLE `laun_widget_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `widget_id` bigint(20) DEFAULT NULL COMMENT 'Widget主键',
  `widget_property_id` bigint(20) DEFAULT NULL COMMENT 'Widget属性主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget属性';

-- ----------------------------
-- Records of laun_widget_property
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_statistics
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_statistics`;
CREATE TABLE `laun_widget_statistics` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `application_id` bigint(20) DEFAULT NULL COMMENT '应用ID',
  `application_name` varchar(255) DEFAULT NULL COMMENT '应用名称',
  `car_num` bigint(20) DEFAULT NULL COMMENT '累计车辆',
  `channel_id` bigint(20) DEFAULT NULL COMMENT '渠道id',
  `start_up_num` bigint(20) DEFAULT NULL COMMENT '启动次数',
  `version` varchar(255) DEFAULT NULL COMMENT '版本',
  `widget_time` datetime DEFAULT NULL COMMENT '日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of laun_widget_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_type
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_type`;
CREATE TABLE `laun_widget_type` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `type_name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget分类';

-- ----------------------------
-- Records of laun_widget_type
-- ----------------------------

-- ----------------------------
-- Table structure for laun_widget_width_to_hight
-- ----------------------------
DROP TABLE IF EXISTS `laun_widget_width_to_hight`;
CREATE TABLE `laun_widget_width_to_hight` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `align` varchar(255) DEFAULT NULL COMMENT '对齐方式(0;左对齐于目标右侧…)',
  `base_align` varchar(255) DEFAULT NULL COMMENT '基于xx对齐(对齐于widget实体中的alignment字段)',
  `distance` bigint(20) DEFAULT NULL COMMENT '边距',
  `type` int(11) NOT NULL COMMENT '类型1:横向2:纵向',
  `widget_config_id` bigint(20) DEFAULT NULL COMMENT 'widgetId',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Widget添加对齐方式';

-- ----------------------------
-- Records of laun_widget_width_to_hight
-- ----------------------------
