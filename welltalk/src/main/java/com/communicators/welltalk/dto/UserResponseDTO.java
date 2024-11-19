package com.communicators.welltalk.dto;

import com.communicators.welltalk.Entity.Role;

public class UserResponseDTO {
  private int id;
  private String institutionalEmail;
  private String firstName;
  private String lastName;
  private String image;
  private Role role;
  private String idNumber;

  public UserResponseDTO() {
  }
  
  public UserResponseDTO( int id,String institutionalEmail, String firstName, String lastName, String image,
      Role role, String idNumber) {
    
    this.id = id;
    this.institutionalEmail = institutionalEmail;
    this.firstName = firstName;
    this.lastName = lastName;
    this.image = image;
    this.role = role;
    this.idNumber = idNumber;
    
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

  
}
