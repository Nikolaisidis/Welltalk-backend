package com.communicators.welltalk.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblassignedCounselor")
public class AssignedCounselorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assigned_counselor_id")
    private int assignedCounselorId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity studentId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private TeacherEntity teacherId;

    @ManyToOne
    @JoinColumn(name = "counselor_id", referencedColumnName = "id")
    private CounselorEntity counselorId;


    @Column(name = "program")
    private String program;

    @Column(name = "year")
    private int year;

    @Column(name = "college")
    private String college;

    public AssignedCounselorEntity() {

    }

    public AssignedCounselorEntity(StudentEntity studentId, TeacherEntity teacherId, CounselorEntity counselorId,
            CounselorEntity outsideCounselorId, String program, int year, String college) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.counselorId = counselorId;
        this.program = program;
        this.year = year;
        this.college = college;
    }

    public int getAssignedCounselorId() {
        return assignedCounselorId;
    }

    public StudentEntity getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentEntity studentId) {
        this.studentId = studentId;
    }

    public TeacherEntity getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(TeacherEntity teacherId) {
        this.teacherId = teacherId;
    }

    public CounselorEntity getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(CounselorEntity counselorId) {
        this.counselorId = counselorId;
    }

    public void setAssignedCounselorId(int assignedCounselorId) {
        this.assignedCounselorId = assignedCounselorId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
