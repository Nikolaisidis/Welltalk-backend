package com.communicators.welltalk.Entity;

// import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tblteacher")
public class TeacherEntity extends UserEntity {

    // @Column(name = "college")
    // private String college;


    public TeacherEntity() {

    }

    public TeacherEntity(String institutionalEmail, String idNumber, String firstName, String lastName, String gender,
            String password, String image, String role, String college, String program) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, image, role, college, program);
        this.college = college;
    }

    public TeacherEntity(String institutionalEmail, String idNumber, String firstName, String lastName,
            String gender, String password, String role, String college, String program) {
        super(institutionalEmail, idNumber, firstName, lastName, gender, password, role, college, program);
        this.college = college;
    }

    // public String getCollege() {
    //     return college;
    // }

    // public void setCollege(String college) {
    //     this.college = college;
    // }
}
