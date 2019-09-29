/***
 * Class for storing basic Employee data.
 * Follows the SQL schema specification for an "employees" object, therefore this class provides pre-validation.
 */

package application;

import java.util.ArrayList;
import java.util.Date;

public class Employee {
	
	private String socialSecurityNumber;
	private Date dateOfBirth;
	private String firstName;
	private String surname;
	private float salary;
	private String gender;

	public Employee(
			String socialSecurityNumber,
			Date dateOfBirth,
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
	};
	
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		if (socialSecurityNumber.length() >= 9) {
			// Should be at-least 9 digits
			this.socialSecurityNumber = socialSecurityNumber;
		}
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName.length() > 1) {
			this.firstName = firstName;
		}
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if (surname.length() > 1) {
			this.surname = surname;
		}
	}
	
	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		if (salary > 0) {
			this.salary = salary;
		}
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if (gender == "M" || gender == "F") {
			this.gender = gender;
		}
	}

	public static Employee searchBySurnameOrSSN(String surnameOrSSN, ArrayList<Employee> employees) {
		int employeeCount = employees.size();
		for (int i = 0; i < employeeCount; i += 1) {
			Employee employee = employees.get(i);
			
			if (
					employee.getSurname() == surnameOrSSN ||
					employee.getSocialSecurityNumber() == surnameOrSSN) {
				return employee;
			};
		}
		
		return null;
	}
}
