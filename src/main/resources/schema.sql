/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50625
 Source Host           : localhost
 Source Database       : test

 Target Server Type    : MySQL
 Target Server Version : 50625
 File Encoding         : utf-8

 Date: 09/04/2017 07:39:05 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `permission_name` varchar(100) NOT NULL COMMENT '功能名称',
  `permission_code` varchar(100) NOT NULL COMMENT '功能代码',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '功能状态',
  `url` varchar(200) DEFAULT NULL,
  `pid` varchar(32) NOT NULL COMMENT '父ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `permission`
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES ('0b20b9bc8fe011e7910d492bfb691d54', '编辑', 'permission:edit', '0', '/api/permission', 'fe7d737e8ebf11e7910d492bfb691d54'), ('0d68327e8eb711e7910d492bfb691d54', '系统管理', 'admin:manage', '0', '', '1e768edc8e1011e7910d492bfb691d54'), ('195823f88fe011e7910d492bfb691d54', '删除', 'permission:delete', '0', '/api/permission/del/', 'fe7d737e8ebf11e7910d492bfb691d54'), ('1e768edc8e1011e7910d492bfb691d54', '菜单功能管理', 'code', '0', '/api/permission', '0'), ('26a89ad28ebf11e7910d492bfb691d54', '用户管理', 'user:manage', '0', '/system/user', '0d68327e8eb711e7910d492bfb691d54'), ('4bb48c828ebf11e7910d492bfb691d54', '角色管理', 'role:manage', '0', '/system/role', '0d68327e8eb711e7910d492bfb691d54'), ('85d2dd928ebf11e7910d492bfb691d54', '查询', 'user:query', '0', '/api/user/list', '26a89ad28ebf11e7910d492bfb691d54'), ('a097645e8ebf11e7910d492bfb691d54', '添加', 'user:add', '0', '/api/user', '26a89ad28ebf11e7910d492bfb691d54'), ('aba0bc248ebf11e7910d492bfb691d54', '编辑', 'user:edit', '0', '/api/user', '26a89ad28ebf11e7910d492bfb691d54'), ('b0b305a28fdf11e7910d492bfb691d54', '设置功能', 'rule:setpermisson', '0', '/api/role/setpermission/', '4bb48c828ebf11e7910d492bfb691d54'), ('c47d29788fdf11e7910d492bfb691d54', '编辑', 'role:edit', '0', '/api/role', '4bb48c828ebf11e7910d492bfb691d54'), ('cd69f5788ebf11e7910d492bfb691d54', '设置角色', 'user:setrole', '0', '/api/user/setrole/', '26a89ad28ebf11e7910d492bfb691d54'), ('d6b16a7e8ed511e7910d492bfb691d54', '添加', 'role:add', '0', '/api/role', '4bb48c828ebf11e7910d492bfb691d54'), ('dfd180528fdf11e7910d492bfb691d54', '新增节点', 'permission:add', '0', '/api/permission', 'fe7d737e8ebf11e7910d492bfb691d54'), ('e184059e8ebf11e7910d492bfb691d54', '删除', 'user:delete', '0', '/api/user/del/', '26a89ad28ebf11e7910d492bfb691d54'), ('e606c2b28ed511e7910d492bfb691d54', '删除', 'role:delete', '0', '/api/role/del', '4bb48c828ebf11e7910d492bfb691d54'), ('f4734c4e8ed511e7910d492bfb691d54', '查询', 'role:query', '0', '/api/role/list', '4bb48c828ebf11e7910d492bfb691d54'), ('fd38b2328fdf11e7910d492bfb691d54', '查询', 'permission:query', '0', '/api/permission/list', 'fe7d737e8ebf11e7910d492bfb691d54'), ('fe7d737e8ebf11e7910d492bfb691d54', '功能管理', 'permission:manage', '0', '/system/permission', '0d68327e8eb711e7910d492bfb691d54');
COMMIT;

-- ----------------------------
--  Table structure for `permission_role`
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `permission_id` varchar(32) NOT NULL COMMENT '功能主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`permission_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `permission_role`
-- ----------------------------
BEGIN;
INSERT INTO `permission_role` VALUES ('0d68327e8eb711e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('0d68327e8eb711e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('1e768edc8e1011e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('1e768edc8e1011e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('26a89ad28ebf11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('26a89ad28ebf11e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('4bb48c828ebf11e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('85d2dd928ebf11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('85d2dd928ebf11e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('a097645e8ebf11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('aba0bc248ebf11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('cd69f5788ebf11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('e184059e8ebf11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('f4734c4e8ed511e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('fd38b2328fdf11e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54'), ('fe7d737e8ebf11e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(32) NOT NULL,
  `rolename` varchar(100) NOT NULL COMMENT '角色名',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '角色状态',
  `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('55c084888c7b11e7910d492bfb691d54', '管理员', '0', '管理所有的功能'), ('90e88c548c7b11e7910d492bfb691d54', '小角色', '0', '只是一个小角色');
COMMIT;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `test`
-- ----------------------------
BEGIN;
INSERT INTO `test` VALUES ('1', 'test');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '员工编号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '员工名称',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '用户状态 0 正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('76cc80c0906b11e7910d492bfb691d54', 'AB002', '123456', '测试用户', '0'), ('ad1fdfac8c6811e7910d492bfb691d54', 'AB003', '123456', '测试用户0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '用户主键',
  `role_id` varchar(32) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user_role`
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES ('76cc80c0906b11e7910d492bfb691d54', '55c084888c7b11e7910d492bfb691d54'), ('ad1fdfac8c6811e7910d492bfb691d54', '90e88c548c7b11e7910d492bfb691d54');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
