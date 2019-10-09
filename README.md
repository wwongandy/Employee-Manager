# Employee Manager
This is a Java Swing application for managing employees via the usage of a SQL database and the JDBC API.

## Technologies Used
* Primary Language: Java
* GUI Specification: AWT & Swing
* IDE: Eclipse

## SQL Database Configuration
For the system to work, an SQL database with the following table configuration must exist:
```
CREATE TABLE IF NOT EXISTS `employees` (
  `socialSecurityNumber` int(9) NOT NULL AUTO_INCREMENT,
  `dateOfBirth` date NOT NULL,
  `firstName` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `salary` float NOT NULL,
  `gender` char(1) NOT NULL,
  PRIMARY KEY (`socialSecurityNumber`)
)
```

## Importing
* Clone the project, and import it to your preferred IDE.
* By default, the `mysql-connector-java.jar` file should be configured into the build path of the project, but otherwise; this but be added as a referenced library.
* Setup a local MySQL localhost server following the steps from above.
* Ensure that your SQL database credentials are configured correctly onto the global SQL variables in `src/Main.java`.
* Now the Java program should run without any issues.