package com.communicators.welltalk.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    List<StudentEntity> findByIsDeletedFalse();

    Optional<StudentEntity> findByIdAndIsDeletedFalse(int id);

    List<StudentEntity> findByIsDeletedFalseAndIsVerifiedTrue();

    StudentEntity findByInstitutionalEmailAndIsDeletedFalse(String institutionalEmail);

    boolean existsByIdNumberAndIsDeletedFalse(String idNumber);

    StudentEntity findByIdNumberAndIsDeletedFalse(String idNumber);
}