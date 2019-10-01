/***
 * Class for storing basic Employee data.
 * Follows the SQL schema specification for an "employees" object, therefore this class provides pre-validation.
 */

package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Employee {
	
	// Giving default values to easily check if employee object passes validation
	private int socialSecurityNumber = 0;
	private Date dateOfBirth = null;
	private String firstName = null;
	private String surname = null;
	private float salary = 0;
	private char gender = 'N';
	
	// Storing the most recent error message for validation handling
	private String errorMsg;

	public Employee(
			String socialSecurityNumber,
			LocalDate dateOfBirth,
			String firstName,
			String surname,
			String salary,
			String gender) {
		this.setSocialSecurityNumber(socialSecurityNumber);
		this.setDateOfBirth(dateOfBirth);
		this.setFirstName(firstName);
		this.setSurname(surname);
		this.setSalary(salary);
		this.setGender(gender);
	};
	
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

	public int getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		// Ensuring the social security number is a 9 digit integer
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
	
	public void setSocialSecurityNumber(int socialSecurityNumber) {
		if (socialSecurityNumber < 100000000) {
			this.errorMsg = "Social security number must be at-least 9 digits long";
			return;
		}
		
		this.socialSecurityNumber = socialSecurityNumber;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		try {
			// Converting LocalDate --> Date
			Date newDate = java.sql.Date.valueOf(dateOfBirth);
			this.dateOfBirth = newDate;
		} catch (Exception e) {
			this.errorMsg = "Given date not a valid date";
		}
	}
	
	public void setDateOfBirth(java.sql.Date dateOfBirth) {
		this.dateOfBirth = new Date(dateOfBirth.getTime());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName.length() < 1) {
			this.errorMsg = "Given first name must not be an empty string";
		} else {
			this.firstName = firstName;
		}
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if (surname.length() < 1) {
			this.errorMsg = "Given surname must not be an empty string";
		} else {
			this.surname = surname;
		}
	}
	
	public float getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		// Ensuring salary is a valid float value
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
	
	public void setSalary(float salary) {
		if (salary == 0) {
			this.errorMsg = "Given salary should not be equal zero";
			return;
		}
		
		this.salary = salary;
	}
	
	public char getGender() {
		return gender;
	}

	public void setGender(String gender) {
		// Converting String --> Char
		char newGender = gender.charAt(0);
		if (newGender != 'M' && newGender != 'F') {
			this.errorMsg = "Given gender not a valid value";
			return;
		}
		
		this.gender = newGender;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String toString() {
		return ("SSN: " + this.socialSecurityNumber + ", DoB: " + this.dateOfBirth + ", name: " + this.firstName + " " + this.surname + " (" + this.gender + "), salary: " + this.salary);
	}
	
	public boolean isInvalid() {
		// Checks if the employee passes the validation for SQL insertion		
		return (
				this.socialSecurityNumber == 0 ||
				this.dateOfBirth == null ||
				this.firstName == null ||
				this.surname == null ||
				this.salary == 0 ||
				this.gender == 'N'
		);
	}

	public static Employee searchBySurnameOrSSN(String surnameOrSSN, ArrayList<Employee> employees) {
		// Searches for an employee from the given list based on the given surname or SSN
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
