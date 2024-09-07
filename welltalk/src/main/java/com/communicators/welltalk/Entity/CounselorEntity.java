package com.communicators.welltalk.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblcounselor")


public class CounselorEntity extends UserEntity {

    
    @Column(name = "assignedYear")
    private String assignedYear;

    public CounselorEntity() {

    }

    public CounselorEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String image, String role, String assignedYear, String college, String program) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, image, role, college, program);
        this.assignedYear = assignedYear;
    }

    public CounselorEntity(int teacherId, String institutionalEmail, String idNumber, String firstName, String lastName,
            String gender, String password, String role, String assignedYear, String college, String program) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, role, college, program);
        this.assignedYear = assignedYear;
    }

    public String getAssignedYear() {
        return assignedYear;
    }

    public void setAssignedYear(String assignedYear) {
        this.assignedYear = assignedYear;
    }

    
}
