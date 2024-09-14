package com.communicators.welltalk.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Service.EmailService;
import com.communicators.welltalk.Service.ReferralService;
import com.communicators.welltalk.Service.ReferralTokenService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user/referral")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @Autowired
    private ReferralTokenService referralTokenService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/getAllReferrals")
    public ResponseEntity<List<ReferralEntity>> getAllReferrals() {
        List<ReferralEntity> referrals = referralService.getAllReferrals();
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @GetMapping("/getReferralById/{id}")
    public ResponseEntity<ReferralEntity> getReferralById(@PathVariable int id) {
        ReferralEntity referral = referralService.getReferralById(id);
        if (referral != null) {
            return new ResponseEntity<>(referral, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getReferralsByReferredById/{id}")
    public ResponseEntity<List<ReferralEntity>> getReferralsByReferredById(@PathVariable int id) {
        List<ReferralEntity> referrals = referralService.getReferralsByReferredById(id);
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @PutMapping("/markReferralAsAccepted")
    public ResponseEntity<ReferralEntity> markReferralAsAccepted(@RequestParam int id) {
        ReferralEntity updatedReferral = referralService.markReferralAsAccepted(id);
        return new ResponseEntity<>(updatedReferral, HttpStatus.OK);
    }

    @PostMapping("/createReferral")
    public ResponseEntity<ReferralEntity> insertReferral(@RequestParam int teacherId,
            @RequestBody ReferralEntity referral) {
        ReferralEntity newReferral = referralService.saveReferral(teacherId, referral);

        String token = UUID.randomUUID().toString();
        referralTokenService.createReferralTokenForUser(newReferral, token);

        String message = "You have been referred by " + newReferral.getTeacher().getFirstName() + " "
                + newReferral.getTeacher().getLastName()
                + " for a counselling session. To accept referral, please click on the link below: http://localhost:3000/referral/"
                + token + "/pendingreferral";

        emailService.sendSimpleMessage(newReferral.getStudentEmail(), "Referral Notification", message);
        return new ResponseEntity<>(newReferral, HttpStatus.CREATED);
    }

    @PutMapping("/updateReferral/{id}")
    public ResponseEntity<ReferralEntity> updateReferral(@PathVariable int id, @RequestBody ReferralEntity referral) {
        ReferralEntity updatedReferral = referralService.updateReferral(id, referral);
        if (updatedReferral == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedReferral, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReferral/{id}")
    public ResponseEntity<Void> deleteReferral(@PathVariable int id) {
        boolean deleted = referralService.deleteReferral(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/validateReferralToken")
    public ResponseEntity<?> validateReferralToken(@RequestParam String token) {
        boolean isValid = referralTokenService.validateReferralToken(token);
        if (isValid) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
