package com.communicators.welltalk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByIsDeletedFalse();

    List<UserEntity> findByIsDeletedFalseAndIsVerifiedFalse();
    
    List<UserEntity> findByIsDeletedFalseAndIsVerifiedTrue();
    
    Optional<UserEntity> findByIdAndIsDeletedFalse(int id);

    Optional<UserEntity> findByInstitutionalEmailAndIsDeletedFalse(String institutionalEmail);

    boolean existsByInstitutionalEmail(String institutionalEmail);

    boolean existsByIdNumber(String idNumber);

    UserEntity findByInstitutionalEmail(String institutionalEmail);

    
}