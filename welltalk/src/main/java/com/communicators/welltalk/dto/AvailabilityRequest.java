package com.communicators.welltalk.dto;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityRequest {

    private int counselorId;
    private List<LocalDate> unavailableDates;

    public AvailabilityRequest() {
    }

    public AvailabilityRequest(int counselorId, List<LocalDate> unavailableDates) {
      this.counselorId = counselorId;
      this.unavailableDates = unavailableDates;
    }

    // Getters and Setters
    public int getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(int counselorId) {
        this.counselorId = counselorId;
    }

    public List<LocalDate> getUnavailableDates() {
        return unavailableDates;
    }

    public void setUnavailableDates(List<LocalDate> unavailableDates) {
        this.unavailableDates = unavailableDates;
    }
}
