package com.communicators.welltalk.dto;

import java.time.LocalDateTime;

import com.communicators.welltalk.Entity.Role;

public class CounselorResponseDTO {
  private int id;
  private String institutionalEmail;
  private String firstName;
  private String lastName;
  private String image;
  private Role role;
  private String idNumber;
  private String college;
  private String program;
  private String assignedYear;
  private LocalDateTime dateOfCreation;



  public CounselorResponseDTO() {
  }
  
  public CounselorResponseDTO( int id,String institutionalEmail, String firstName, String lastName, String image, Role role, String idNumber, String college, String program,String assignedYear, LocalDateTime dateOfCreation) {
    
    this.id = id;
    this.institutionalEmail = institutionalEmail;
    this.firstName = firstName;
    this.lastName = lastName;
    this.image = image;
    this.role = role;
    this.idNumber = idNumber;
    this.college = college;
    this.program = program;
    this.assignedYear = assignedYear;
    this.dateOfCreation = dateOfCreation;
  
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

  public String getAssignedYear() {
    return assignedYear;
  }

  public void setAssignedYear(String assignedYear) {
    this.assignedYear = assignedYear;
  }

  public LocalDateTime getDateOfCreation() {
    return dateOfCreation;
  }

  public void setDateOfCreation(LocalDateTime dateOfCreation) {
    this.dateOfCreation = dateOfCreation;
  }

}
