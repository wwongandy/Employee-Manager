package application;

// JDBC and validation imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// Imports for AWT & Swing GUI
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JEditorPane;

public class Main extends JFrame {
	
	// JDBC SQL database configuration settings
	private final String SQL_USERNAME = "root";
	private final String SQL_PASSWORD = "";
	private final String SERVER_NAME = "localhost";
	private final int PORT_NUMBER = 3306;
	private final String DATABASE_NAME = "test";
	private final String TABLE_NAME = "employees";

	// Swing GUI components
	private JPanel contentPane;
	private JTextField socialSecurityNumber;
	private JTextField dateOfBirth;
	private JTextField firstName;
	private JTextField surname;
	private JTextField salary;
	private JComboBox<String> gender;
	private JButton btnAddEmployee;
	private JTextField searchEmployeeText;
	private JEditorPane outputDataBox;
	
	// Fixed strings for quick referencing
	private final String BTN_ADD_EMPLOYEE = "Add Employee";
	private final String BTN_UPDATE_EMPLOYEE = "Update Employee";
	
	// Other variables
	private Connection thisConnection; // SQL connection variable for handling queries and updates
	private ResultSet employeesArr = null; // Storing search history for quick traversal and update/delete selection
	private boolean isUpdatingEmployee = false; // Flag to determine if update operation is in progress
	private final String[] GENDER_OPTIONS = {"Male", "Female", "Other"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialises the GUI and the connection to the SQL database.
	 */
	public Main() {
		this.makeGUI();
		this.connectToSQL();
	}
	
	/**
	 * Creates the GUI frame.
	 */
	public void makeGUI() {
		
		// Auto-generated code from Eclipse WindowBuilder to setup the AWT & Swing GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSSN = new JLabel("SSN");
		lblSSN.setBounds(41, 5, 70, 13);
		contentPane.add(lblSSN);
		
		socialSecurityNumber = new JTextField();
		socialSecurityNumber.setBounds(121, 5, 310, 19);
		socialSecurityNumber.setToolTipText("Social security number, 9+ digit numbers only");
		contentPane.add(socialSecurityNumber);
		socialSecurityNumber.setColumns(10);
		
		JLabel lblDoB = new JLabel("DoB");
		lblDoB.setBounds(40, 29, 71, 13);
		contentPane.add(lblDoB);
		
		dateOfBirth = new JTextField();
		dateOfBirth.setBounds(121, 29, 310, 19);
		dateOfBirth.setToolTipText("Date of birth, YYYY-MM-DD formats only");
		dateOfBirth.setColumns(10);
		contentPane.add(dateOfBirth);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(10, 53, 101, 13);
		contentPane.add(lblFirstName);
		
		firstName = new JTextField();
		firstName.setBounds(121, 53, 310, 19);
		firstName.setToolTipText("First name");
		firstName.setColumns(10);
		contentPane.add(firstName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(18, 77, 93, 13);
		contentPane.add(lblSurname);
		
		surname = new JTextField();
		surname.setBounds(121, 77, 310, 19);
		surname.setToolTipText("Surname");
		surname.setColumns(10);
		contentPane.add(surname);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setBounds(31, 101, 80, 13);
		contentPane.add(lblSalary);
		
		salary = new JTextField();
		salary.setBounds(121, 101, 310, 19);
		salary.setToolTipText("Salary, digits only");
		salary.setColumns(10);
		contentPane.add(salary);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(25, 126, 86, 13);
		contentPane.add(lblGender);
		
		gender = new JComboBox<String>(GENDER_OPTIONS);
		gender.setBounds(121, 125, 310, 21);
		contentPane.add(gender);
		
		btnAddEmployee = new JButton(BTN_ADD_EMPLOYEE);
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isUpdatingEmployee) {
					updateEmployeeFin();
				} else {
					addEmployee();
				}
			}
		});
		btnAddEmployee.setBounds(296, 151, 135, 21);
		contentPane.add(btnAddEmployee);
		
		JButton btnClearFields = new JButton("Clear Fields");
		btnClearFields.setBounds(296, 177, 135, 21);
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Button also used for cancelling update operation
				if (isUpdatingEmployee) {
					clearUpdateFormFields();
				} else {
					clearAddFormFields();
				}
			}
		});
		contentPane.add(btnClearFields);
		
		Component divider = Box.createHorizontalStrut(40);
		divider.setBounds(0, 0, 0, 0);
		contentPane.add(divider);
		
		JButton btnSearchEmployee = new JButton("Search Employee");
		btnSearchEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchEmployee();
			}
		});
		btnSearchEmployee.setBounds(5, 213, 171, 21);
		contentPane.add(btnSearchEmployee);
		
		searchEmployeeText = new JTextField();
		searchEmployeeText.setBounds(186, 214, 245, 19);
		searchEmployeeText.setToolTipText("Surname or SSN");
		contentPane.add(searchEmployeeText);
		searchEmployeeText.setColumns(10);
		
		outputDataBox = new JEditorPane();
		outputDataBox.setBounds(186, 239, 245, 169);
		outputDataBox.setText("No employee with associated SSN or surname found.");
		outputDataBox.setEditable(false);
		contentPane.add(outputDataBox);
		
		JButton btnNextEmployee = new JButton("Next");
		btnNextEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getNextEmployee();
			}
		});
		btnNextEmployee.setBounds(5, 240, 80, 21);
		contentPane.add(btnNextEmployee);
		
		JButton btnPrevEmployee = new JButton("Previous");
		btnPrevEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getPreviousEmployee();
			}
		});
		btnPrevEmployee.setBounds(91, 240, 85, 21);
		contentPane.add(btnPrevEmployee);
		
		JButton btnUpdateEmployee = new JButton("Update");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEmployee();
			}
		});
		btnUpdateEmployee.setBounds(5, 271, 80, 21);
		contentPane.add(btnUpdateEmployee);
		
		JButton btnDeleteEmployee = new JButton("Delete");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEmployee();
			}
		});
		btnDeleteEmployee.setBounds(91, 271, 85, 21);
		contentPane.add(btnDeleteEmployee);
	}
	
	/**
	 * Makes a connection to the local SQL database using JDBC.
	 */
	public void connectToSQL() {
		
		Properties connectionProps = new Properties();
		
		// Login credentials for SQL localhost database
		connectionProps.put("user", this.SQL_USERNAME);
		connectionProps.put("password", this.SQL_PASSWORD);
		
		try {
			// Connecting to the SQL database
			this.thisConnection = DriverManager.getConnection(
					"jdbc:mysql://" + this.SERVER_NAME + ":" + this.PORT_NUMBER + "/" + this.DATABASE_NAME + "?serverTimezone=UTC",
					connectionProps
			);
		} catch (Exception e) {
		}
	}
	
	/**
	 * Empties all text fields from the GUI.
	 */
	public void clearAddFormFields() {
		socialSecurityNumber.setText("");
		dateOfBirth.setText("");
		firstName.setText("");
		surname.setText("");
		salary.setText("");
	}
	
	/**
	 * Empties all text fields from the GUI if update operation is in process.
	 */
	public void clearUpdateFormFields() {
		if (!this.isUpdatingEmployee) {
			return;
		};
		
		socialSecurityNumber.setText("");
		dateOfBirth.setText("");
		firstName.setText("");
		surname.setText("");
		salary.setText("");
		
		this.isUpdatingEmployee = false;
		btnAddEmployee.setText(BTN_ADD_EMPLOYEE);
	};
	
	/**
	 * Searches for an employee via a query into the SQL database. Performs pre-validation to determine the search parameter (SSN or first name/surname).
	 */
	public void searchEmployee() {
		
		String query = searchEmployeeText.getText();
		boolean searchingViaSSN = true;
		int ssnQuery = -1;
		
		if (Employee.validateSSN(query)) {
			ssnQuery = Integer.parseInt(query);
		} else {
			searchingViaSSN = false;
		}
		
		try {
			Statement stmt = this.thisConnection.createStatement();
			
			// Building command based on query parameter
			if (searchingViaSSN) {
				stmt.executeQuery(
						"SELECT * FROM `employees` " +
						"WHERE (socialSecurityNumber = " + ssnQuery + ")"
				);
			} else {
				stmt.executeQuery(
						"SELECT * FROM `employees` " +
						"WHERE (firstName = '" + query + "' OR surname = '" + query + "')"
				);
			}
			
			ResultSet employeesFound = stmt.getResultSet();
			this.employeesArr = employeesFound;
			
			// Display the first listed employee
			this.employeesArr.first();
			Employee employeeFound = this.getEmployee();
			outputDataBox.setText(employeeFound.toString());
		} catch (Exception e) {
			employeesArr = null;
			outputDataBox.setText("No employee with associated SSN or surname found.");
		}
		
		this.clearUpdateFormFields();
	}
	
	/**
	 * Searches for the next employee in the result set.
	 */
	public void getNextEmployee() {
		if (this.employeesArr == null) {
			return;
		}
		
		try {
			this.employeesArr.next();
			Employee nextEmployee = this.getEmployee();
			outputDataBox.setText(nextEmployee.toString());
		} catch (Exception e) {
			outputDataBox.setText("There is no more employees after the current one.");
		}

		this.clearUpdateFormFields();
	}
	
	/**
	 * Searches for the previous employee in the result set.
	 */
	public void getPreviousEmployee() {
		if (this.employeesArr == null) {
			return;
		}
		
		try {
			this.employeesArr.previous();
			Employee prevEmployee = this.getEmployee();
			outputDataBox.setText(prevEmployee.toString());
		} catch (Exception e) {
			outputDataBox.setText("There is no more employees before the current one.");
		}
		
		this.clearUpdateFormFields();
	};
	
	/**
	 * Removes the selected employee in the GUI from the database.
	 */
	public void deleteEmployee() {
		if (this.employeesArr == null) {
			return;
		}
		
		try {
			Employee deletingEmployee = this.getEmployee();
			int socialSecurityNumber = deletingEmployee.getSocialSecurityNumber();
			
			Statement stmt = this.thisConnection.createStatement();
			
			stmt.executeUpdate(
					"DELETE FROM `employees` " +
					"WHERE (socialSecurityNumber = " + socialSecurityNumber + ")"
			);
			
			stmt.close();
			outputDataBox.setText("The selected employee with ID " + socialSecurityNumber + " was deleted.");
			
			this.employeesArr = null;
			this.clearUpdateFormFields();
		} catch (SQLException e) {
			return;
		}
	}
	
	/**
	 * Updates the selected employee in the GUI and replaces his existing details with the new given details in the database.
	 */
	public void updateEmployee() {
		if (this.employeesArr == null) {
			return;
		}
		
		try {
			Employee updatingEmployee = this.getEmployee();
			// If no error; employee for updating, exists
			
			socialSecurityNumber.setText(Integer.toString(updatingEmployee.getSocialSecurityNumber()));
			dateOfBirth.setText(updatingEmployee.getDateOfBirthFormatted());
			firstName.setText(updatingEmployee.getFirstName());
			surname.setText(updatingEmployee.getSurname());
			salary.setText(Float.toString(updatingEmployee.getSalary()));
			gender.setSelectedItem(updatingEmployee.getGender());
			
			isUpdatingEmployee = true;
			btnAddEmployee.setText(BTN_UPDATE_EMPLOYEE);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Updates the current selected employee with the new updated details.
	 */
	public void updateEmployeeFin() {
		if (!isUpdatingEmployee || this.employeesArr == null) {
			return;
		}
		
		// Ensuring new employee details passes validation tests
		String _socialSecurityNumber = socialSecurityNumber.getText();
		String _dateOfBirth = dateOfBirth.getText();
		String _firstName = firstName.getText();
		String _surname = surname.getText();
		String _salary = salary.getText();
		String _gender = GENDER_OPTIONS[gender.getSelectedIndex()];
		
		Employee updatedEmployee = new Employee(
				_socialSecurityNumber,
				_dateOfBirth,
				_firstName,
				_surname,
				_salary,
				_gender
		);
		
		if (updatedEmployee.isInvalid()) {
			outputDataBox.setText(updatedEmployee.getErrorMsg());
			return;
		}
		
		try {
			Employee oldEmployee = this.getEmployee();
			int __socialSecurityNumber = updatedEmployee.getSocialSecurityNumber();
			
			// Primary key is SSN, therefore not mutable!
			if (oldEmployee.getSocialSecurityNumber() != __socialSecurityNumber) {
				outputDataBox.setText("Social security number cannot be changed.");
				socialSecurityNumber.setText(Integer.toString(oldEmployee.getSocialSecurityNumber()));
				return;
			}
			
			PreparedStatement stmt = this.thisConnection.prepareStatement(
					"UPDATE `employees` " +
					"SET dateOfBirth = ?, firstName = ?, surname = ?, salary = ?, gender = ? " +
					"WHERE socialSecurityNumber = ?"
			);
			
			stmt.setDate(1, new java.sql.Date(updatedEmployee.getDateOfBirth().getTime()));
			stmt.setString(2, updatedEmployee.getFirstName());
			stmt.setString(3, updatedEmployee.getSurname());
			stmt.setFloat(4, updatedEmployee.getSalary());
			stmt.setString(5, String.valueOf(updatedEmployee.getGender()));
			stmt.setInt(6, __socialSecurityNumber);
			
			stmt.executeUpdate();
			stmt.close();
			
			this.employeesArr = null;
			this.clearUpdateFormFields();
			outputDataBox.setText("Employee with SSN " + __socialSecurityNumber + " was updated.");
		} catch (Exception e) {
			outputDataBox.setText("An error occurred while attempting to update an employee.");
			return;
		}
	}
	
	/**
	 * Retrieves the current selected employee as an employee object.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Employee getEmployee() throws SQLException {
		return new Employee(
				this.employeesArr.getInt("socialSecurityNumber"),
				this.employeesArr.getDate("dateOfBirth"),
				this.employeesArr.getString("firstName"),
				this.employeesArr.getString("surname"),
				this.employeesArr.getFloat("salary"),
				this.employeesArr.getString("gender")
		);
	}
	
	/**
	 * Adds an employee to the database with the given details from the GUI, uses Employee class to perform pre-validation before sending it to SQL.
	 */
	public void addEmployee() {
		String _socialSecurityNumber = socialSecurityNumber.getText();
		String _dateOfBirth = dateOfBirth.getText();
		String _firstName = firstName.getText();
		String _surname = surname.getText();
		String _salary = salary.getText();
		String _gender = GENDER_OPTIONS[gender.getSelectedIndex()];
		
		Employee newEmployee = new Employee(
				_socialSecurityNumber,
				_dateOfBirth,
				_firstName,
				_surname,
				_salary,
				_gender
		);
		
		if (newEmployee.isInvalid()) {
			outputDataBox.setText(newEmployee.getErrorMsg());
			return;
		}
		
		try {
			PreparedStatement stmt = this.thisConnection.prepareStatement(
					"INSERT INTO `employees` (`socialSecurityNumber`, `dateOfBirth`, `firstName`, `surname`, `salary`, `gender`) " +
					"VALUES (?, ?, ?, ?, ?, ?)"
			);
			
			stmt.setInt(1, newEmployee.getSocialSecurityNumber());
			stmt.setDate(2, new java.sql.Date(newEmployee.getDateOfBirth().getTime()));
			stmt.setString(3, newEmployee.getFirstName());
			stmt.setString(4, newEmployee.getSurname());
			stmt.setFloat(5, newEmployee.getSalary());
			stmt.setString(6, String.valueOf(newEmployee.getGender()));
			
			stmt.executeUpdate();
			stmt.close();
		} catch (Exception e) {
			outputDataBox.setText("An error occurred while attempting to add the employee.");			
			return;
		}
		
		outputDataBox.setText(newEmployee.toString());
		
		// Clear all text fields after insertion
		this.clearAddFormFields();
	}
}