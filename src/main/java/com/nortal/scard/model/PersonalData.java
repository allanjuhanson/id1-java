package com.nortal.scard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

public class PersonalData {

    private String lastName;
    private String firstName;
    private String gender;
    private String citizenship;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String personalCode;
    private String documentNumber;
    private LocalDate expiryDate;
    private LocalDate issuedDate;
    private String residencePermitType;
    private String comment1;
    private String comment2;
    private String comment3;
    private String comment4;
    private String comment5;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getResidencePermitType() {
        return residencePermitType;
    }

    public void setResidencePermitType(String residencePermitType) {
        this.residencePermitType = residencePermitType;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
    }

    public String getComment4() {
        return comment4;
    }

    public void setComment4(String comment4) {
        this.comment4 = comment4;
    }

    public String getComment5() {
        return comment5;
    }

    public void setComment5(String comment5) {
        this.comment5 = comment5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("lastName", lastName)
            .append("firstName", firstName)
            .append("gender", gender)
            .append("citizenship", citizenship)
            .append("dateOfBirth", dateOfBirth)
            .append("placeOfBirth", placeOfBirth)
            .append("personalCode", personalCode)
            .append("documentNumber", documentNumber)
            .append("expiryDate", expiryDate)
            .append("issuedDate", issuedDate)
            .append("residencePermitType", residencePermitType)
            .append("comment1", comment1)
            .append("comment2", comment2)
            .append("comment3", comment3)
            .append("comment4", comment4)
            .append("comment5", comment5)
            .toString();
    }
}
