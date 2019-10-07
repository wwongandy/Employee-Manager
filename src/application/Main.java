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
	
	// SQL DB connection configuration, localhost so it's not a security flaw! Totally.
	private final String SQL_USERNAME = "root";
	private final String SQL_PASSWORD = "";
	private final String SERVER_NAME = "localhost";
	private final int PORT_NUMBER = 3306;
	private final String DATABASE_NAME = "test";
	private final String TABLE_NAME = "employees";
	
	// SQL connection variable for handling queries and updates
	private Connection thisConnection;
	
	private ResultSet employeesArr = null;
	private boolean isUpdatingEmployee = false;
	private final String[] GENDER_OPTIONS = {"Male", "Female", "Other"};

	// Swing GUI components
	private JPanel contentPane;
	private JTextField socialSecurityNumber;
	private JTextField dateOfBirth;
	private JTextField firstName;
	private JTextField surname;
	private JTextField salary;
	private JComboBox<String> gender;
	private JButton btnAdd;
	private JTextField searchEmployeeText;
	private JEditorPane outputDataBox;

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
		
		try {
			this.thisConnection = this.connectToSQL();
		} catch (Exception e) {
		}
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
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setBounds(41, 5, 70, 13);
		contentPane.add(lblSsn);
		
		socialSecurityNumber = new JTextField();
		socialSecurityNumber.setBounds(121, 5, 310, 19);
		socialSecurityNumber.setToolTipText("Social security number, 9+ digit numbers only");
		contentPane.add(socialSecurityNumber);
		socialSecurityNumber.setColumns(10);
		
		JLabel label = new JLabel("DoB");
		label.setBounds(40, 29, 71, 13);
		contentPane.add(label);
		
		dateOfBirth = new JTextField();
		dateOfBirth.setBounds(121, 29, 310, 19);
		dateOfBirth.setToolTipText("Date of birth, YYYY-MM-DD formats only");
		dateOfBirth.setColumns(10);
		contentPane.add(dateOfBirth);
		
		JLabel label_1 = new JLabel("First name");
		label_1.setBounds(10, 53, 101, 13);
		contentPane.add(label_1);
		
		firstName = new JTextField();
		firstName.setBounds(121, 53, 310, 19);
		firstName.setToolTipText("First name");
		firstName.setColumns(10);
		contentPane.add(firstName);
		
		JLabel label_2 = new JLabel("Surname");
		label_2.setBounds(18, 77, 93, 13);
		contentPane.add(label_2);
		
		surname = new JTextField();
		surname.setBounds(121, 77, 310, 19);
		surname.setToolTipText("Surname");
		surname.setColumns(10);
		contentPane.add(surname);
		
		JLabel label_3 = new JLabel("Salary");
		label_3.setBounds(31, 101, 80, 13);
		contentPane.add(label_3);
		
		salary = new JTextField();
		salary.setBounds(121, 101, 310, 19);
		salary.setToolTipText("Salary, digits only");
		salary.setColumns(10);
		contentPane.add(salary);
		
		JLabel label_4 = new JLabel("Gender");
		label_4.setBounds(25, 126, 86, 13);
		contentPane.add(label_4);
		
		gender = new JComboBox<String>(GENDER_OPTIONS);
		gender.setBounds(121, 125, 310, 21);
		contentPane.add(gender);
		
		btnAdd = new JButton("Add Employee");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (isUpdatingEmployee) {
					updateEmployeeFin();
				} else {
					addEmployee();
				}
			}
		});
		btnAdd.setBounds(296, 151, 135, 21);
		contentPane.add(btnAdd);
		
		JButton button = new JButton("Clear Fields");
		button.setBounds(296, 177, 135, 21);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isUpdatingEmployee) {
					clearUpdateFormFields();
				} else {
					clearAddFormFields();
				}
			}
		});
		contentPane.add(button);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setBounds(0, 0, 0, 0);
		contentPane.add(horizontalStrut_1);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(0, 0, 0, 0);
		contentPane.add(horizontalStrut);
		
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
		
		JButton button_1 = new JButton("Next");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getNextEmployee();
			}
		});
		button_1.setBounds(5, 240, 80, 21);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Previous");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getPreviousEmployee();
			}
		});
		button_2.setBounds(91, 240, 85, 21);
		contentPane.add(button_2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEmployee();
			}
		});
		btnUpdate.setBounds(5, 271, 80, 21);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEmployee();
			}
		});
		btnDelete.setBounds(91, 271, 85, 21);
		contentPane.add(btnDelete);
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
		
		// Login credentials for SQL localhost database
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
		socialSecurityNumber.setText("");
		dateOfBirth.setText("");
		firstName.setText("");
		surname.setText("");
		salary.setText("");
	}
	
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
		btnAdd.setText("Add Employee");
	};
	
	/**
	 * Searches for an employee from the loaded array list of employees.
	 * Instead of constantly querying from the SQL database, this will be more efficient and give less load on the database.
	 */
	public void searchEmployee() {
		
		String query = searchEmployeeText.getText();
		if (query.equals("")) {
			return;
		}
		
		boolean searchingViaSSN = true;
		int ssnQuery = -1;
		try {
			ssnQuery = Integer.parseInt(query);
			
			if (ssnQuery < 100000000) {
				outputDataBox.setText("Social security number must be 9+ digit numbers only.");
				this.clearUpdateFormFields();
				return;
			}
		} catch (Exception e) {
			searchingViaSSN = false;
		}
		
		try {
			Statement stmt = this.thisConnection.createStatement();
			
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
			this.employeesArr.first();
			
			Employee employeeFound = this.getEmployee();
			outputDataBox.setText(employeeFound.toString());
			
		} catch (Exception e) {
			employeesArr = null;
			
			outputDataBox.setText("No employee with associated SSN or surname found.");
			System.out.println(e);
		}
		
		this.clearUpdateFormFields();
	}
	
	/**
	 * Searches for the next employee in the array.
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
			System.out.println(e);
		}

		this.clearUpdateFormFields();
	}
	
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
			System.out.println(e);
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
			System.out.println(e);
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
			btnAdd.setText("Update Employee");
		} catch (Exception e) {
			return;
		}
	}
	
	/**
	 * Updates the current selected employee with the new updated details.
	 */
	public void updateEmployeeFin() {
		if (!isUpdatingEmployee || this.employeesArr == null) {
			return;
		}
		
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
			int socialSecurityNumber = updatedEmployee.getSocialSecurityNumber();
			
			if (oldEmployee.getSocialSecurityNumber() != socialSecurityNumber) {
				outputDataBox.setText("Social security number cannot be changed.");
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
			stmt.setInt(6, socialSecurityNumber);
			
			stmt.executeUpdate();
			stmt.close();
			
			this.employeesArr = null;
			this.clearUpdateFormFields();
			outputDataBox.setText("Employee with SSN " + socialSecurityNumber + " was updated.");
		} catch (Exception e) {
			outputDataBox.setText("An error occurred while attempting to update an employee.");
			System.out.println(e);
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
			System.out.println(e);
			
			return;
		}
		
		outputDataBox.setText(newEmployee.toString());
		
		// Clear all text fields after insertion
		this.clearAddFormFields();
	}
}