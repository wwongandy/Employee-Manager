/***
 * Class for storing basic Employee data.
 * Follows the SQL schema specification for an "employees" object, therefore this class provides pre-validation.
 */

package application;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Employee {
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	// Giving default values to easily check if employee object passes validation
	private int socialSecurityNumber = 0;
	private Date dateOfBirth = null;
	private String firstName = null;
	private String surname = null;
	private float salary = 0;
	private char gender = 'N';
	
	// Storing the most recent error message for validation handling
	private String errorMsg;

	/**
	 * Default constructor to ensure given employee details passes validation.
	 * 
	 * @param socialSecurityNumber
	 * @param _dateOfBirth
	 * @param firstName
	 * @param surname
	 * @param salary
	 * @param gender
	 */
	public Employee(
			String socialSecurityNumber,
			String _dateOfBirth,
			String firstName,
			String surname,
			String salary,
			String gender) {
		
		this.setSocialSecurityNumber(socialSecurityNumber);
		this.setDateOfBirth(_dateOfBirth);
		this.setFirstName(firstName);
		this.setSurname(surname);
		this.setSalary(salary);
		this.setGender(gender);
	};
	
	/**
	 * Constructor to store employee results from the SQL database queries as an object.
	 * 
	 * @param socialSecurityNumber
	 * @param dateOfBirth
	 * @param firstName
	 * @param surname
	 * @param salary
	 * @param gender
	 */
	public Employee(
			int socialSecurityNumber,
			java.sql.Date dateOfBirth,
			String firstName,
			String surname,
			float salary,
			String gender) {
		this.setSocialSecurityNumber(socialSecurityNumber);
		this.setDateOfBirth(dateOfBirth);
		this.setFirstName(firstName);
		this.setSurname(surname);
		this.setSalary(salary);
		this.setGender(gender);
	}

	/**
	 * @return
	 */
	public int getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	/**
	 * Default setter for socialSecurityNumber.
	 * Ensures that the given social security number is a 9-digit or more integer.
	 * 
	 * @param socialSecurityNumber
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		
		if (socialSecurityNumber.length() < 9) {
			this.errorMsg = "Social security number must be at-least 9 digits long";
			return;
		}
		
		try {
			int newSSN = Integer.parseInt(socialSecurityNumber);
			this.socialSecurityNumber = newSSN;
		} catch (Exception e) {
			this.errorMsg = "Letters should not exist within the social security number"; 
		}
	}
	
	/**
	 * Setter for socialSecurityNumber.
	 * Ensures that the given social security number is a 9-digit or more integer.
	 * 
	 * @param socialSecurityNumber
	 */
	public void setSocialSecurityNumber(int socialSecurityNumber) {
		
		if (socialSecurityNumber < 100000000) {
			this.errorMsg = "Social security number must be at-least 9 digits long";
			return;
		}
		
		this.socialSecurityNumber = socialSecurityNumber;
	}
	/**
	 * Unused.
	 * 
	 * @return
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Default setter for dateOfBirth.
	 * Ensures the given date can be converted into a "Date" object.
	 * 
	 * @param dateOfBirth
	 */
	public void setDateOfBirth(String dateOfBirth) {
		
		if (dateOfBirth.length() < 10) {
			this.errorMsg = "Given date must follow the YYYY-MM-DD format";
			return;
		}
		
		try {
			Date newDate = dateFormatter.parse(dateOfBirth);
			
			this.dateOfBirth = newDate;
		} catch (Exception e) {
			this.errorMsg = "Given date not a valid date";
		}
	}
	
	/**
	 * Setter for dateOfBirth.
	 * Ensures the given date can be converted into a "Date" object.
	 * 
	 * @param dateOfBirth
	 */
	public void setDateOfBirth(java.sql.Date dateOfBirth) {
		this.dateOfBirth = new Date(dateOfBirth.getTime());
	}

	/**
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Default setter for firstName.
	 * Ensures the given first name is not an empty string.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		
		if (firstName.length() < 1) {
			this.errorMsg = "Given first name must not be an empty string";
		} else {
			this.firstName = firstName;
		}
	}

	/**
	 * @return
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Default setter for surname.
	 * Ensures the given surname is not an empty string.
	 * @param surname
	 */
	public void setSurname(String surname) {
		
		if (surname.length() < 1) {
			this.errorMsg = "Given surname must not be an empty string";
		} else {
			this.surname = surname;
		}
	}
	
	/**
	 * Unused.
	 * 
	 * @return
	 */
	public float getSalary() {
		return salary;
	}

	/**
	 * Default setter for salary.
	 * Converts the given salary into a string, and is not 0.
	 * 
	 * @param salary
	 */
	public void setSalary(String salary) {
		
		if (salary == "") {
			this.errorMsg = "Given salary must not be an empty value";
			return;
		}
		
		try {
			float newSalary = Float.parseFloat(salary);
			if (newSalary == 0) {
				this.errorMsg = "Given salary should not be equal zero";
				return;
			}
			
			this.salary = newSalary;
		} catch (Exception e) {
			this.errorMsg = "Letters should not exist within salary";
		}
	}
	
	/**
	 * Setter for salary.
	 * Ensures the given salary is not 0.
	 * 
	 * @param salary
	 */
	public void setSalary(float salary) {
		
		if (salary == 0) {
			this.errorMsg = "Given salary should not be equal zero";
			return;
		}
		
		this.salary = salary;
	}
	
	/**
	 * Unused.
	 * 
	 * @return
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * Setter for gender.
	 * Converts the given gender into a "char" value.
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		
		char newGender = gender.charAt(0);
		if (newGender != 'M' && newGender != 'F') {
			this.errorMsg = "Given gender not a valid value";
			return;
		}
		
		this.gender = newGender;
	}
	
	/**
	 * Returns the most recent error message, if there are validation errors.
	 * 
	 * @return
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Compiles and returns a string value of the employee's details.
	 * 
	 * @return
	 */
	public String toString() {
		return ("SSN: " + this.socialSecurityNumber + ", DoB: " + this.dateOfBirth + ", name: " + this.firstName + " " + this.surname + " (" + this.gender + "), salary: " + this.salary);
	}
	
	/**
	 * Checks if the employee details does not meet the validation requirements for the SQL database schema.
	 * 
	 * @return
	 */
	public boolean isInvalid() {
		
		return (
				this.socialSecurityNumber == 0 ||
				this.dateOfBirth == null ||
				this.firstName == null ||
				this.surname == null ||
				this.salary == 0 ||
				this.gender == 'N'
		);
	}

	/**
	 * Searches the array list of employees for any matching;
	 * 	- first name
	 * 	- surname
	 * 	- social security numbers
	 * 
	 * and returns the first one it finds.
	 * 
	 * @param surnameOrSSN
	 * @param employees
	 * @return
	 */
	public static Employee searchBySurnameOrSSN(String surnameOrSSN, ArrayList<Employee> employees) {
		
		int employeeCount = employees.size();
		for (int i = 0; i < employeeCount; i += 1) {
			Employee employee = employees.get(i);
			
			if (
					employee.getFirstName() == surnameOrSSN ||
					employee.getSurname() == surnameOrSSN ||
					Integer.toString(employee.getSocialSecurityNumber()) == surnameOrSSN) {
				return employee;
			};
		}
		
		return null;
	}
}
