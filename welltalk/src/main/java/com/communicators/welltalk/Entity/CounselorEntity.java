package com.communicators.welltalk.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblcounselor")
public class CounselorEntity extends UserEntity {

    @Column(name = "assignedYear")
    private String assignedYear;

    @Column(name = "status")
    private String status;

    @Column(name = "unavailableDates")
    private String unavailableDates; 

    public CounselorEntity() {

    }

    public CounselorEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String image, String role, String assignedYear, String college, String program, String status, String unavailableDates) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, image, role, college, program);
        this.assignedYear = assignedYear;
        this.status = status;
        this.unavailableDates = unavailableDates;
    }

    public CounselorEntity(int teacherId, String institutionalEmail, String idNumber, String firstName, String lastName,
            String gender, String password, String role, String assignedYear, String college, String program, String status, String unavailableDates) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, role, college, program);
        this.assignedYear = assignedYear;
        this.status = status;
        this.unavailableDates = unavailableDates;
    }

    public String getAssignedYear() {
        return assignedYear;
    }

    public void setAssignedYear(String assignedYear) {
        this.assignedYear = assignedYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnavailableDates() {
        return unavailableDates;
    }

    public void setUnavailableDates(String unavailableDates) {
        this.unavailableDates = unavailableDates;
    }
}
