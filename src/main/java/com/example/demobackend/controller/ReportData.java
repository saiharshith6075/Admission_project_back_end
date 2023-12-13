package com.example.demobackend.controller;

public class ReportData {

	private final long totalAdmissions;
    private final long acceptedAdmissions;
    private final long rejectedAdmissions;

    public ReportData(long totalAdmissions, long acceptedAdmissions, long rejectedAdmissions) {
        this.totalAdmissions = totalAdmissions;
        this.acceptedAdmissions = acceptedAdmissions;
        this.rejectedAdmissions = rejectedAdmissions;
    }

    public long getTotalAdmissions() {
        return totalAdmissions;
    }

    public long getAcceptedAdmissions() {
        return acceptedAdmissions;
    }

    public long getRejectedAdmissions() {
        return rejectedAdmissions;
    }
}
