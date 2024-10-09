package com.communicators.welltalk.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.CounselorEntity;

@Repository
public interface CounselorRepository extends JpaRepository<CounselorEntity, Integer> {
    List<CounselorEntity> findByIsDeletedFalse();

    List<CounselorEntity> findByIsDeletedFalseAndIsVerifiedTrue();

    Optional<CounselorEntity> findByIdAndIsDeletedFalse(int id);

    Optional<CounselorEntity> findByInstitutionalEmailAndIsDeletedFalse(String institutionalEmail);

    Optional<CounselorEntity> findCounselorByInstitutionalEmailAndIsDeletedFalse(String institutionalEmail);

    CounselorEntity findByProgramContainingAndCollegeAndAssignedYearContainingAndIsDeletedFalse(
            @Param("program") String program,
            @Param("college") String college,
            @Param("assignedYear") String assignedYear);

    List<CounselorEntity> findByCollegeAndIsDeletedFalse(String college);

    List<CounselorEntity> findByProgramContainingAndIsDeletedFalse(String program);

    List<CounselorEntity> findByAssignedYearContainingAndIsDeletedFalse(String assignedYear);

}
