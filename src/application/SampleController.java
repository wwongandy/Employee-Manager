package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SampleController {
	
	// SQL database connection configuration
	// Technically a security flaw, but thankfully our database is in localhost!
	private final String SQL_USERNAME = "root";
	private final String SQL_PASSWORD = "root";
	private final String SERVER_NAME = "localhost";
	private final int PORT_NUMBER = 3306;
	private final String DATABASE_NAME = "test";
	private final String TABLE_NAME = "employees";
	
	// SQL connection variable for handling queries and updates
	private Connection thisConnection;
	
	@FXML
	private TextField socialSecurityNumber, firstName, surname, salary;
	@FXML
	private DatePicker dateOfBirth;
	@FXML
	private ComboBox<String> gender;
	@FXML
	private Button addEmployee, clearFields;
	@FXML
	private ListView employeeListing;
	
	// Initialisation function once JavaFX variables are loaded
	public void initialize() {
		
		// Connecting to SQL upon initialisation
		try {
			thisConnection = this.connectToSQL();
		} catch (Exception e) {
			thisConnection = null;
			return;
		}
		
		// Adding selectable options for gender dropdown
		gender.getItems().addAll(
				"Male",
				"Female"
		);
	}
	
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
	
	// Employee form buttons handlers
	public void clearAddFormFields() {
		socialSecurityNumber.clear();
		firstName.clear();
		surname.clear();
		salary.clear();
	}
	
	// Employee profile traversal operations
	public void viewEmployee() {
		
	}
	
	public void viewNextEmployee() {
		
	}
	
	public void viewPreviousEmployee() {
		
	}
	
	public void searchEmployee() {
		
	}
	
	// CRUD - JDBC to SQL Operations;
	public void getAllEmployees() {
		
	}
	
	public void deleteEmployee() {
		
	}
	
	public void updateEmployee() {
		
	}
	
	public void addEmployee() {
		String _socialSecurityNumber = socialSecurityNumber.getText();
		LocalDate _dateOfBirth = dateOfBirth.getValue();
		String _firstName = firstName.getText();
		String _surname = surname.getText();
		String _salary = salary.getText();
		String _gender = gender.getValue();
		
		System.out.println("SSN: " + _socialSecurityNumber + ", DoB: " + _dateOfBirth + ", Name: " + _firstName + " " + _surname + " (" + _gender + "), Salary: " + _salary);
		
		// Clear all text fields after insertion
		this.clearAddFormFields();
	}
}
