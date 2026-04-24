package com.company_z.dao;

import com.company_z.database.DatabaseConnection;
import com.company_z.models.Payroll;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {

    // Get payroll record by payroll ID
    public Payroll getPayrollByID(int payrollID) throws SQLException {
        String query = "SELECT * FROM payroll WHERE payroll_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, payrollID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPayroll(rs);
            }
        }
        return null;
    }

    // Get all payroll records for an employee
    public List<Payroll> getPayrollByEmpID(int empID) throws SQLException {
        String query = "SELECT * FROM payroll WHERE empID = ? ORDER BY pay_date DESC";
        List<Payroll> payrollList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, empID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payrollList.add(mapResultSetToPayroll(rs));
            }
        }
        return payrollList;
    }

    // Get payroll records within a date range
    public List<Payroll> getPayrollByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        String query = "SELECT * FROM payroll WHERE pay_date BETWEEN ? AND ? ORDER BY pay_date DESC";
        List<Payroll> payrollList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                payrollList.add(mapResultSetToPayroll(rs));
            }
        }
        return payrollList;
    }

    // Insert new payroll record
    public boolean insertPayroll(Payroll payroll) throws SQLException {
        String query = "INSERT INTO payroll (empID, pay_date, gross_pay, net_pay, deductions) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, payroll.getEmpID());
            stmt.setDate(2, Date.valueOf(payroll.getPayDate()));
            stmt.setDouble(3, payroll.getGrossPay());
            stmt.setDouble(4, payroll.getNetPay());
            stmt.setDouble(5, payroll.getDeductions());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Update payroll record
    public boolean updatePayroll(int payrollID, double grossPay, double netPay, double deductions) throws SQLException {
        String query = "UPDATE payroll SET gross_pay = ?, net_pay = ?, deductions = ? WHERE payroll_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, grossPay);
            stmt.setDouble(2, netPay);
            stmt.setDouble(3, deductions);
            stmt.setInt(4, payrollID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete payroll record
    public boolean deletePayroll(int payrollID) throws SQLException {
        String query = "DELETE FROM payroll WHERE payroll_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, payrollID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Get total pay for month by job title
    public List<Object[]> getTotalPayByJobTitle(int month, int year) throws SQLException {
        String query = "SELECT jt.job_title_name, SUM(p.gross_pay) as total_pay " +
                "FROM payroll p " +
                "JOIN employees e ON p.empID = e.empID " +
                "JOIN job_titles jt ON e.job_titleID = jt.job_titleID " +
                "WHERE MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ? " +
                "GROUP BY jt.job_title_name";
        List<Object[]> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Object[]{rs.getString("job_title_name"), rs.getDouble("total_pay")});
            }
        }
        return results;
    }

    // Get total pay for month by division
    public List<Object[]> getTotalPayByDivision(int month, int year) throws SQLException {
        String query = "SELECT d.division_name, SUM(p.gross_pay) as total_pay " +
                "FROM payroll p " +
                "JOIN employees e ON p.empID = e.empID " +
                "JOIN divisions d ON e.divID = d.divID " +
                "WHERE MONTH(p.pay_date) = ? AND YEAR(p.pay_date) = ? " +
                "GROUP BY d.division_name";
        List<Object[]> results = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Object[]{rs.getString("division_name"), rs.getDouble("total_pay")});
            }
        }
        return results;
    }

    // Helper method to map ResultSet to Payroll object
    private Payroll mapResultSetToPayroll(ResultSet rs) throws SQLException {
        Payroll payroll = new Payroll();
        payroll.setPayrollID(rs.getInt("payroll_id"));
        payroll.setEmpID(rs.getInt("empID"));
        payroll.setPayDate(rs.getDate("pay_date").toLocalDate());
        payroll.setGrossPay(rs.getDouble("gross_pay"));
        payroll.setNetPay(rs.getDouble("net_pay"));
        payroll.setDeductions(rs.getDouble("deductions"));
        return payroll;
    }
}