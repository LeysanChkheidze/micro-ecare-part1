package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.customermanagement.dao.Passport;

import java.time.LocalDate;

public class PassportView {
    String passportType;
    private String documentID;
    private LocalDate issueDate;

    public PassportView(Passport passport) {
        this.passportType = passport.getPassportType().getDisplayName();
        this.documentID = passport.getDocumentID();
        this.issueDate = passport.getIssueDate();
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
}
