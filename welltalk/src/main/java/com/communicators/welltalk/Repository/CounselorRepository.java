package com.communicators.welltalk.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.CounselorEntity;

@Repository
public interface CounselorRepository extends JpaRepository<CounselorEntity, Integer> {
    List<CounselorEntity> findByIsDeletedFalse();

    Optional<CounselorEntity> findByIdAndIsDeletedFalse(int id);

    Optional<CounselorEntity> findByInstitutionalEmailAndIsDeletedFalse(String institutionalEmail);

    Optional<CounselorEntity> findCounselorByInstitutionalEmailAndIsDeletedFalse(String institutionalEmail);

    Optional<CounselorEntity> findByProgramAndCollegeAndAssignedYearAndIsDeletedFalse(String program, String college, String assignedYear);
}

