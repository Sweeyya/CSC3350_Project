package com.company_z.ui;

import com.company_z.dao.EmployeeDAO;
import com.company_z.dao.PayrollDAO;
import com.company_z.models.Employee;
import com.company_z.models.Payroll;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static EmployeeDAO employeeDAO = new EmployeeDAO();
    private static PayrollDAO payrollDAO = new PayrollDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Company Z Employee Management System ===\n");
        
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter choice (1-8): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            try {
                switch (choice) {
                    case 1:
                        searchEmployee();
                        break;
                    case 2:
                        insertEmployee();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        updateSalaries();
                        break;
                    case 6:
                        viewPayrollByEmployee();
                        break;
                    case 7:
                        viewPayrollReports();
                        break;
                    case 8:
                        running = false;
                        System.out.println("Exiting system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.\n");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Search Employee by ID");
        System.out.println("2. Insert New Employee");
        System.out.println("3. Update Employee Data");
        System.out.println("4. Delete Employee");
        System.out.println("5. Update Salaries by Percentage");
        System.out.println("6. View Payroll by Employee");
        System.out.println("7. View Payroll Reports");
        System.out.println("8. Exit");
    }

    private static void searchEmployee() throws Exception {
        System.out.print("Enter Employee ID: ");
        int empID = scanner.nextInt();
        scanner.nextLine();
        
        Employee emp = employeeDAO.searchByEmpID(empID);
        if (emp != null) {
            System.out.println("Found: " + emp);
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void insertEmployee() throws Exception {
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("DOB (YYYY-MM-DD): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine());
        System.out.print("SSN: ");
        String ssn = scanner.nextLine();
        System.out.print("Mobile Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Emergency Contact Name: ");
        String emergencyName = scanner.nextLine();
        System.out.print("Emergency Contact Phone: ");
        String emergencyPhone = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = scanner.nextDouble();
        System.out.print("Hire Date (YYYY-MM-DD): ");
        scanner.nextLine();
        LocalDate hireDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Address ID: ");
        int addressID = scanner.nextInt();
        System.out.print("Division ID: ");
        int divID = scanner.nextInt();
        System.out.print("Job Title ID: ");
        int jobTitleID = scanner.nextInt();
        scanner.nextLine();
        
        Employee emp = new Employee(firstName, lastName, dob, ssn, phone, emergencyName, emergencyPhone, salary, hireDate, addressID, divID, jobTitleID);
        if (employeeDAO.insertEmployee(emp)) {
            System.out.println("Employee inserted successfully!");
        } else {
            System.out.println("Failed to insert employee.");
        }
    }

    private static void updateEmployee() throws Exception {
        System.out.print("Enter Employee ID to update: ");
        int empID = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter field name to update (e.g., first_name, salary, etc.): ");
        String fieldName = scanner.nextLine();
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        
        if (employeeDAO.updateEmployee(empID, fieldName, newValue)) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Failed to update employee.");
        }
    }

    private static void deleteEmployee() throws Exception {
        System.out.print("Enter Employee ID to delete: ");
        int empID = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            if (employeeDAO.deleteEmployee(empID)) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Failed to delete employee.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private static void updateSalaries() throws Exception {
        System.out.print("Enter salary threshold: ");
        double threshold = scanner.nextDouble();
        System.out.print("Enter percentage increase (e.g., 0.1 for 10%): ");
        double percentage = scanner.nextDouble();
        scanner.nextLine();
        
        int updated = employeeDAO.updateSalariesByPercentage(threshold, percentage);
        System.out.println("Updated " + updated + " employee salaries.");
    }

    private static void viewPayrollByEmployee() throws Exception {
        System.out.print("Enter Employee ID: ");
        int empID = scanner.nextInt();
        scanner.nextLine();
        
        List<Payroll> payrolls = payrollDAO.getPayrollByEmpID(empID);
        if (payrolls.isEmpty()) {
            System.out.println("No payroll records found.");
        } else {
            System.out.println("Payroll records for Employee " + empID + ":");
            for (Payroll p : payrolls) {
                System.out.println(p);
            }
        }
    }

    private static void viewPayrollReports() throws Exception {
        System.out.println("1. Total Pay by Job Title");
        System.out.println("2. Total Pay by Division");
        System.out.print("Choose report (1-2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            List<Object[]> results = payrollDAO.getTotalPayByJobTitle(month, year);
            System.out.println("\nTotal Pay by Job Title:");
            for (Object[] row : results) {
                System.out.println(row[0] + ": $" + row[1]);
            }
        } else if (choice == 2) {
            List<Object[]> results = payrollDAO.getTotalPayByDivision(month, year);
            System.out.println("\nTotal Pay by Division:");
            for (Object[] row : results) {
                System.out.println(row[0] + ": $" + row[1]);
            }
        }
    }
}