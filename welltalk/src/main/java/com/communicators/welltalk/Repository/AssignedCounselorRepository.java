package com.communicators.welltalk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.AssignedCounselorEntity;
import com.communicators.welltalk.Entity.TeacherEntity;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.StudentEntity;

@Repository
public interface AssignedCounselorRepository extends JpaRepository<AssignedCounselorEntity, Integer> {
    
    List<AssignedCounselorEntity> findByCounselorId(CounselorEntity counselor);

    List<AssignedCounselorEntity> findByStudentId(StudentEntity student);

    List<AssignedCounselorEntity> findByTeacherId(TeacherEntity teacher);
}
