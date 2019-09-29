-- Script for creating and populating the "Employees" table

-- Creation
CREATE TABLE IF NOT EXISTS `employees` (
    `socialSecurityNumber` int(9) NOT NULL AUTO_INCREMENT,
    `dateOfBirth` datetime NOT NULL,
    `firstName` varchar(128) NOT NULL,
    `surname` varchar(128) NOT NULL,
    `salary` float(11) NOT NULL,
    `gender` char(1) NOT NULL,

    PRIMARY KEY (`socialSecurityNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

-- Population TODO