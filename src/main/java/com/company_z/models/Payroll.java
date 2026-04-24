package com.company_z.models;

import java.time.LocalDate;

public class Payroll {
    private int payrollID;
    private int empID;
    private LocalDate payDate;
    private double grossPay;
    private double netPay;
    private double deductions;

    public Payroll() {}

    public Payroll(int empID, LocalDate payDate, double grossPay, double netPay, double deductions) {
        this.empID = empID;
        this.payDate = payDate;
        this.grossPay = grossPay;
        this.netPay = netPay;
        this.deductions = deductions;
    }

    public int getPayrollID() { return payrollID; }
    public void setPayrollID(int payrollID) { this.payrollID = payrollID; }

    public int getEmpID() { return empID; }
    public void setEmpID(int empID) { this.empID = empID; }

    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }

    public double getGrossPay() { return grossPay; }
    public void setGrossPay(double grossPay) { this.grossPay = grossPay; }

    public double getNetPay() { return netPay; }
    public void setNetPay(double netPay) { this.netPay = netPay; }

    public double getDeductions() { return deductions; }
    public void setDeductions(double deductions) { this.deductions = deductions; }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollID=" + payrollID +
                ", empID=" + empID +
                ", payDate=" + payDate +
                ", grossPay=" + grossPay +
                ", netPay=" + netPay +
                ", deductions=" + deductions +
                '}';
    }
}