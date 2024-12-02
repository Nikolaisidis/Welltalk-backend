package com.communicators.welltalk.dto;

import java.time.LocalDate;

public class AppointmentGetDateResponseDTO {
    private LocalDate appointmentDate;
    private String appointmentStartTime;
    private int studentId;

    public AppointmentGetDateResponseDTO() {
    }

    public AppointmentGetDateResponseDTO(LocalDate appointmentDate, String appointmentStartTime, int studentId) {
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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
}
