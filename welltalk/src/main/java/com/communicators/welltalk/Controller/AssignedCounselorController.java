package com.communicators.welltalk.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.AssignedCounselorEntity;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Service.AssignedCounselorService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/assignCounselor")
public class AssignedCounselorController {

    @Autowired
    private AssignedCounselorService assignedCounselorService;

    @GetMapping("/getByCounselorId/{counselorId}")
    public ResponseEntity<List<Object>> getByCounselorId(@PathVariable int counselorId) {
        List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService.getByCounselorId(counselorId);
        if (assignedCounselors != null && !assignedCounselors.isEmpty()) {
            List<Object> result = assignedCounselors.stream().map(assignedCounselor -> {
                if (assignedCounselor.getStudentId() != null) {
                    return assignedCounselor.getStudentId();
                } else if (assignedCounselor.getTeacherId() != null) {
                    return assignedCounselor.getTeacherId();
                }
                return null;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCounselorByStudentId/{studentId}")
    public ResponseEntity<List<CounselorEntity>> getCounselorByStudentId(@PathVariable int studentId) {
        List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService.getByStudentId(studentId);
        if (assignedCounselors != null && !assignedCounselors.isEmpty()) {
            List<CounselorEntity> counselors = assignedCounselors.stream()
                    .map(AssignedCounselorEntity::getCounselorId)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(counselors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCounselorByTeacherId/{teacherId}")
    public ResponseEntity<List<CounselorEntity>> getCounselorByTeacherId(@PathVariable int teacherId) {
        List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService.getByTeacherId(teacherId);
        if (assignedCounselors != null && !assignedCounselors.isEmpty()) {
            List<CounselorEntity> counselors = assignedCounselors.stream()
                    .map(AssignedCounselorEntity::getCounselorId)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(counselors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getCounselorByReceiverId/{receiverId}")
    public ResponseEntity<List<CounselorEntity>> getCounselorByReceiverId(@PathVariable int receiverId) {
        List<CounselorEntity> counselors = assignedCounselorService.getCounselorsByReceiverId(receiverId);
        if (counselors != null && !counselors.isEmpty()) {
            return new ResponseEntity<>(counselors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
