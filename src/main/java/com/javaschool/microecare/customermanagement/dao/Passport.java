package com.javaschool.microecare.customermanagement.dao;

import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.customermanagement.service.PassportType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "PASSPORTS")
public class Passport extends BaseEntity {
    @Column(name = "PASSPORT_TYPE")
    @NotBlank
    PassportType passportType;

    @Column(name = "DOCUMENT_ID")
    @NotBlank
    @Size(min = 5, max = 10)
    private String documentID;

    @Column(name = "ISSUE_DATE")
    @NotBlank
    private Date issueDate;

    @OneToOne(mappedBy = "passport")
    private Customer customer;

    public Passport() {
        super();
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
