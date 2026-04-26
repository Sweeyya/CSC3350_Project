USE company_z;

-- Sample data for Company Z Employee Management System
-- Run this AFTER database_schema.sql

-- States
INSERT INTO states (state_abbreviation) VALUES
('GA'),
('FL'),
('CA');

-- Cities
INSERT INTO cities (city_name, stateID) VALUES
('Atlanta', 1),
('Lilburn', 1),
('Orlando', 2),
('Los Angeles', 3);

-- Addresses
INSERT INTO addresses (street, cityID, stateID, zip) VALUES
('100 Peachtree St', 1, 1, '30303'),
('200 Main St', 2, 1, '30047'),
('300 Lake Ave', 3, 2, '32801'),
('400 Sunset Blvd', 4, 3, '90028'),
('500 College Ave', 1, 1, '30302');

-- Divisions
INSERT INTO divisions (division_name) VALUES
('Human Resources'),
('Information Technology'),
('Finance'),
('Sales'),
('Management');

-- Job Titles
INSERT INTO job_titles (job_title_name) VALUES
('HR Admin'),
('Software Developer'),
('Accountant'),
('Sales Representative'),
('Project Manager');

-- Employees
INSERT INTO employees
(first_name, last_name, dob, ssn, mobile_phone, emergency_contact_name, emergency_contact_phone, salary, hire_date, addressID, divID, job_titleID)
VALUES
('Sweeya', 'Ghanta', '1998-04-12', '111-22-3333', '404-111-1000', 'Ravi Ghanta', '404-111-2000', 72000.00, '2024-01-10', 1, 2, 2),
('Jamison', 'Braxton', '1997-08-25', '222-33-4444', '404-222-1000', 'Taylor Braxton', '404-222-2000', 65000.00, '2024-02-15', 2, 4, 4),
('Christian', 'Wright', '1996-11-03', '333-44-5555', '404-333-1000', 'Morgan Wright', '404-333-2000', 78000.00, '2023-09-01', 3, 3, 3),
('Jamila', 'Doughty', '1999-02-18', '444-55-6666', '404-444-1000', 'Avery Doughty', '404-444-2000', 70000.00, '2025-03-20', 4, 1, 1),
('Tien', 'Pham', '2002-10-20', '555-66-7777', '404-555-1000', 'Huu Pham', '404-555-2000', 80000.00, '2024-06-05', 5, 5, 5);

-- Employee Division History
INSERT INTO employee_division
(empID, divID, assignment_date)
VALUES
(1, 2, '2024-01-10'),
(2, 4, '2024-02-15'),
(3, 3, '2023-09-01'),
(4, 1, '2025-03-20'),
(5, 5, '2024-06-05');

-- Employee Job Title History
INSERT INTO employee_job_titles
(empID, job_titleID, assignment_date)
VALUES
(1, 2, '2024-01-10'),
(2, 4, '2024-02-15'),
(3, 3, '2023-09-01'),
(4, 1, '2025-03-20'),
(5, 5, '2024-06-05');

-- Payroll History
INSERT INTO payroll
(empID, pay_date, gross_pay, net_pay, deductions)
VALUES
(1, '2026-04-15', 3000.00, 2450.00, 550.00),
(1, '2026-03-15', 3000.00, 2450.00, 550.00),

(2, '2026-04-15', 2708.33, 2200.00, 508.33),
(2, '2026-03-15', 2708.33, 2200.00, 508.33),

(3, '2026-04-15', 3250.00, 2650.00, 600.00),
(3, '2026-03-15', 3250.00, 2650.00, 600.00),

(4, '2026-04-15', 2916.67, 2375.00, 541.67),
(4, '2026-03-15', 2916.67, 2375.00, 541.67),

(5, '2026-04-15', 3333.33, 2725.00, 608.33),
(5, '2026-03-15', 3333.33, 2725.00, 608.33);