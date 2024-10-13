package com.communicators.welltalk.Entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tblnotifications")
public class NotificationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;
    private String type;
    private boolean isRead;
    private LocalDateTime date;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private UserEntity receiver;

    @ManyToOne
    @JoinColumn(name = "app_id", referencedColumnName = "appointmentId")
    private AppointmentEntity appointment; 

    @ManyToOne
    @JoinColumn(name = "ref_id", referencedColumnName = "referralId")
    private ReferralEntity referral; 

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private PostEntity post; 

    public NotificationsEntity() {
    }

    public NotificationsEntity(int notificationId, String type, boolean isRead, LocalDateTime date, boolean isDeleted, UserEntity sender,
            UserEntity receiver, AppointmentEntity appointment, ReferralEntity referral, PostEntity post) {
        this.notificationId = notificationId;
        this.type = type;
        this.isRead = isRead;
        this.date = date;
        this.isDeleted = isDeleted;
        this.sender = sender;
        this.receiver = receiver;
        this.appointment = appointment;
        this.referral = referral;
        this.post = post;
    }


    // appointment
    public NotificationsEntity(String type, UserEntity sender, UserEntity receiver, AppointmentEntity appointment) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.appointment = appointment;
        this.isDeleted = false;
        this.isRead = false;
    }

    // referral
    public NotificationsEntity(String type, UserEntity sender, UserEntity receiver, ReferralEntity referral) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.referral = referral;
        this.isDeleted = false;
        this.isRead = false;
    }

    // post
    public NotificationsEntity(String type, UserEntity sender, UserEntity receiver, PostEntity post) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.post = post;
        this.isDeleted = false;
        this.isRead = false;
    }
    
   
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }

    public ReferralEntity getReferral() {
        return referral;
    }

    public void setReferral(ReferralEntity referral) {
        this.referral = referral;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    
    

    // @PreUpdate
    // protected void onUpdate() {
    //     readDate = LocalDateTime.now();
    // }

}
