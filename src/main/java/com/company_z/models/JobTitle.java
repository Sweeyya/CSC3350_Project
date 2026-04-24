package com.company_z.models;

public class JobTitle {
    private int jobTitleID;
    private String jobTitleName;

    public JobTitle() {}

    public JobTitle(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public int getJobTitleID() { return jobTitleID; }
    public void setJobTitleID(int jobTitleID) { this.jobTitleID = jobTitleID; }

    public String getJobTitleName() { return jobTitleName; }
    public void setJobTitleName(String jobTitleName) { this.jobTitleName = jobTitleName; }

    @Override
    public String toString() {
        return "JobTitle{" +
                "jobTitleID=" + jobTitleID +
                ", jobTitleName='" + jobTitleName + '\'' +
                '}';
    }
}