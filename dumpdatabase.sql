-- MySQL dump 10.13  Distrib 8.0.32, for macos13.0 (arm64)
--
-- Host: 54.36.16.110    Database: default_rat23
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allergens`
--

DROP TABLE IF EXISTS `allergens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allergens` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergens`
--

LOCK TABLES `allergens` WRITE;
/*!40000 ALTER TABLE `allergens` DISABLE KEYS */;
INSERT INTO `allergens` VALUES (1,'glutine'),(2,'lattosio'),(3,'arachidi e derivati'),(4,'frutta a guscio'),(5,'molluschi'),(6,'pesce'),(7,'sesamo'),(8,'soia'),(9,'crostacei'),(10,'lupini'),(11,'senape'),(12,'sedano'),(13,'anidride solforosa e solfiti'),(14,'uova'),(62,'mandorla');
/*!40000 ALTER TABLE `allergens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `bill_view`
--

DROP TABLE IF EXISTS `bill_view`;
/*!50001 DROP VIEW IF EXISTS `bill_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bill_view` AS SELECT 
 1 AS `id`,
 1 AS `cart_id`,
 1 AS `total`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_id` bigint(20) NOT NULL,
  `status` enum('OPEN','CLOSED') NOT NULL DEFAULT 'OPEN',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `fk_order_table_idx` (`table_id`),
  CONSTRAINT `fk_order_table` FOREIGN KEY (`table_id`) REFERENCES `table_restaurant` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (120,111,'OPEN','2023-02-26 11:35:41','2023-03-09 23:08:12'),(132,112,'CLOSED','2023-02-27 21:47:13','2023-02-28 14:39:18'),(224,110,'CLOSED','2023-03-09 23:16:20','2023-03-10 08:59:21');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_dish`
--

DROP TABLE IF EXISTS `cart_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_dish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cart_id` bigint(20) NOT NULL,
  `dish_id` bigint(20) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `update_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_dish_order_id` (`cart_id`),
  KEY `fk_order_dish_dish_id` (`dish_id`),
  KEY `fk_order_dish_user` (`user_id`),
  CONSTRAINT `fk_order_dish_dish_id` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_order_dish_order_id` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_order_dish_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_dish`
--

LOCK TABLES `cart_dish` WRITE;
/*!40000 ALTER TABLE `cart_dish` DISABLE KEYS */;
INSERT INTO `cart_dish` VALUES (225,224,200,'2023-03-09 23:16:20','2023-03-09 23:16:20',107),(226,224,202,'2023-03-09 23:16:20','2023-03-09 23:16:20',107);
/*!40000 ALTER TABLE `cart_dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `priority` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (166,'Primo piatto',0),(167,'Bibite',2),(168,'Dolci',3),(169,'Frittura',0),(217,'Antipasto',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `cost` decimal(10,2) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `menu_id` bigint(20) NOT NULL,
  `description_lan` varchar(255) DEFAULT NULL,
  `name_lan` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dish_menu` (`menu_id`),
  KEY `fk_dish_category_id` (`category_id`),
  CONSTRAINT `fk_dish_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_dish_menu` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (187,'Babà al caffè','caffe, rum, farina, uova, burro',10.00,168,109,'delicious','Coffee Babà'),(190,'Torta pan di stelle','cacao, farina, uova, zucchero, miele, biscotti, panna, nutella, nocciole',50.00,168,109,NULL,NULL),(195,'Spaghetti al nero di seppia','seppia, pasta, olio, sale, pepe',15.00,166,109,NULL,NULL),(197,'Coca Cola Zero','caffeina, zuccheri',3.00,167,109,NULL,NULL),(199,'Acqua minerale naturale Sant\'Anna','acqua',2.00,167,109,NULL,NULL),(200,'Paccheri napoletani','ragù, pasta, olio, sale, pepe',15.00,166,109,NULL,NULL),(202,'Pasta e patete','',10.00,166,109,NULL,NULL),(203,'Crostata di mele','farina, mele, zucchero, olio',25.00,168,109,NULL,NULL),(204,'Gamberi e calamari','pesce, uova, sale, pepe',15.00,169,109,NULL,NULL),(210,'Paccheri allo scoglio','pasta, misto di mare, olio, sale, pepe',15.00,166,109,NULL,NULL),(218,'Bruschetta ai funghi','pane, funghi, olio, sale, pepe',5.00,217,109,NULL,NULL),(220,'Gnocchi alla sorrentina','gnocchi, patate, pomodoro',13.00,166,109,'dumplings, potato, tomato','Sorrentina\'s dumplings '),(223,'Alette di pollo','pollo, rosmarino, sale',7.00,169,109,'chicken, rosemary, salt','Chicken wings'),(231,'pizza con l\'ananas','ananas ben cotta di Pozzuoli',9.50,166,230,NULL,NULL);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_allergens`
--

DROP TABLE IF EXISTS `dish_allergens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish_allergens` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dish_id` bigint(20) NOT NULL,
  `allergen_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dish_allergens_dish` (`dish_id`),
  KEY `fk_dish_allergens_allergens` (`allergen_id`),
  CONSTRAINT `fk_dish_allergens_allergens` FOREIGN KEY (`allergen_id`) REFERENCES `allergens` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_dish_allergens_dish` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_allergens`
--

LOCK TABLES `dish_allergens` WRITE;
/*!40000 ALTER TABLE `dish_allergens` DISABLE KEYS */;
INSERT INTO `dish_allergens` VALUES (188,187,1),(189,187,2),(191,190,1),(192,190,2),(193,190,3),(194,190,4),(196,195,4),(198,197,13),(201,200,1),(203,202,1),(211,210,1),(212,210,5),(219,218,1),(221,220,1),(222,220,2),(232,231,12),(233,231,14);
/*!40000 ALTER TABLE `dish_allergens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (234);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_restaurant_id` (`restaurant_id`),
  CONSTRAINT `fk_menu_restaurant` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (109,108),(230,229);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (108,'quel fottuto ristorante','Via marco polo','2023-02-24 16:36:46','2023-02-24 16:36:46'),(229,'Di capri forever','Via Roma','2023-03-10 14:35:16','2023-03-10 14:35:16');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_restaurant`
--

DROP TABLE IF EXISTS `table_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table_restaurant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seats` bigint(20) NOT NULL,
  `restaurant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `table_restaurant_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_restaurant`
--

LOCK TABLES `table_restaurant` WRITE;
/*!40000 ALTER TABLE `table_restaurant` DISABLE KEYS */;
INSERT INTO `table_restaurant` VALUES (110,4,108),(111,8,108),(112,8,108),(227,8,108);
/*!40000 ALTER TABLE `table_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT 0,
  `first_access` tinyint(1) DEFAULT 0,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `restaurant_id` bigint(20) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `surname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_unique` (`email`),
  KEY `fk_user_restaurant` (`restaurant_id`),
  CONSTRAINT `fk_user_restaurant` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (107,'fagibic549@wireps.com',1,1,'giacomino','$2a$10$dRaF2mQbuBrWUskECMT.cOGUhgnoFnRNy7hy74A6U3tE3Hza7WO6y',108,'ADMIN','dinamo'),(173,'giacomino@gmail.com',1,1,'marco','$2a$10$qA/CNs.ESsdiGIyrMJ0kQeXt8U7d.rHsWzHNiBGlgWX1xD0WfNeeS',108,'WAITER','merrino'),(213,'mojer94526@terkoer.com',0,1,'franco','$2a$10$zXPgHIQzaF1GZM.90O16nu.O4LpuakebJrVXxdrTnJELrCra.HlHu',NULL,'ADMIN','cavaluccio'),(214,'ciaocom@gmail.com',0,1,'franco','$2a$10$5Vmrw41FEmF872I7jG.AAu/JNGXA1wGSG4JadKcCGf3LrRAI/4AYC',NULL,'ADMIN','cavaluccio'),(216,'giacominofedele@gmail.com',0,1,'franco','$2a$10$sjPwwFNPhcsyjTGXs28Bgu0FdQt7yT1yrZaa57o6OZK5yuldxyK.q',NULL,'ADMIN','cavaluccio'),(228,'dicapri@gmail.com',1,1,'peppino','$2a$10$RAS873t2k09UL3KYCkvFYOf3h1zMgxmSb4kCkYW0mpu8A0jtmWUQG',229,'ADMIN','dicapri');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `bill_view`
--

/*!50001 DROP VIEW IF EXISTS `bill_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`devremote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `bill_view` AS select row_number() over ( order by `c`.`id`) AS `id`,`c`.`id` AS `cart_id`,sum(`d`.`cost`) AS `total` from ((`cart` `c` join `cart_dish` `cd` on(`c`.`id` = `cd`.`cart_id`)) join `dish` `d` on(`cd`.`dish_id` = `d`.`id`)) where `c`.`status` = 'CLOSED' group by `c`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-11 19:59:41
