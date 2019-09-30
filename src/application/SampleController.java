package application;

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
		gender.getItems().addAll(
				"Male",
				"Female"
		);
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
		
	}
}
