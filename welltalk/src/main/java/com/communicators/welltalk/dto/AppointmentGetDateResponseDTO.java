package com.communicators.welltalk.dto;

import java.time.LocalDate;

public class AppointmentGetDateResponseDTO {
    private LocalDate appointmentDate;
    private String appointmentStartTime;

    public AppointmentGetDateResponseDTO() {
    }

    public AppointmentGetDateResponseDTO(LocalDate appointmentDate, String appointmentStartTime) {
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
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
