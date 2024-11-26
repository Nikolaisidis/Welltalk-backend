package com.communicators.welltalk.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Entity.ReferralTokenEntity;
import com.communicators.welltalk.Repository.ReferralTokenRepository;

@Service
public class ReferralTokenService {
    @Autowired
    private ReferralTokenRepository referralTokenRepository;

    public void createReferralTokenForUser(ReferralEntity referral, String token) {
        ReferralTokenEntity myToken = new ReferralTokenEntity();
        myToken.setReferral(referral);
        myToken.setToken(token);
        myToken.setExpiryDate(calculateExpiryDate(60 * 24 * 15)); // Token expires in 30 days
        System.out.println("Token: " + myToken.getToken());
        referralTokenRepository.save(myToken);
    }

    // calculate expiry date
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    // check if token is expired
    private boolean isTokenExpired(ReferralTokenEntity token) {
        final Calendar cal = Calendar.getInstance();
        return token.getExpiryDate().before(cal.getTime());
    }

    // validate the token
    public boolean validateReferralToken(String token) {
        ReferralTokenEntity referralToken = referralTokenRepository.findByToken(token).orElse(null);
        return referralToken != null && !isTokenExpired(referralToken);
    }

    public ReferralTokenEntity getReferralToken(String token) {
        return referralTokenRepository.findByToken(token).orElse(null);
    }

    public void deleteReferralToken(ReferralTokenEntity referralToken) {
        referralTokenRepository.delete(referralToken);
    }

    public String getReferralTokenByReferralId(int referralId) {
        ReferralTokenEntity referralTokenEntity = referralTokenRepository.findByReferral_ReferralId(referralId);
        String token = referralTokenEntity.getToken();
        return token;
    }
}
