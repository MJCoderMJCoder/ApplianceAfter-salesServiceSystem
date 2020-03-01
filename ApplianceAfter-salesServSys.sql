CREATE DATABASE  IF NOT EXISTS `applianceafter-salesservsys` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `applianceafter-salesservsys`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: applianceafter-salesservsys
-- ------------------------------------------------------
-- Server version	5.7.29-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appliance`
--

DROP TABLE IF EXISTS `appliance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appliance` (
  `appliance_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '家电ID',
  `appliance_name` varchar(45) NOT NULL COMMENT '家电名称',
  `appliance_model` varchar(45) NOT NULL COMMENT '家电型号',
  `appliance_part` varchar(45) NOT NULL COMMENT '家电零件名称',
  `appliance_price` float NOT NULL COMMENT '家电价格',
  `appliance_arg` varchar(200) NOT NULL COMMENT '家电参数',
  UNIQUE KEY `appliance_id_UNIQUE` (`appliance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='家电表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appliance`
--

LOCK TABLES `appliance` WRITE;
/*!40000 ALTER TABLE `appliance` DISABLE KEYS */;
INSERT INTO `appliance` VALUES (2,'大喊大叫?@','五花大绑#@*@','四级四级??*',441.898,'等级低等级\n即小见大ejf?*977#*???\n?E JDK'),(3,'华盛顿','但不得不','大部分',4.5,'，#*???????☝\n????☝??\n??');
/*!40000 ALTER TABLE `appliance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintainer`
--

DROP TABLE IF EXISTS `maintainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintainer` (
  `maintainer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修工ID',
  `maintainer_name` varchar(45) NOT NULL COMMENT '维修工名称',
  `maintainer_phone` varchar(45) NOT NULL COMMENT '维修工手机',
  `maintainer_gender` varchar(4) NOT NULL COMMENT '维修工性别',
  `maintainer_address` varchar(200) NOT NULL COMMENT '维修工地址',
  `maintainer_pwd` varchar(45) NOT NULL,
  PRIMARY KEY (`maintainer_id`),
  UNIQUE KEY `user_id_UNIQUE` (`maintainer_id`),
  UNIQUE KEY `maintainer_phone_UNIQUE` (`maintainer_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='维修工/维修人员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintainer`
--

LOCK TABLES `maintainer` WRITE;
/*!40000 ALTER TABLE `maintainer` DISABLE KEYS */;
INSERT INTO `maintainer` VALUES (1,'纸纷飞','18334706003','男','山西省太原市迎泽区五龙口街民营经济园区新和路9号','598157378'),(7,'hi是基督教','18334701234','女','eg CH的话是否，*@#*#????','qwe');
/*!40000 ALTER TABLE `maintainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance`
--

DROP TABLE IF EXISTS `maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance` (
  `maintenance_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修申请ID',
  `maintenance_name` varchar(45) NOT NULL COMMENT '维修申请名称',
  `maintenance_address` varchar(200) NOT NULL COMMENT '维修申请地址',
  `maintenance_appliance` varchar(200) NOT NULL COMMENT '维修申请的家电信息',
  `maintenance_user` int(11) NOT NULL COMMENT '维修申请的发起用户ID',
  `maintenance_status` int(2) NOT NULL COMMENT '0-未接取【用户】/可接取【维修工】\\\\n1-已接取【用户】/未完成【维修工】\\\\n3-已取消\\\\n4-已完成\\\\n',
  `maintenance_maintainer` int(11) DEFAULT NULL COMMENT '接取维修申请的维修工ID',
  PRIMARY KEY (`maintenance_id`),
  UNIQUE KEY `maintenance_request_id_UNIQUE` (`maintenance_id`),
  KEY `maintenance_user_idx` (`maintenance_user`),
  KEY `maintenance_maintainer_foreign_idx` (`maintenance_maintainer`),
  CONSTRAINT `maintenance_maintainer_foreign` FOREIGN KEY (`maintenance_maintainer`) REFERENCES `maintainer` (`maintainer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `maintenance_user_foreign` FOREIGN KEY (`maintenance_user`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='维修申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance`
--

LOCK TABLES `maintenance` WRITE;
/*!40000 ALTER TABLE `maintenance` DISABLE KEYS */;
INSERT INTO `maintenance` VALUES (2,'da','饿啊','dfa',1,1,NULL),(3,'简述喊??@…???@','end低俗小说地址 ID?????❞????@\\♥♠♥♣????','?♠?♥?♥?零件??…((\"????????；??',2,4,1),(4,'俄呵呵呵','大家都觉得','世界是平的和',2,4,1),(5,'我要修电脑，usb接口不灵敏','山西省晋中市榆次区','联想\n拯救者\ny9000',2,1,1);
/*!40000 ALTER TABLE `maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
  `maintenance` int(11) NOT NULL COMMENT '维修申请的ID',
  `user` int(11) DEFAULT NULL COMMENT '留言发起人ID',
  `user_name` varchar(45) DEFAULT NULL COMMENT '留言发起人名称',
  `maintainer` int(11) DEFAULT NULL COMMENT '回复给某人的ID',
  `maintainer_name` varchar(45) DEFAULT NULL COMMENT '回复给某人的名称',
  `reply_message` int(11) DEFAULT NULL COMMENT '回复那条信息',
  `message_content` varchar(200) NOT NULL COMMENT '留言内容',
  PRIMARY KEY (`message_id`),
  UNIQUE KEY `leave_words_id_UNIQUE` (`message_id`),
  KEY `maintenance_foreign_idx` (`maintenance`),
  CONSTRAINT `maintenance_foreign` FOREIGN KEY (`maintenance`) REFERENCES `maintenance` (`maintenance_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='维修人员和维修申请关联表/维修人员和用户留言关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (3,2,1,'1',1,'1',NULL,'1'),(4,2,NULL,NULL,1,'1',NULL,'1'),(8,4,2,'新年',-1,'',-1,'大呼大喊的'),(9,4,2,'新年',-1,'',8,'大呼大喊的广告'),(10,4,2,'新年',-1,'',9,'SSD'),(11,4,-1,'',1,'纸纷飞',10,'文革'),(12,4,-1,'',1,'纸纷飞',11,'得到'),(13,4,-1,'',1,'纸纷飞',12,'谢谢'),(14,4,-1,'',1,'纸纷飞',13,'小姐小白'),(15,3,2,'新年',-1,'',-1,'SHSH'),(16,3,2,'新年',-1,'',15,'…?,???'),(17,3,2,'新年',-1,'',16,'…?,???'),(18,4,-1,'',1,'纸纷飞',14,'顶顶顶'),(19,3,-1,'',1,'纸纷飞',17,'很多'),(20,4,-1,'',1,'纸纷飞',18,'除非');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(45) NOT NULL COMMENT '用户名称',
  `user_phone` varchar(45) NOT NULL COMMENT '用户手机',
  `user_gender` varchar(4) NOT NULL COMMENT '用户性别',
  `user_address` varchar(200) NOT NULL COMMENT '用户地址',
  `user_pwd` varchar(45) NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_phone_UNIQUE` (`user_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'电话??','14644','女','Ed DD#??###@','123'),(2,'新年','18334703470','男','上班时间\n?@??#4#6…；…*#(~,\\\">♣♥♠〈‘❞','3470');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'applianceafter-salesservsys'
--

--
-- Dumping routines for database 'applianceafter-salesservsys'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-01  9:10:08
