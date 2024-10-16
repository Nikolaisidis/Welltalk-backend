package com.communicators.welltalk.Entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblstudent")
// @PrimaryKeyJoinColumn(name = "id")
public class StudentEntity extends UserEntity {

    // @Column(name = "college")
    // private String college;

    // @Column(name = "program")
    // private String program;

    @Column(name = "year")
    private int year;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Column(name = "contactNumber")
    private String contactNumber;

    private String permanentAddress;
    private String currentAddress;
    private String parentGuardianName;
    private String parentGuardianContactNumber;
    private String parentGuardianRelationship;

    public StudentEntity() {

    }

    public StudentEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String image, String role, String college, String program, int year, LocalDate birthDate,
            String contactNumber, String permanentAddress, String currentAddress, String parentGuardianName,
            String parentGuardianContactNumber, String parentGuardianRelationship) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, image, role, college, program);
        this.year = year;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.permanentAddress = permanentAddress;
        this.currentAddress = currentAddress;
        this.parentGuardianContactNumber = parentGuardianContactNumber;
        this.parentGuardianName = parentGuardianName;
        this.parentGuardianRelationship = parentGuardianRelationship;
    }

    public StudentEntity(/* int teacherId, */ String institutionalEmail, String idNumber, String firstName,
            String lastName,
            String gender, String password, String role, String college, String program, int year, LocalDate birthDate,
            String contactNumber, String permanentAddress, String currentAddress, String parentGuardianName,
            String parentGuardianContactNumber, String parentGuardianRelationship) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, role, college, program);  
        this.year = year;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.permanentAddress = permanentAddress;
        this.currentAddress = currentAddress;
        this.parentGuardianContactNumber = parentGuardianContactNumber;
        this.parentGuardianName = parentGuardianName;
        this.parentGuardianRelationship = parentGuardianRelationship;
    }

    public String getParentGuardianName() {
        return parentGuardianName;
    }

    public void setParentGuardianName(String parentGuardianName) {
        this.parentGuardianName = parentGuardianName;
    }

    public String getParentGuardianContactNumber() {
        return parentGuardianContactNumber;
    }

    public void setParentGuardianContactNumber(String parentGuardianContactNumber) {
        this.parentGuardianContactNumber = parentGuardianContactNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getParentGuardianRelationship() {
        return parentGuardianRelationship;
    }

    public void setParentGuardianRelationship(String parentGuardianRelationship) {
        this.parentGuardianRelationship = parentGuardianRelationship;
    }
}
