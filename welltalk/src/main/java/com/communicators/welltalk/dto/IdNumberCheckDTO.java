package com.communicators.welltalk.dto;

public class IdNumberCheckDTO {
    private String idNumber;

    public IdNumberCheckDTO() {
    }
    

    public IdNumberCheckDTO(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
