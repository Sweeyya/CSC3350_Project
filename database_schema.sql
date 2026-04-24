-- Company Z Employee Management System Database Schema
-- Created: April 24, 2026

-- Create States table
CREATE TABLE states (
    stateID INT PRIMARY KEY AUTO_INCREMENT,
    state_abbreviation VARCHAR(2) NOT NULL UNIQUE
);

-- Create Cities table
CREATE TABLE cities (
    cityID INT PRIMARY KEY AUTO_INCREMENT,
    city_name VARCHAR(25) NOT NULL,
    stateID INT NOT NULL,
    FOREIGN KEY (stateID) REFERENCES states(stateID)
);

-- Create Addresses table
CREATE TABLE addresses (
    addressID INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(100) NOT NULL,
    cityID INT NOT NULL,
    stateID INT NOT NULL,
    zip VARCHAR(10) NOT NULL,
    FOREIGN KEY (cityID) REFERENCES cities(cityID),
    FOREIGN KEY (stateID) REFERENCES states(stateID)
);

-- Create Divisions table
CREATE TABLE divisions (
    divID INT PRIMARY KEY AUTO_INCREMENT,
    division_name VARCHAR(50) NOT NULL
);

-- Create Job Titles table
CREATE TABLE job_titles (
    job_titleID INT PRIMARY KEY AUTO_INCREMENT,
    job_title_name VARCHAR(50) NOT NULL
);

-- Create Employees table
CREATE TABLE employees (
    empID INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE NOT NULL,
    ssn VARCHAR(11) NOT NULL UNIQUE,
    mobile_phone VARCHAR(15),
    emergency_contact_name VARCHAR(50),
    emergency_contact_phone VARCHAR(15),
    salary DECIMAL(10, 2) NOT NULL,
    hire_date DATE NOT NULL,
    addressID INT NOT NULL,
    divID INT NOT NULL,
    job_titleID INT NOT NULL,
    FOREIGN KEY (addressID) REFERENCES addresses(addressID),
    FOREIGN KEY (divID) REFERENCES divisions(divID),
    FOREIGN KEY (job_titleID) REFERENCES job_titles(job_titleID),
    INDEX idx_empID (empID),
    INDEX idx_ssn (ssn),
    INDEX idx_last_name (last_name)
);

-- Create Payroll table
CREATE TABLE payroll (
    payroll_id INT PRIMARY KEY AUTO_INCREMENT,
    empID INT NOT NULL,
    pay_date DATE NOT NULL,
    gross_pay DECIMAL(10, 2) NOT NULL,
    net_pay DECIMAL(10, 2) NOT NULL,
    deductions DECIMAL(10, 2),
    FOREIGN KEY (empID) REFERENCES employees(empID),
    INDEX idx_empID (empID),
    INDEX idx_pay_date (pay_date)
);

-- Create Employee Division table (for tracking division assignments)
CREATE TABLE employee_division (
    empID INT NOT NULL,
    divID INT NOT NULL,
    assignment_date DATE NOT NULL,
    PRIMARY KEY (empID, divID),
    FOREIGN KEY (empID) REFERENCES employees(empID),
    FOREIGN KEY (divID) REFERENCES divisions(divID)
);

-- Create Employee Job Titles table (for tracking job title history)
CREATE TABLE employee_job_titles (
    empID INT NOT NULL,
    job_titleID INT NOT NULL,
    assignment_date DATE NOT NULL,
    PRIMARY KEY (empID, job_titleID),
    FOREIGN KEY (empID) REFERENCES employees(empID),
    FOREIGN KEY (job_titleID) REFERENCES job_titles(job_titleID)
);