package application;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
	
	// SQL database connection configuration, localhost so it's not a security flaw! Totally.
	private final String SQL_USERNAME = "root";
	private final String SQL_PASSWORD = "root";
	private final String SERVER_NAME = "localhost";
	private final int PORT_NUMBER = 3306;
	private final String DATABASE_NAME = "test";
	private final String TABLE_NAME = "employees";
	
	// SQL connection variable for handling queries and updates
	private Connection thisConnection;
	private ArrayList<Employee> employeesArr;
	
	/**
	 * Main function to initialise the Swing application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Main app = new Main();
		app.initialize();
	}
	
	/**
	 * Initialisation function to setup Swing components and connection to SQL.
	 */
	public void initialize() {
		
		try {
			thisConnection = this.connectToSQL();
		} catch (Exception e) {
			thisConnection = null;
			return;
		}
		
		this.setupSwingComponents();
	}
	
	/**
	 * Builds the Swing GUI and its associated event listeners.
	 */
	public void setupSwingComponents() {
		
	}
	
	/**
	 * Makes a connection to the local SQL database using JDBC.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection connectToSQL() throws SQLException {
		
		Connection conn = null;
		Properties connectionProps = new Properties();
		
		// Login credentials for SQL localhost
		connectionProps.put("user", this.SQL_USERNAME);
		connectionProps.put("password", this.SQL_PASSWORD);
		
		try {
			// Connecting to the SQL database
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + this.SERVER_NAME + ":" + this.PORT_NUMBER + "/" + this.DATABASE_NAME + "?serverTimezone=UTC",
					connectionProps
			);
			
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Empties all text fields from the GUI.
	 */
	public void clearAddFormFields() {
		
	}
	
	/**
	 * Searches for an employee from the loaded array list of employees.
	 * Instead of constantly querying from the SQL database, this will be more efficient and give less load on the database.
	 */
	public void searchEmployee() {
		
	}
	
	/**
	 * Retrieves all employees from the database and stores it into an array list.
	 */
	public void getAllEmployees() {
		
		try {
			Statement stmt = this.thisConnection.createStatement();
			stmt.executeQuery(
					"SELECT * FROM `employees`"
			);
			
			ResultSet currentEmployee = stmt.getResultSet();
			ArrayList<Employee> employeesArr = new ArrayList<Employee>();
			
			// Converting to ArrayList for quick referencing later
			while (currentEmployee.next()) {
				Employee employee = new Employee(
					currentEmployee.getInt("socialSecurityNumber"),
					currentEmployee.getDate("dateOfBirth"),
					currentEmployee.getString("firstName"),
					currentEmployee.getString("surname"),
					currentEmployee.getFloat("salary"),
					currentEmployee.getString("gender")
				);
				
				employeesArr.add(employee);
			}
			
			this.employeesArr = employeesArr;
			
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Removes the selected employee in the GUI from the database.
	 */
	public void deleteEmployee() {
		
	}
	
	/**
	 * Updates the selected employee in the GUI and replaces his existing details with the new given details in the database.
	 */
	public void updateEmployee() {
		
	}
	
	/**
	 * Adds an employee to the database with the given details from the GUI, uses Employee class to perform pre-validation before sending it to SQL.
	 */
	public void addEmployee() {
		/*
		String _socialSecurityNumber = socialSecurityNumber.getText();
		LocalDate _dateOfBirth = dateOfBirth.getValue();
		String _firstName = firstName.getText();
		String _surname = surname.getText();
		String _salary = salary.getText();
		String _gender = gender.getValue();
		
		Employee newEmployee = new Employee(
				_socialSecurityNumber,
				_dateOfBirth,
				_firstName,
				_surname,
				_salary,
				_gender
		);
		
		if (newEmployee.isInvalid()) {
			// TODO: Toast message indicating invalid schema using newEmployee.getErrorMsg();
//			System.out.println(newEmployee.getErrorMsg());
			return;
		}
		
		try {
			PreparedStatement stmt = this.thisConnection.prepareStatement(
					"INSERT INTO `employees` (`socialSecurityNumber`, `dateOfBirth`, `firstName`, `surname`, `salary`, `gender`) " +
					"VALUES (?, ?, ?, ?, ?, ?)"
			);
			
			stmt.setInt(1, newEmployee.getSocialSecurityNumber());
			stmt.setDate(2, (Date) newEmployee.getDateOfBirth());
			stmt.setString(3, newEmployee.getFirstName());
			stmt.setString(4, newEmployee.getSurname());
			stmt.setFloat(5, newEmployee.getSalary());
			stmt.setString(6, String.valueOf(newEmployee.getGender()));
			
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			// TODO: Toast message indicating invalid insertion
//			System.out.println(e);
			return;
		}
		
//		System.out.println("Inserted employee to database");
		
		// Clear all text fields after insertion
		this.clearAddFormFields();
		*/
	}
}
