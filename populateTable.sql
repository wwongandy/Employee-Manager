-- Script for creating and populating the "Employees" table

CREATE TABLE IF NOT EXISTS `employees` (
    `socialSecurityNumber` int(9) NOT NULL AUTO_INCREMENT,
    `dateOfBirth` datetime NOT NULL,
    `firstName` varchar(128) NOT NULL,
    `surname` varchar(128) NOT NULL
    -- `salary` varchar(128) NOT NULL,
    -- `gender` varchar(128) NOT NULL

    PRIMARY KEY (`socialSecurityNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;