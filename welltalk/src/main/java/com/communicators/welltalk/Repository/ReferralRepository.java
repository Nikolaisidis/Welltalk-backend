package com.communicators.welltalk.Repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.communicators.welltalk.Entity.ReferralEntity;

@Repository
public interface ReferralRepository extends JpaRepository<ReferralEntity, Integer> {
    List<ReferralEntity> findByIsDeletedFalse();

    ReferralEntity findByReferralIdAndIsDeletedFalse(int id);

    List<ReferralEntity> findByTeacher_IdAndIsDeletedFalseOrderByReferralIdDesc(int id);

    List<ReferralEntity> findByCounselor_IdAndIsDeletedFalseOrderByReferralIdDesc(int id);

    ReferralEntity findByStudentIdAndIsDeletedFalse(String studentId);
}
