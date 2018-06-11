-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bibliotecaoose
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `opera`
--

DROP TABLE IF EXISTS `opera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `opera` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `titolo` varchar(45) NOT NULL,
  `autore` varchar(45) NOT NULL,
  `genere` varchar(45) NOT NULL,
  `ISBN` varchar(45) NOT NULL,
  `anno_pubb` int(10) unsigned NOT NULL,
  `lingua` varchar(45) NOT NULL,
  `IDstato` int(10) unsigned NOT NULL,
  `IDuploader` int(10) unsigned NOT NULL,
  `pathCopertina` varchar(700) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `stato_operafk_idx` (`IDstato`),
  CONSTRAINT `stato_operafk` FOREIGN KEY (`IDstato`) REFERENCES `stato opera` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opera`
--

LOCK TABLES `opera` WRITE;
/*!40000 ALTER TABLE `opera` DISABLE KEYS */;
INSERT INTO `opera` VALUES (22,'Il Piccolo Principe','Antoine de Saint-Exupéry','Romanzo','DR3464737',1943,'Italiano',2,17,'C:\\Users\\angel\\Desktop\\Opere\\Il Piccolo Principe\\Copertina.jpg'),(23,'Il Fu Mattia Pascal','Pirandello','Romanzo','DR647362',1904,'Italiano',2,17,'C:\\Users\\angel/Desktop/Opere/Il Fu Mattia Pascal/Copertina.jpg');
/*!40000 ALTER TABLE `opera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagina`
--

DROP TABLE IF EXISTS `pagina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagina` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `numero_pagina` int(10) unsigned NOT NULL,
  `IDopera` int(10) unsigned NOT NULL,
  `IDstatopag` int(10) unsigned NOT NULL,
  `directorypagepath` varchar(800) NOT NULL,
  `directorytrascrpath` varchar(800) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_pag` (`IDopera`,`numero_pagina`),
  KEY `stato_paginafk_idx` (`IDstatopag`),
  CONSTRAINT `stato_paginafk` FOREIGN KEY (`IDstatopag`) REFERENCES `statopagina` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagina`
--

LOCK TABLES `pagina` WRITE;
/*!40000 ALTER TABLE `pagina` DISABLE KEYS */;
INSERT INTO `pagina` VALUES (25,1,22,2,'C:\\Users\\angel\\Desktop\\Opere\\Il Piccolo Principe\\Pagina1.jpg','C:\\Users\\angel\\Desktop\\Opere\\Il Piccolo Principe\\Trascrizione1.txt'),(26,1,23,2,'C:\\Users\\angel\\Desktop\\Opere\\Il Fu Mattia Pascal\\Pagina1.jpg','C:\\Users\\angel\\Desktop\\Opere\\Il Fu Mattia Pascal\\Trascrizione1.txt');
/*!40000 ALTER TABLE `pagina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `richiesta`
--

DROP TABLE IF EXISTS `richiesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `richiesta` (
  `ID` int(10) unsigned NOT NULL,
  `stato_richiesta` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `stato_richiesta_UNIQUE` (`stato_richiesta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `richiesta`
--

LOCK TABLES `richiesta` WRITE;
/*!40000 ALTER TABLE `richiesta` DISABLE KEYS */;
INSERT INTO `richiesta` VALUES (2,'Accettata'),(1,'Attesa di essere accetta'),(3,'Nessuna richiesta');
/*!40000 ALTER TABLE `richiesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruolo`
--

DROP TABLE IF EXISTS `ruolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ruolo` (
  `ID` int(10) unsigned NOT NULL,
  `tipo_ruolo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruolo`
--

LOCK TABLES `ruolo` WRITE;
/*!40000 ALTER TABLE `ruolo` DISABLE KEYS */;
INSERT INTO `ruolo` VALUES (1,'Utente base'),(2,'Uploader'),(3,'Trascrittore'),(4,'Revisore'),(5,'Admin'),(6,'Manager'),(7,'Utente privilegiato');
/*!40000 ALTER TABLE `ruolo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stato opera`
--

DROP TABLE IF EXISTS `stato opera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stato opera` (
  `ID` int(10) unsigned NOT NULL,
  `stato opera` varchar(300) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stato opera`
--

LOCK TABLES `stato opera` WRITE;
/*!40000 ALTER TABLE `stato opera` DISABLE KEYS */;
INSERT INTO `stato opera` VALUES (1,'Inserita'),(2,'Pubblicata'),(3,'Upload chiuso');
/*!40000 ALTER TABLE `stato opera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stato_trascrizione`
--

DROP TABLE IF EXISTS `stato_trascrizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stato_trascrizione` (
  `ID` int(10) unsigned NOT NULL,
  `stato trascrizione` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stato_trascrizione`
--

LOCK TABLES `stato_trascrizione` WRITE;
/*!40000 ALTER TABLE `stato_trascrizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `stato_trascrizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statopagina`
--

DROP TABLE IF EXISTS `statopagina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statopagina` (
  `ID` int(10) unsigned NOT NULL,
  `stato_pagina` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statopagina`
--

LOCK TABLES `statopagina` WRITE;
/*!40000 ALTER TABLE `statopagina` DISABLE KEYS */;
INSERT INTO `statopagina` VALUES (1,'attesa di revisione'),(2,'verificata'),(3,'respinta');
/*!40000 ALTER TABLE `statopagina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trascrizione`
--

DROP TABLE IF EXISTS `trascrizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trascrizione` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IDuploader` int(30) unsigned NOT NULL,
  `IDPagina` int(30) unsigned NOT NULL,
  `stato_trascrizione` int(30) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `idup_fk_idx` (`IDuploader`),
  KEY `idpage_fk_idx` (`IDPagina`),
  CONSTRAINT `idpage_fk` FOREIGN KEY (`IDPagina`) REFERENCES `pagina` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idup_fk` FOREIGN KEY (`IDuploader`) REFERENCES `utente` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trascrizione`
--

LOCK TABLES `trascrizione` WRITE;
/*!40000 ALTER TABLE `trascrizione` DISABLE KEYS */;
INSERT INTO `trascrizione` VALUES (20,18,25,1),(21,18,26,1);
/*!40000 ALTER TABLE `trascrizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(50) NOT NULL,
  `psswrd` varchar(24) NOT NULL,
  `IDruolo` int(10) unsigned NOT NULL,
  `IDrichiesta` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_user` (`username`),
  KEY `tipo_ruolo` (`IDruolo`),
  KEY `tipo_req_idx` (`IDrichiesta`),
  CONSTRAINT `tipo_req` FOREIGN KEY (`IDrichiesta`) REFERENCES `richiesta` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tipo_ruolo` FOREIGN KEY (`IDruolo`) REFERENCES `ruolo` (`ID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (16,'admin','admin','admin','admin@prova.it','admin',5,3),(17,'uploader1','uploader','uploader1','uploader@prova.it','uploader1',2,3),(18,'trascrittore1','trascrittore','trascrittore1','trascrittore@prova.it','trascrittore1',3,3),(19,'revisore1','revisore','revisore1','revisore@prova.it','revisore1',4,3),(20,'manager1','manager','manager1','manager@prova.it','manager1',6,3),(21,'utente base','utente base','utentebase1','utentebase@prova.it','utentebase1',7,2),(23,'utente base','utente base','utentebase2','utentebase2@test.it','utentebase2',1,3);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-09 18:49:07
