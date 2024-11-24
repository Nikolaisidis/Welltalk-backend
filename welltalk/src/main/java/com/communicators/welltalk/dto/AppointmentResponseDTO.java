package com.communicators.welltalk.dto;

import java.time.LocalDate;

public class AppointmentResponseDTO {
  private int appointmentId;
  private StudentResponseDTO student;
  private CounselorResponseDTO counselor;
  private LocalDate appointmentDate;
  private String appointmentStartTime;
  private String appointmentStatus;
  private String appointmentNotes;
  private String appointmentType;
  private String appointmentAdditionalNotes;
  private String appointmentPurpose;
  private LocalDate appointmentBooked;
  private LocalDate appointmentModified;

  public AppointmentResponseDTO(){

  }

  public AppointmentResponseDTO(int appointmentId, StudentResponseDTO student, CounselorResponseDTO counselor,
      LocalDate appointmentDate, String appointmentStartTime, String appointmentStatus, String appointmentNotes,
      String appointmentType, String appointmentAdditionalNotes, String appointmentPurpose, LocalDate appointmentBooked,
      LocalDate appointmentModified) {
    this.appointmentId = appointmentId;
    this.student = student;
    this.counselor = counselor;
    this.appointmentDate = appointmentDate;
    this.appointmentStartTime = appointmentStartTime;
    this.appointmentStatus = appointmentStatus;
    this.appointmentNotes = appointmentNotes;
    this.appointmentType = appointmentType;
    this.appointmentAdditionalNotes = appointmentAdditionalNotes;
    this.appointmentPurpose = appointmentPurpose;
    this.appointmentBooked = appointmentBooked;
    this.appointmentModified = appointmentModified;
  }

  public int getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
  }

  public StudentResponseDTO getStudent() {
    return student;
  }

  public void setStudent(StudentResponseDTO student) {
    this.student = student;
  }

  public CounselorResponseDTO getCounselor() {
    return counselor;
  }

  public void setCounselor(CounselorResponseDTO counselor) {
    this.counselor = counselor;
  }

  public LocalDate getAppointmentDate() {
    return appointmentDate;
  }

  public void setAppointmentDate(LocalDate appointmentDate) {
    this.appointmentDate = appointmentDate;
  }

  public String getAppointmentStartTime() {
    return appointmentStartTime;
  }

  public void setAppointmentStartTime(String appointmentStartTime) {
    this.appointmentStartTime = appointmentStartTime;
  }

  public String getAppointmentStatus() {
    return appointmentStatus;
  }

  public void setAppointmentStatus(String appointmentStatus) {
    this.appointmentStatus = appointmentStatus;
  }

  public String getAppointmentNotes() {
    return appointmentNotes;
  }

  public void setAppointmentNotes(String appointmentNotes) {
    this.appointmentNotes = appointmentNotes;
  }

  public String getAppointmentType() {
    return appointmentType;
  }

  public void setAppointmentType(String appointmentType) {
    this.appointmentType = appointmentType;
  }

  public String getAppointmentAdditionalNotes() {
    return appointmentAdditionalNotes;
  }

  public void setAppointmentAdditionalNotes(String appointmentAdditionalNotes) {
    this.appointmentAdditionalNotes = appointmentAdditionalNotes;
  }

  public String getAppointmentPurpose() {
    return appointmentPurpose;
  }

  public void setAppointmentPurpose(String appointmentPurpose) {
    this.appointmentPurpose = appointmentPurpose;
  }

  public LocalDate getAppointmentBooked() {
    return appointmentBooked;
  }

  public void setAppointmentBooked(LocalDate appointmentBooked) {
    this.appointmentBooked = appointmentBooked;
  }

  public LocalDate getAppointmentModified() {
    return appointmentModified;
  }

  public void setAppointmentModified(LocalDate appointmentModified) {
    this.appointmentModified = appointmentModified;
  }

 
  
 
}
