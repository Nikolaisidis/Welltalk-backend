package com.communicators.welltalk.dto;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityResponse {

    private int availabilityId;
    private List<LocalDate> unavailableDates;

    // Constructor
    public AvailabilityResponse(int availabilityId, List<LocalDate> unavailableDates) {
        this.availabilityId = availabilityId;
        this.unavailableDates = unavailableDates;
    }

    // Getters and Setters
    public int getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }

    public List<LocalDate> getUnavailableDates() {
        return unavailableDates;
    }

    public void setUnavailableDates(List<LocalDate> unavailableDates) {
        this.unavailableDates = unavailableDates;
    }
}
