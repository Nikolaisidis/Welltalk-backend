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
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Service.AssignedCounselorService;
import com.communicators.welltalk.Service.AuthenticationService;
import com.communicators.welltalk.Service.CounselorService;
import com.communicators.welltalk.dto.CounselorResponseDTO;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user/counselor")
public class CounselorController {

    @Autowired
    CounselorService counselorService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AssignedCounselorService assignedCounselorService;

    @PostMapping("/createCounselor")
    public ResponseEntity<CounselorEntity> insertCounselor(@RequestBody CounselorEntity counselor) {
        CounselorEntity newCounselor = authenticationService.registerCounselor(counselor);
        assignedCounselorService.assignCounselorIfVerified(newCounselor);
        return new ResponseEntity<>(newCounselor, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCounselors")
    public ResponseEntity<List<CounselorResponseDTO>> getAllCounselors() {
        List<CounselorResponseDTO> counselors = counselorService.getAllCounselors();
        return new ResponseEntity<>(counselors, HttpStatus.OK);
    }

    @GetMapping("/getCounselorById/{id}")
    public ResponseEntity<CounselorEntity> getCounselorById(@PathVariable int id) {
        CounselorEntity counselor = counselorService.getCounselorById(id);
        if (counselor != null) {
            return new ResponseEntity<>(counselor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCounselor/{id}")
    public ResponseEntity<CounselorEntity> updateCounselor(@PathVariable int id,
            @RequestBody CounselorEntity counselor) {
        CounselorEntity updatedCounselor = counselorService.updateCounselor(id, counselor);
        if (updatedCounselor == null) {
            assignedCounselorService.updateAssignmentsForCounselor(updatedCounselor);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCounselor, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCounselor/{id}")
    public ResponseEntity<Void> deleteCounselor(@PathVariable int id) {
        boolean deleted = counselorService.deleteCounselor(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
