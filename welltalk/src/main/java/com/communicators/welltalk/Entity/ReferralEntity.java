package com.communicators.welltalk.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "tblreferral")
public class ReferralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int referralId;

    @ManyToOne
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private TeacherEntity teacher;

    @ManyToOne
    @JoinColumn(name = "studentAccountId", referencedColumnName = "id")
    private StudentEntity student;

    @OneToMany(mappedBy = "referral", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppointmentEntity> appointments;

    private String studentId;

    private String studentEmail;

    private String studentFirstName;

    private String studentLastName;

    private String studentCollege;

    private String studentProgram;

    private String studentYear;

    private String reason;

    private String status;

    private String feedback;

    private String additionalNotes;

    private LocalDateTime dateOfRefferal;

    private LocalDateTime dateOfModification;

    private boolean isDeleted;

    private boolean isAccepted;

    @ManyToOne
    @JoinColumn(name = "counselorId", referencedColumnName = "id")
    private CounselorEntity counselor;

    public ReferralEntity() {
    }

    public ReferralEntity(TeacherEntity teacher, String studentId, String studentEmail, String studentFirstName,
            String studentLastName, String reason, String studentYear, String studentCollege, String studentProgram,
            CounselorEntity counselor) {
        this.teacher = teacher;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.reason = reason;
        this.studentCollege = studentCollege;
        this.studentProgram = studentProgram;
        this.studentYear = studentYear;
        this.counselor = counselor;
    }

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public CounselorEntity getCounselor() {
        return counselor;
    }

    public void setCounselor(CounselorEntity counselor) {
        this.counselor = counselor;
    }

    public int getReferralId() {
        return referralId;
    }

    public void setReferralId(int referralId) {
        this.referralId = referralId;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentCollege() {
        return studentCollege;
    }

    public void setStudentCollege(String studentCollege) {
        this.studentCollege = studentCollege;
    }

    public String getStudentProgram() {
        return studentProgram;
    }

    public void setStudentProgram(String studentProgram) {
        this.studentProgram = studentProgram;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public LocalDateTime getDateOfRefferal() {
        return dateOfRefferal;
    }

    public void setDateOfRefferal(LocalDateTime dateOfRefferal) {
        this.dateOfRefferal = dateOfRefferal;
    }

    public LocalDateTime getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(LocalDateTime dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @PrePersist
    protected void onCreate() {
        dateOfRefferal = LocalDateTime.now();
        status = "Pending";
    }

    @PreUpdate
    protected void onUpdate() {
        dateOfModification = LocalDateTime.now();
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

}
