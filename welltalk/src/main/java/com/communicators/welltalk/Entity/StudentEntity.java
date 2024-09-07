package com.communicators.welltalk.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDate;

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

    private String specificAddress;
    private String parentGuardianName;
    private String parentGuardianContactNumber;
    // private String barangay;
    // private String city;
    // private String province;
    // private String zipCode;


    public StudentEntity() {

    }

    public StudentEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String image, String role,String college, String program, int year, LocalDate birthDate,
            String contactNumber, String specificAddress, /* String barangay, String city, String province,
            String zipCode,*/ String parentGuardianName, String parentGuardianContactNumber) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, image, role, college, program);
        this.year = year;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.specificAddress = specificAddress;
        // this.barangay = barangay;
        // this.city = city;
        // this.province = province;
        // this.zipCode = zipCode;
        this.parentGuardianContactNumber = parentGuardianContactNumber;
        this.parentGuardianName = parentGuardianName;
    }

    public StudentEntity(int teacherId, String institutionalEmail, String idNumber, String firstName, String lastName,
            String gender, String password, String role, String college, String program, int year, LocalDate birthDate,
            String contactNumber, String specificAddress, /*String barangay, String city, String province,
            String zipCode, */String parentGuardianName, String parentGuardianContactNumber) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, role, college, program);
        // this.college = college;
        // this.program = program;
        this.year = year;
        this.birthDate = birthDate;
        this.contactNumber = contactNumber;
        this.specificAddress = specificAddress;
        // this.barangay = barangay;
        // this.city = city;
        // this.province = province;
        // this.zipCode = zipCode;
        this.parentGuardianContactNumber = parentGuardianContactNumber;
        this.parentGuardianName = parentGuardianName;
    }

    // public String getCollege() {
    //     return college;
    // }

    // public void setCollege(String college) {
    //     this.college = college;
    // }

    // public String getProgram() {
    //     return program;
    // }

    // public void setProgram(String program) {
    //     this.program = program;
    // }

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

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
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

    // public String getBarangay() {
    //     return barangay;
    // }

    // public void setBarangay(String barangay) {
    //     this.barangay = barangay;
    // }

    // public String getCity() {
    //     return city;
    // }

    // public void setCity(String city) {
    //     this.city = city;
    // }

    // public String getProvince() {
    //     return province;
    // }

    // public void setProvince(String province) {
    //     this.province = province;
    // }

    // public String getZipCode() {
    //     return zipCode;
    // }

    // public void setZipCode(String zipCode) {
    //     this.zipCode = zipCode;
    // }

}
