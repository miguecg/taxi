/*
 Navicat Premium Backup

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : taxis

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 06/02/2016 12:07:18 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `PEDIDO`
-- ----------------------------
DROP TABLE IF EXISTS `PEDIDO`;
CREATE TABLE `PEDIDO` (
  `PEDI_PEDIDO` int(11) NOT NULL AUTO_INCREMENT,
  `PEDI_ESTATUS` varchar(1) NOT NULL DEFAULT 'P',
  `PEDI_FECHA` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PEDI_LATITUD` double NOT NULL,
  `PEDI_LONGITUD` double NOT NULL,
  `PEDI_MONTO` float NOT NULL DEFAULT '0',
  `PEDI_FECHAPAGO` datetime DEFAULT NULL,
  `PEDI_TAXI` int(11) DEFAULT NULL,
  `PEDI_USUARIO` int(11) NOT NULL,
  PRIMARY KEY (`PEDI_PEDIDO`),
  KEY `PEDI_TAXI` (`PEDI_TAXI`),
  KEY `PEDI_USUARIO` (`PEDI_USUARIO`),
  CONSTRAINT `FK_PEDIDO_TAXI` FOREIGN KEY (`PEDI_TAXI`) REFERENCES `TAXI` (`TAXI_TAXI`),
  CONSTRAINT `FK_PEDIDO_USUARIO` FOREIGN KEY (`PEDI_USUARIO`) REFERENCES `USUARIO` (`USUA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `PEDIDO`
-- ----------------------------
BEGIN;
INSERT INTO `PEDIDO` VALUES ('5', 'P', '2016-06-02 10:57:18', '19.690484899999998', '-101.2028663', '0', null, null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `RECORRIDO`
-- ----------------------------
DROP TABLE IF EXISTS `RECORRIDO`;
CREATE TABLE `RECORRIDO` (
  `RECO_RECORRIDO` int(11) NOT NULL AUTO_INCREMENT,
  `RECO_FECHA` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `RECO_LATITUD` double NOT NULL DEFAULT '0',
  `RECO_LONGITUD` double NOT NULL,
  `RECO_PEDIDO` int(11) NOT NULL,
  `RECO_TRECORRIDO` varchar(1) NOT NULL,
  PRIMARY KEY (`RECO_RECORRIDO`),
  KEY `RECO_PEDIDO` (`RECO_PEDIDO`),
  CONSTRAINT `FK_PED_PEDIDO` FOREIGN KEY (`RECO_PEDIDO`) REFERENCES `PEDIDO` (`PEDI_PEDIDO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ROL`
-- ----------------------------
DROP TABLE IF EXISTS `ROL`;
CREATE TABLE `ROL` (
  `ROLE_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_DESCRIP` varchar(32) NOT NULL,
  PRIMARY KEY (`ROLE_ROLE`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ROL`
-- ----------------------------
BEGIN;
INSERT INTO `ROL` VALUES ('1', 'ADMIN'), ('2', 'CONDUCTOR'), ('3', 'OPERADOR'), ('4', 'CLIENTE');
COMMIT;

-- ----------------------------
--  Table structure for `TAXI`
-- ----------------------------
DROP TABLE IF EXISTS `TAXI`;
CREATE TABLE `TAXI` (
  `TAXI_TAXI` int(11) NOT NULL AUTO_INCREMENT,
  `TAXI_MODELO` int(11) NOT NULL,
  `TAXI_PLACA` varchar(10) NOT NULL,
  `TAXI_OCUPANTES` int(11) NOT NULL DEFAULT '4',
  `TAXI_TIPOTAXI` int(11) NOT NULL,
  `TAXI_DESCRIP` text,
  `TAXI_ESTATUS` varchar(1) NOT NULL DEFAULT 'A',
  `TAXI_USUARIO` int(11) DEFAULT '0',
  `TAXI_MARCA` varchar(50) NOT NULL,
  PRIMARY KEY (`TAXI_TAXI`),
  KEY `TAXI_TIPOTAXI` (`TAXI_TIPOTAXI`),
  KEY `TAXI_USUARIO` (`TAXI_USUARIO`),
  CONSTRAINT `FK_TAXI_USUARIO` FOREIGN KEY (`TAXI_USUARIO`) REFERENCES `USUARIO` (`USUA_ID`),
  CONSTRAINT `FK_TIPO_TAXI` FOREIGN KEY (`TAXI_TIPOTAXI`) REFERENCES `TIPOTAXI` (`TIPT_TIPOTAXI`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `TAXI`
-- ----------------------------
BEGIN;
INSERT INTO `TAXI` VALUES ('1', '2000', '2384PLS', '4', '1', 'Buen estado', 'A', '1', 'NISSAN');
COMMIT;

-- ----------------------------
--  Table structure for `TIPOTAXI`
-- ----------------------------
DROP TABLE IF EXISTS `TIPOTAXI`;
CREATE TABLE `TIPOTAXI` (
  `TIPT_TIPOTAXI` int(11) NOT NULL AUTO_INCREMENT,
  `TIPT_PRECIO` float NOT NULL DEFAULT '0',
  `TIPT_DESCRIP` text,
  `TIPT_TEXTO` text,
  PRIMARY KEY (`TIPT_TIPOTAXI`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `TIPOTAXI`
-- ----------------------------
BEGIN;
INSERT INTO `TIPOTAXI` VALUES ('1', '10', 'Taxi convencional', 'Taxi convencional'), ('2', '20', 'Camioneta Crossover', 'Mediano lujo'), ('3', '30', 'Camioneta Crossover', 'De lujo');
COMMIT;

-- ----------------------------
--  Table structure for `USROL`
-- ----------------------------
DROP TABLE IF EXISTS `USROL`;
CREATE TABLE `USROL` (
  `USRO_ROL` int(11) NOT NULL,
  `USRO_USUID` int(11) NOT NULL,
  KEY `USRO_ROL` (`USRO_ROL`),
  KEY `USRO_USUID` (`USRO_USUID`),
  CONSTRAINT `FK_ROLE_ROL` FOREIGN KEY (`USRO_ROL`) REFERENCES `ROL` (`ROLE_ROLE`),
  CONSTRAINT `FK_USUID_USUARIO` FOREIGN KEY (`USRO_USUID`) REFERENCES `USUARIO` (`USUA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `USROL`
-- ----------------------------
BEGIN;
INSERT INTO `USROL` VALUES ('4', '1');
COMMIT;

-- ----------------------------
--  Table structure for `USUARIO`
-- ----------------------------
DROP TABLE IF EXISTS `USUARIO`;
CREATE TABLE `USUARIO` (
  `USUA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USUA_USUARIO` varchar(32) NOT NULL,
  `USUA_NOMBRE` varchar(50) NOT NULL,
  `USUA_APEPAT` varchar(50) NOT NULL,
  `USUA_APEMAT` varchar(50) DEFAULT NULL,
  `USUA_EMAIL` varchar(100) NOT NULL,
  `USUA_TELEFONO` int(11) NOT NULL,
  `USUA_PASSWORD` varchar(32) NOT NULL,
  `USUA_ESTATUS` varchar(1) DEFAULT 'A',
  PRIMARY KEY (`USUA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `USUARIO`
-- ----------------------------
BEGIN;
INSERT INTO `USUARIO` VALUES ('1', 'miguel', 'Miguel Angel', 'Cedeño', 'Garcidueñas', 'miguecg@gmail.com', '28281111', 'e201994dca9320fc94336603b1cfc970', 'A'), ('2', 'paco', 'Francisco', 'Castro', 'Perez', 'pacorro32@hotmail.com', '423432233', '25d55ad283aa400af464c76d713c07ad', 'A'), ('3', 'memo', 'Guillermo', 'Rojas', 'Sandoval', 'memo', '45454545', '25d55ad283aa400af464c76d713c07ad', 'A'), ('4', 'ilse', 'Ilse', 'Lopez', 'Garcia', 'ilse352@gmail.com', '32818282', '25d55ad283aa400af464c76d713c07ad', 'A');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
