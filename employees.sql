-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 04, 2019 at 09:28 AM
-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `socialSecurityNumber` int(9) NOT NULL AUTO_INCREMENT,
  `dateOfBirth` date NOT NULL,
  `firstName` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `salary` float NOT NULL,
  `gender` char(1) NOT NULL,
  PRIMARY KEY (`socialSecurityNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=300000001 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`socialSecurityNumber`, `dateOfBirth`, `firstName`, `surname`, `salary`, `gender`) VALUES
(100000000, '1990-01-01', 'Joe', 'Bloggs', 54000, 'M'),
(100000001, '1990-01-02', 'Jon', 'Bloggs', 55000, 'M'),
(100000002, '1990-01-03', 'John', 'Bloggs', 56000, 'M'),
(100000003, '1998-04-20', 'Leana', 'Express', 60000, 'F'),
(100000006, '2019-10-31', 'Andy', 'Wong', 30000, 'M'),
(300000000, '2019-09-29', 'Lana', 'Doe', 20000, 'F');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
