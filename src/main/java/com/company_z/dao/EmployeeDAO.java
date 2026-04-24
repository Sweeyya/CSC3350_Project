package com.company_z.dao;

import com.company_z.database.DatabaseConnection;
import com.company_z.models.Employee;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // Search employee by empID
    public Employee searchByEmpID(int empID) throws SQLException {
        String query = "SELECT * FROM employees WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, empID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }
        }
        return null;
    }

    // Search employee by SSN
    public Employee searchBySSN(String ssn) throws SQLException {
        String query = "SELECT * FROM employees WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ssn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }
        }
        return null;
    }

    // Search employee by last name
    public List<Employee> searchByLastName(String lastName) throws SQLException {
        String query = "SELECT * FROM employees WHERE last_name LIKE ?";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + lastName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }
        return employees;
    }

    // Search employee by DOB
    public List<Employee> searchByDOB(LocalDate dob) throws SQLException {
        String query = "SELECT * FROM employees WHERE dob = ?";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(dob));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }
        return employees;
    }

    // Get all employees
    public List<Employee> getAllEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        }
        return employees;
    }

    // Insert new employee
    public boolean insertEmployee(Employee emp) throws SQLException {
        String query = "INSERT INTO employees (first_name, last_name, dob, ssn, mobile_phone, " +
                "emergency_contact_name, emergency_contact_phone, salary, hire_date, addressID, divID, job_titleID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getLastName());
            stmt.setDate(3, Date.valueOf(emp.getDob()));
            stmt.setString(4, emp.getSsn());
            stmt.setString(5, emp.getMobilePhone());
            stmt.setString(6, emp.getEmergencyContactName());
            stmt.setString(7, emp.getEmergencyContactPhone());
            stmt.setDouble(8, emp.getSalary());
            stmt.setDate(9, Date.valueOf(emp.getHireDate()));
            stmt.setInt(10, emp.getAddressID());
            stmt.setInt(11, emp.getDivID());
            stmt.setInt(12, emp.getJobTitleID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Update employee data
    public boolean updateEmployee(int empID, String fieldName, Object newValue) throws SQLException {
        // Validate that field exists in employees table
        List<String> validFields = List.of("first_name", "last_name", "dob", "ssn", "mobile_phone",
                "emergency_contact_name", "emergency_contact_phone", "salary", "hire_date", "addressID", "divID", "job_titleID");

        if (!validFields.contains(fieldName)) {
            System.err.println("Error: Column '" + fieldName + "' does not exist in employees table.");
            return false;
        }

        // Check if employee exists
        if (searchByEmpID(empID) == null) {
            System.err.println("Error: Employee with ID " + empID + " does not exist.");
            return false;
        }

        String query = "UPDATE employees SET " + fieldName + " = ? WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, newValue);
            stmt.setInt(2, empID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete employee by empID
    public boolean deleteEmployee(int empID) throws SQLException {
        if (searchByEmpID(empID) == null) {
            System.err.println("Error: Employee with ID " + empID + " does not exist.");
            return false;
        }

        String query = "DELETE FROM employees WHERE empID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, empID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Update salary by percentage for employees below threshold
    public int updateSalariesByPercentage(double threshold, double percentageIncrease) throws SQLException {
        if (percentageIncrease < 0) {
            System.err.println("Error: Percentage increase must be positive (no salary decreases allowed).");
            return 0;
        }

        String query = "UPDATE employees SET salary = salary * (1 + ?) WHERE salary < ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, percentageIncrease);
            stmt.setDouble(2, threshold);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Successfully updated " + rowsAffected + " employee salaries.");
            return rowsAffected;
        }
    }

    // Helper method to map ResultSet to Employee object
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmpID(rs.getInt("empID"));
        emp.setFirstName(rs.getString("first_name"));
        emp.setLastName(rs.getString("last_name"));
        emp.setDob(rs.getDate("dob").toLocalDate());
        emp.setSsn(rs.getString("ssn"));
        emp.setMobilePhone(rs.getString("mobile_phone"));
        emp.setEmergencyContactName(rs.getString("emergency_contact_name"));
        emp.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
        emp.setSalary(rs.getDouble("salary"));
        emp.setHireDate(rs.getDate("hire_date").toLocalDate());
        emp.setAddressID(rs.getInt("addressID"));
        emp.setDivID(rs.getInt("divID"));
        emp.setJobTitleID(rs.getInt("job_titleID"));
        return emp;
    }
}