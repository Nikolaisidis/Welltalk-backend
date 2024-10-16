package com.communicators.welltalk.Entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name = "tblappointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "counselorId", referencedColumnName = "id")
    private CounselorEntity counselor;

    @OneToOne
    @JoinColumn(name = "referralId", referencedColumnName = "referralId", nullable = true)
    private ReferralEntity referral;

    @ManyToOne
    @JoinColumn(name = "outsideCounselorid", referencedColumnName = "id")
    private CounselorEntity outsideCounselor;

    private LocalDate appointmentDate;

    private String appointmentStartTime;

    private String appointmentStatus;

    private String appointmentNotes;

    private String appointmentType;

    private String appointmentAdditionalNotes;

    private String appointmentPurpose;

    private LocalDate appointmentBooked;

    private LocalDate appointmentModified;

    private boolean isDeleted;

    public AppointmentEntity() {

    }

    public AppointmentEntity(StudentEntity student, CounselorEntity counselor, LocalDate appointmentDate,
            String appointmentStartTime, String appointmentStatus,
            String appointmentNotes,
            String appointmentType, String appointmentPurpose, CounselorEntity outsideCounselor) {
        this.student = student;
        this.counselor = counselor;
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentStatus = appointmentStatus;
        this.appointmentNotes = appointmentNotes;
        this.appointmentType = appointmentType;
        this.appointmentPurpose = appointmentPurpose;
        isDeleted = false;
        this.outsideCounselor = outsideCounselor;
    }

    public CounselorEntity getOutsideCounselor() {
        return outsideCounselor;
    }

    public void setOutsideCounselor(CounselorEntity outsideCounselor) {
        this.outsideCounselor = outsideCounselor;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public CounselorEntity getCounselor() {
        return counselor;
    }

    public void setCounselor(CounselorEntity counselor) {
        this.counselor = counselor;
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

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getAppointmentNotes() {
        return appointmentNotes;
    }

    public void setAppointmentNotes(String appointmentNotes) {
        this.appointmentNotes = appointmentNotes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentAdditionalNotes() {
        return appointmentAdditionalNotes;
    }

    public void setAppointmentAdditionalNotes(String appointmentAdditionalNotes) {
        this.appointmentAdditionalNotes = appointmentAdditionalNotes;
    }

    public String getAppointmentPurpose() {
        return appointmentPurpose;
    }

    public void setAppointmentPurpose(String appointmentPurpose) {
        this.appointmentPurpose = appointmentPurpose;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getAppointmentBooked() {
        return appointmentBooked;
    }

    public void setAppointmentBooked(LocalDate appointmentBooked) {
        this.appointmentBooked = appointmentBooked;
    }

    public LocalDate getAppointmentModified() {
        return appointmentModified;
    }

    public void setAppointmentModified(LocalDate appointmentModified) {
        this.appointmentModified = appointmentModified;
    }

    public ReferralEntity getReferral() {
        return referral;
    }

    public void setReferral(ReferralEntity referral) {
        this.referral = referral;
    }

    @PrePersist
    public void prePersist() {
        appointmentBooked = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        appointmentModified = LocalDate.now();
    }
}
