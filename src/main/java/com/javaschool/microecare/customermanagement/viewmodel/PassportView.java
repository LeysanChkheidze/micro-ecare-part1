package com.javaschool.microecare.customermanagement.viewmodel;

import com.javaschool.microecare.commonentitymanagement.service.CommonEntityService;
import com.javaschool.microecare.customermanagement.dao.Passport;
import com.javaschool.microecare.customermanagement.dto.PassportDTO;

import java.time.LocalDate;

public class PassportView {
    private String passportType;
    private String documentID;
    private String issueDate;

    public PassportView(Passport passport) {
        this.passportType = passport.getPassportType().getDisplayName();
        this.documentID = passport.getDocumentID();
        this.issueDate = passport.getIssueDate().format(CommonEntityService.dateFormatter);
    }

    public PassportView(PassportDTO passportDTO) {
        this.passportType = passportDTO.getPassportType().getDisplayName();
        this.documentID = passportDTO.getDocumentID();
        this.issueDate = passportDTO.getIssueDate();
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return passportType + " number: " + documentID + ", issued " + issueDate;
    }
}
