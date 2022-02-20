package com.javaschool.microecare.customermanagement.dto;

import com.javaschool.microecare.customermanagement.service.PassportType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PassportDTO {
    @NotBlank(message = "{field.mandatory.msg}")
    PassportType passportType;

    @NotBlank(message = "{field.mandatory.msg}")
    @Size(min = 5, max = 10, message = "{passport.document_id.size.msg}")
    private String documentID;

    @NotBlank(message = "{field.mandatory.msg}")
    private String issueDate;

    public PassportDTO() {
    }

    public PassportDTO(PassportType passportType, String documentID, String issueDate) {
        this.passportType = passportType;
        this.documentID = documentID;
        this.issueDate = issueDate;
    }

    public PassportType getPassportType() {
        return passportType;
    }

    public void setPassportType(PassportType passportType) {
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
}
