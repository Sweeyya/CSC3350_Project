package com.company_z.models;

import java.time.LocalDate;

public class Employee {
    private int empID;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String ssn;
    private String mobilePhone;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private double salary;
    private LocalDate hireDate;
    private int addressID;
    private int divID;
    private int jobTitleID;

    public Employee() {}

    public Employee(String firstName, String lastName, LocalDate dob, String ssn,
                   String mobilePhone, String emergencyContactName, String emergencyContactPhone,
                   double salary, LocalDate hireDate, int addressID, int divID, int jobTitleID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.ssn = ssn;
        this.mobilePhone = mobilePhone;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.salary = salary;
        this.hireDate = hireDate;
        this.addressID = addressID;
        this.divID = divID;
        this.jobTitleID = jobTitleID;
    }

    // Getters and Setters
    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getMobilePhone() { return mobilePhone; }
    public void setMobilePhone(String mobilePhone) { this.mobilePhone = mobilePhone; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public int getAddressID() { return addressID; }
    public void setAddressID(int addressID) { this.addressID = addressID; }

    public int getDivID() { return divID; }
    public void setDivID(int divID) { this.divID = divID; }

    public int getJobTitleID() { return jobTitleID; }
    public void setJobTitleID(int jobTitleID) { this.jobTitleID = jobTitleID; }

    @Override
    public String toString() {
        return "Employee{" +
                "empID=" + empID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", ssn='" + ssn + '\'' +
                ", salary=" + salary +
                '}';
    }
}