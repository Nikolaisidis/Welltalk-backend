package com.communicators.welltalk.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.StudentEntity;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {
        List<AppointmentEntity> findByIsDeletedFalse(Sort sort);

        Optional<AppointmentEntity> findByAppointmentIdAndIsDeletedFalse(int id);

        AppointmentEntity findByAppointmentId(int id);

        List<AppointmentEntity> findByAppointmentDateAndIsDeletedFalse(LocalDate date);

        List<AppointmentEntity> findByStudent(StudentEntity student);

        Boolean existsByAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(LocalDate date, String startTime);

        List<AppointmentEntity> findByCounselorAndAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(
                        CounselorEntity counselor, LocalDate date, String startTime);

        List<AppointmentEntity> findByCounselor(CounselorEntity counselor);

        List<AppointmentEntity> findByCounselorAndAppointmentDateAndIsDeletedFalse(CounselorEntity counselor,
                        LocalDate date);

        List<AppointmentEntity> findByCounselorOrOutsideCounselor(CounselorEntity counselor,
                        CounselorEntity outsideCounselor);

        boolean existsByCounselorAndAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(CounselorEntity counselor,
                        LocalDate date, String startTime);

}
