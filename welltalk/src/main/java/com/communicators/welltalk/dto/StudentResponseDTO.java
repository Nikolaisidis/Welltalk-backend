package com.communicators.welltalk.dto;

import java.time.LocalDate;

import com.communicators.welltalk.Entity.Role;

public class StudentResponseDTO {
  private int id;
  private String institutionalEmail;
  private String firstName;
  private String lastName;
  private String image;
  private String gender;
  private Role role;
  private String idNumber;
  private String college;
  private String program;
  private int year;
  private LocalDate birthDate;
  private String contactNumber;
  private String permanentAddress;
  private String currentAddress;
  private String parentGuardianName;
  private String parentGuardianContactNumber;
  private String guardianRelationship;


  public StudentResponseDTO() {
  }
  
  public StudentResponseDTO( int id,String institutionalEmail, String firstName, String lastName, String image,
      Role role, String idNumber, String college, String program, int year, LocalDate birthDate, String contactNumber, String permanentAddress, String currentAddress, String parentGuardianName, String parentGuardianContactNumber, String guardianRelationship, String gender) {
    
    this.id = id;
    this.institutionalEmail = institutionalEmail;
    this.firstName = firstName;
    this.lastName = lastName;
    this.image = image;
    this.role = role;
    this.gender = gender;
    this.idNumber = idNumber;
    this.college = college;
    this.program = program;
    this.year = year;
    this.birthDate = birthDate;
    this.contactNumber = contactNumber;
    this.permanentAddress = permanentAddress;
    this.currentAddress = currentAddress;
    this.parentGuardianName = parentGuardianName;
    this.parentGuardianContactNumber = parentGuardianContactNumber;
    this.guardianRelationship = guardianRelationship;
  }

  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }
  
  public String getInstitutionalEmail() {
    return institutionalEmail;
  }
  public void setInstitutionalEmail(String institutionalEmail) {
    this.institutionalEmail = institutionalEmail;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getImage() {
    return image;
  }
  public void setImage(String image) {
    this.image = image;
  }
  public Role getRole() {
    return role;
  }
  public void setRole(Role role) {
    this.role = role;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getCollege() {
    return college;
  }

  public void setCollege(String college) {
    this.college = college;
  }

  public String getProgram() {
    return program;
  }

  public void setProgram(String program) {
    this.program = program;
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

  public String getGuardianRelationship() {
    return guardianRelationship;
  }

  public void setGuardianRelationship(String guardianRelationship) {
    this.guardianRelationship = guardianRelationship;
  }

  public String getGender() {
    return gender;
  }
  
  public void setGender(String gender){
    this.gender = gender;
  }

}
