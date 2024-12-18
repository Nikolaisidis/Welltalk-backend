package com.communicators.welltalk.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Repository.AssignedCounselorRepository;
import com.communicators.welltalk.Repository.CounselorRepository;
import com.communicators.welltalk.dto.CounselorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class CounselorService {

    @Autowired
    CounselorRepository counselorRepository;

    @Autowired
    AssignedCounselorRepository assignedCounselorRepository;

    @Autowired
    AssignedCounselorService assignedCounselorService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public CounselorEntity saveCounselor(CounselorEntity counselor) {
        CounselorEntity savedCounselor = counselorRepository.save(counselor);
        assignedCounselorService.assignCounselorIfVerified(savedCounselor);
        return savedCounselor;
    }

    public List<CounselorResponseDTO> getAllCounselors() {
        List<CounselorEntity> counselors = counselorRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        List<CounselorResponseDTO> counselorDTOs = new ArrayList<>();
        
        for (CounselorEntity counselor : counselors) {
            CounselorResponseDTO dto = new CounselorResponseDTO();
            dto.setId(counselor.getId());
            dto.setInstitutionalEmail(counselor.getInstitutionalEmail());
            dto.setFirstName(counselor.getFirstName());
            dto.setLastName(counselor.getLastName());
            dto.setImage(counselor.getImage());
            dto.setProgram(counselor.getProgram());
            dto.setCollege(counselor.getCollege());
            dto.setAssignedYear(counselor.getAssignedYear());
            dto.setStatus(counselor.getStatus());
            dto.setUnavailableDates(counselor.getUnavailableDates());
            dto.setRole(counselor.getRole());
            dto.setIdNumber(counselor.getIdNumber());
            
            counselorDTOs.add(dto);
        }
        return counselorDTOs;
    }

    public CounselorEntity getCounselorById(int id) {
        return counselorRepository.findByIdAndIsDeletedFalse(id).get();
    }

    public CounselorEntity getCounselorByEmail(String email) {
        return counselorRepository.findByInstitutionalEmailAndIsDeletedFalse(email).get();
    }

    // public void assignCounselor(StudentEntity student, TeacherEntity teacher) {
    // List<CounselorEntity> counselors =
    // counselorRepository.findByIsDeletedFalse();
    // for (CounselorEntity counselor : counselors) {
    // if (counselor.getProgram().equals(student.getProgram()) &&
    // counselor.getCollege().equals(student.getCollege()) &&
    // counselor.getAssignedYear().equals(String.valueOf(student.getYear()))) {

    // AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
    // assignedCounselor.setStudentId(student.getId());
    // assignedCounselor.setTeacherId(teacher.getId());
    // assignedCounselor.setCounselorId(counselor.getId());
    // assignedCounselor.setProgram(student.getProgram());
    // assignedCounselor.setYear(student.getYear());
    // assignedCounselor.setCollege(student.getCollege());

    // assignedCounselorRepository.save(assignedCounselor);
    // break;
    // }
    // }
    // }

    @SuppressWarnings("finally")
    public CounselorEntity updateCounselor(int id, CounselorEntity counselor) {
        CounselorEntity counselorToUpdate = new CounselorEntity();
        try {
            counselorToUpdate = counselorRepository.findByIdAndIsDeletedFalse(id).get();

            // counselorToUpdate.setInstitutionalEmail(counselor.getInstitutionalEmail());
            counselorToUpdate.setFirstName(counselor.getFirstName());
            counselorToUpdate.setLastName(counselor.getLastName());
            counselorToUpdate.setGender(counselor.getGender());
            // counselorToUpdate.setPassword(counselor.getPassword());
            counselorToUpdate.setImage(counselor.getImage());
            counselorToUpdate.setProgram(counselor.getProgram());
            counselorToUpdate.setCollege(counselor.getCollege());
            counselorToUpdate.setAssignedYear(counselor.getAssignedYear());
            counselorToUpdate.setStatus(counselor.getStatus());
            counselorToUpdate.setUnavailableDates(counselor.getUnavailableDates());
            
            // String newUnavailableDates = counselor.getUnavailableDates();
            // if (newUnavailableDates != null && !newUnavailableDates.isEmpty()) {
            //     // Append the new unavailable dates to the current ones
            //     String currentUnavailableDates = counselorToUpdate.getUnavailableDates();
            //     if (currentUnavailableDates != null && !currentUnavailableDates.isEmpty()) {
            //         // Append new dates separated by a comma
            //         counselorToUpdate.setUnavailableDates(currentUnavailableDates + "," + newUnavailableDates);
            //     } else {
            //         // If no current dates, set the new dates
            //         counselorToUpdate.setUnavailableDates(newUnavailableDates);
            //     }
            // } else {
            //     // If no new dates are provided, retain the current dates
            //     // This part is optional depending on your requirement
            //     counselorToUpdate.setUnavailableDates(counselorToUpdate.getUnavailableDates());
            // }
    

            counselorToUpdate = counselorRepository.save(counselorToUpdate);

            assignedCounselorService.assignCounselorIfVerified(counselorToUpdate);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Counselor " + id + " does not exist.");
        } finally {
            return counselorToUpdate;
        }
    }

    public boolean deleteCounselor(int id) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(id).get();
        if (counselor != null) {
            counselor.setIsDeleted(true);
            counselorRepository.save(counselor);
            return true;
        } else {
            System.out.println("Counselor " + id + " does not exist.");
            return false;
        }
    }

    public void addUnavailableDates(int counselorId, String newDates) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException("Counselor not found"));

        String currentUnavailableDates = counselor.getUnavailableDates();
        if (currentUnavailableDates != null && !currentUnavailableDates.isEmpty()) {
            // Append the new dates (comma-separated)
            counselor.setUnavailableDates(currentUnavailableDates + "," + newDates);
        } else {
            // If no dates are present, set the new dates
            counselor.setUnavailableDates(newDates);
        }

        counselorRepository.save(counselor);
    } 

    // Retrieve the unavailable dates as an array
    public String[] getUnavailableDates(int counselorId) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException("Counselor not found"));

        // Split the comma-separated string into an array
        String unavailableDates = counselor.getUnavailableDates();
        return unavailableDates != null ? unavailableDates.split(",") : new String[0];
    }

}