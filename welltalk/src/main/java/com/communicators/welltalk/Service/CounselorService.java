package com.communicators.welltalk.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Repository.CounselorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CounselorService {

    @Autowired
    CounselorRepository counselorRepository;

    public CounselorEntity saveCounselor(CounselorEntity counselor) {
        return counselorRepository.save(counselor);
    }

    public List<CounselorEntity> getAllCounselors() {
        return counselorRepository.findByIsDeletedFalse();
    }

    public CounselorEntity getCounselorById(int id) {
        return counselorRepository.findByIdAndIsDeletedFalse(id).get();
    }

    public CounselorEntity getCounselorByEmail(String email) {
        return counselorRepository.findByInstitutionalEmailAndIsDeletedFalse(email).get();
    }

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
            counselorToUpdate.setAssignedYear(counselor.getAssignedYear());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Counselor " + id + " does not exist.");
        } finally {
            return counselorRepository.save(counselorToUpdate);
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

}
