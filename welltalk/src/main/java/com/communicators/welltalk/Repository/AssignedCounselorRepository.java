package com.communicators.welltalk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.AssignedCounselorEntity;

@Repository
public interface AssignedCounselorRepository extends JpaRepository<AssignedCounselorEntity, Integer> {
    List<AssignedCounselorEntity> findByCounselorId(int counselorId);

    List<AssignedCounselorEntity> findByStudentId(int studentId);

    List<AssignedCounselorEntity> findByTeacherId(int teacherId);

}
