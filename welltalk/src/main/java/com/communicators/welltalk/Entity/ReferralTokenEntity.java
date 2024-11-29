package com.communicators.welltalk.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import java.util.Date;

@Entity
@Table(name = "referral_token")
public class ReferralTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @OneToOne(targetEntity = ReferralEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "referral_id")
    private ReferralEntity referral;

    private Date expiryDate;

    public ReferralTokenEntity() {
    }

    public ReferralTokenEntity(String token, ReferralEntity referral, Date expiryDate) {
        this.token = token;
        this.referral = referral;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public ReferralEntity getReferral() {
        return referral;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setReferral(ReferralEntity referral) {
        this.referral = referral;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
