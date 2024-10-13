package com.communicators.welltalk.Controller;

import java.util.List;

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
import com.communicators.welltalk.Service.NotificationsService;
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
    private NotificationsService notificationService;

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

    // @PutMapping("/markReferralAsAccepted")
    // public ResponseEntity<ReferralEntity> markReferralAsAccepted(@RequestParam
    // int id) {
    // ReferralEntity updatedReferral = referralService.markReferralAsAccepted(id);
    // return new ResponseEntity<>(updatedReferral, HttpStatus.OK);
    // }

    @GetMapping("/getReferralsByCounselorId")
    public ResponseEntity<List<ReferralEntity>> getReferralByCounselorId(@RequestParam int id) {
        List<ReferralEntity> referrals = referralService.getReferralsByCounselorId(id);
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @PostMapping("/createReferral")
    public ResponseEntity<ReferralEntity> insertReferral(@RequestParam int teacherId,
            @RequestBody ReferralEntity referral) {
        ReferralEntity newReferral = referralService.saveReferral(teacherId, referral);

        notificationService.createReferralNotification(teacherId, newReferral);
        
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

    @PutMapping("/acceptReferral")
    public ResponseEntity<ReferralEntity> acceptReferral(@RequestParam String token) {
        ReferralEntity updatedReferral = referralService.referralAcceptedByStudent(token);

        notificationService.acceptedReferralNotification(updatedReferral);
        return new ResponseEntity<>(updatedReferral, HttpStatus.OK);
    }

    @PutMapping("/rejectReferral")
    public ResponseEntity<ReferralEntity> rejectReferral(@RequestParam String token) {
        ReferralEntity updatedReferral = referralService.referralDeclinedByStudent(token);

        notificationService.declinedReferralNotification(updatedReferral);
        return new ResponseEntity<>(updatedReferral, HttpStatus.OK);
    }

    @GetMapping("/getReferralTokenByReferralId")
    public ResponseEntity<String> getReferralTokenByReferralId(@RequestParam int referralId) {
        String token = referralTokenService.getReferralTokenByReferralId(referralId);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/getActiveAcceptedReferral")
    public ResponseEntity<?> checkActiveAcceptedReferral(@RequestParam String studentEmail) {
        boolean isActiveAcceptedReferral = referralService.checkActiveAcceptedReferral(studentEmail);
        return new ResponseEntity<>(isActiveAcceptedReferral, HttpStatus.OK);
    }

    @GetMapping("/getReferralToken")
    public ResponseEntity<String> getReferralToken(@RequestParam int referralId) {
        String token = referralTokenService.getReferralTokenByReferralId(referralId);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
