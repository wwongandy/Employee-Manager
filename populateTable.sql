-- Script for creating and populating the "Employees" table

-- Creation
CREATE TABLE IF NOT EXISTS `employees` (
    `socialSecurityNumber` int(9) NOT NULL AUTO_INCREMENT,
    `dateOfBirth` date NOT NULL,
    `firstName` varchar(128) NOT NULL,
    `surname` varchar(128) NOT NULL,
    `salary` float(11) NOT NULL,
    `gender` char(1) NOT NULL,

    PRIMARY KEY (`socialSecurityNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

-- Population
INSERT INTO `employees` (`socialSecurityNumber`, `dateOfBirth`, `firstName`, `surname`, `salary`, `gender`)
VALUES  (100000000, '1990-01-01', 'Joe', 'Bloggs', 54000, 'M'),
        (100000001, '1990-01-02', 'Jon', 'Bloggs', 55000, 'M'),
        (100000002, '1990-01-03', 'John', 'Bloggs', 56000, 'M'),
        (100000003, '1998-04-20', 'Leana', 'Express', 60000, 'F')