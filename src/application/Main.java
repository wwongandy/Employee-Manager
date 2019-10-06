package application;

// JDBC and validation imports
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

// Imports for AWT & Swing GUI
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JFormattedTextField;
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
	private ArrayList<Employee> employeesArr;

	// Swing GUI components
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

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
		
		textField = new JTextField();
		textField.setBounds(121, 5, 310, 19);
		textField.setToolTipText("Social security number, 9+ digit numbers only");
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("DoB");
		label.setBounds(40, 29, 71, 13);
		contentPane.add(label);
		
		textField_1 = new JTextField();
		textField_1.setBounds(121, 29, 310, 19);
		textField_1.setToolTipText("Date of birth, YYYY-MM-DD formats only");
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		JLabel label_1 = new JLabel("First name");
		label_1.setBounds(10, 53, 101, 13);
		contentPane.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(121, 53, 310, 19);
		textField_2.setToolTipText("First name");
		textField_2.setColumns(10);
		contentPane.add(textField_2);
		
		JLabel label_2 = new JLabel("Surname");
		label_2.setBounds(18, 77, 93, 13);
		contentPane.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(121, 77, 310, 19);
		textField_3.setToolTipText("Surname");
		textField_3.setColumns(10);
		contentPane.add(textField_3);
		
		JLabel label_3 = new JLabel("Salary");
		label_3.setBounds(31, 101, 80, 13);
		contentPane.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(121, 101, 310, 19);
		textField_4.setToolTipText("Salary, please use digits only");
		textField_4.setColumns(10);
		contentPane.add(textField_4);
		
		JLabel label_4 = new JLabel("Gender");
		label_4.setBounds(25, 126, 86, 13);
		contentPane.add(label_4);
		
		String[] comboBoxOptions = {"Male", "Female", "Other"};
		JComboBox<String> comboBox = new JComboBox<String>(comboBoxOptions);
		comboBox.setBounds(121, 125, 310, 21);
		contentPane.add(comboBox);
		
		JButton btnAdd = new JButton("Add Employee");
		btnAdd.setBounds(296, 151, 135, 21);
		contentPane.add(btnAdd);
		
		JButton button = new JButton("Clear Fields");
		button.setBounds(296, 177, 135, 21);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnSearchEmployee.setBounds(5, 213, 171, 21);
		contentPane.add(btnSearchEmployee);
		
		textField_5 = new JTextField();
		textField_5.setBounds(186, 214, 245, 19);
		textField_5.setToolTipText("Surname or SSN");
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JEditorPane dtrpnNoEmployeeWith = new JEditorPane();
		dtrpnNoEmployeeWith.setBounds(186, 239, 245, 169);
		dtrpnNoEmployeeWith.setText("No employee with associated SSN or surname found.");
		dtrpnNoEmployeeWith.setEditable(false);
		contentPane.add(dtrpnNoEmployeeWith);
		
		JButton button_1 = new JButton("Next");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(5, 240, 80, 21);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("Previous");
		button_2.setBounds(91, 240, 85, 21);
		contentPane.add(button_2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(5, 271, 80, 21);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
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