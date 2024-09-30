package com.communicators.welltalk.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Entity.AssignedCounselorEntity;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Entity.Role;
import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Repository.AppointmentRepository;
import com.communicators.welltalk.Repository.CounselorRepository;
import com.communicators.welltalk.Repository.ReferralRepository;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    CounselorService counselorService;

    @Autowired
    EmailService emailService;

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AssignedCounselorService assignedCounselorService;

    @Autowired
    CounselorRepository counselorRepository;

    // public AppointmentEntity saveAppointment(int id, AppointmentEntity
    // appointment) {
    // if (checkAppointmentIsTaken(appointment.getAppointmentDate(),
    // appointment.getAppointmentStartTime())) {
    // throw new IllegalArgumentException("Date and Time is already taken");
    // }

    // StudentEntity student = studentService.getStudentById(id);

    // appointment.setStudent(student);
    // appointment.setAppointmentStatus("Pending");

    // if (appointment.getAppointmentType().equals("Referral")) {
    // ReferralEntity referral = referralRepository
    // .findByStudentIdAndIsDeletedFalse(appointment.getStudent().getIdNumber());
    // if (referral != null) {
    // System.out.print("Referral founder" + referral.getStudentEmail());

    // String messageToTeacher = "An appointment has been made for the student you
    // referred. Student: "
    // + appointment.getReferral().getStudentFirstName()
    // + " " + appointment.getReferral().getStudentLastName() + " Email: "
    // + appointment.getReferral().getStudentEmail();

    // emailService.sendSimpleMessage(appointment.getReferral().getTeacher().getInstitutionalEmail(),
    // "Appointment Created", messageToTeacher);

    // referral.setStatus("Appointment");
    // referralRepository.save(referral);
    // }
    // }
    // // // Assign available counselor based on assigned counselor service
    // // List<AssignedCounselorEntity> assignedCounselors =
    // assignedCounselorService.getByStudentId(student.getId());
    // // CounselorEntity availableCounselor =
    // findAvailableCounselor(assignedCounselors,
    // // appointment.getAppointmentDate(), appointment.getAppointmentStartTime());
    // // if (availableCounselor != null) {
    // // appointment.setCounselor(availableCounselor);
    // // } else {
    // // throw new IllegalArgumentException("No available counselor for the
    // selected timeslot.");
    // // }

    // // AppointmentEntity appointmentCreated =
    // appointmentRepository.save(appointment);
    // // //
    // String message = "An appointment has been created for you. Date: " +
    // appointment.getAppointmentDate()
    // + " Time: "
    // + appointment.getAppointmentStartTime() + " Purpose: " +
    // appointment.getAppointmentPurpose() + " Type: "
    // + appointment.getAppointmentType() + " Please wait for the counselor to
    // assign you a time.";

    // emailService.sendSimpleMessage(appointmentCreated.getStudent().getInstitutionalEmail(),
    // "Appointment Created",
    // message);

    // return appointmentCreated;
    // }

    public AppointmentEntity counselorSaveAppointment(int counselorId, int studentId, AppointmentEntity appointment) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Counselor with ID " + counselorId + " does not exist or is deleted."));
        StudentEntity student = studentService.getStudentById(studentId);

        appointment.setCounselor(counselor);
        appointment.setStudent(student);
        appointment.setAppointmentStatus("Pending");

        AppointmentEntity appointmentCreated = appointmentRepository.save(appointment);

        String message = "An appointment has been created for you. Date: " + appointment.getAppointmentDate()
                + " Time: " + appointment.getAppointmentStartTime() + " Purpose: " + appointment.getAppointmentPurpose()
                + " Type: " + appointment.getAppointmentType() + " Please wait for the counselor to assign you a time.";

        emailService.sendSimpleMessage(appointmentCreated.getStudent().getInstitutionalEmail(), "Appointment Created",
                message);

        return appointmentCreated;
    }

    public AppointmentEntity saveAppointment(int id, AppointmentEntity appointment) {
        StudentEntity student = studentService.getStudentById(id);
        appointment.setStudent(student);
        appointment.setAppointmentStatus("Pending");

        // Assign available counselor based on assigned counselor service
        List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService.getByStudentId(student.getId());
        CounselorEntity availableCounselor = findAvailableCounselor(assignedCounselors,
                appointment.getAppointmentDate(), appointment.getAppointmentStartTime());
        if (availableCounselor != null) {
            appointment.setCounselor(availableCounselor);
        } else {
            throw new IllegalArgumentException("No available counselor for the selected timeslot.");
        }

        AppointmentEntity appointmentCreated = appointmentRepository.save(appointment);

        String message = "An appointment has been created for you. Date: " + appointment.getAppointmentDate()
                + " Time: " + appointment.getAppointmentStartTime() + " Purpose: " + appointment.getAppointmentPurpose()
                + " Type: " + appointment.getAppointmentType() + " Please wait for the counselor to assign you a time.";

        emailService.sendSimpleMessage(appointmentCreated.getStudent().getInstitutionalEmail(), "Appointment Created",
                message);

        return appointmentCreated;
    }

    public AppointmentEntity saveReferralAppointment(int referralId, AppointmentEntity appointment) {
        if (checkAppointmentIsTaken(appointment.getAppointmentDate(), appointment.getAppointmentStartTime())) {
            throw new IllegalArgumentException("Date and Time is already taken");
        }

        ReferralEntity referral = referralRepository.findByReferralIdAndIsDeletedFalse(referralId);
        if (referral == null) {
            throw new IllegalArgumentException("Referral with ID " + referralId + " does not exist or is deleted.");
        }
        appointment.setReferral(referral);

        StudentEntity student;

        if (studentService.doesStudentExist(referral.getStudentId())) {
            student = studentService.getStudentByStudentId(referral.getStudentId());
        } else {
            StudentEntity studentToCreate = new StudentEntity();
            studentToCreate.setIdNumber(referral.getStudentId());
            studentToCreate.setInstitutionalEmail(referral.getStudentEmail());
            studentToCreate.setFirstName(referral.getStudentFirstName());
            studentToCreate.setLastName(referral.getStudentLastName());
            studentToCreate.setIsDeleted(false);
            studentToCreate.setPassword("12345678");
            studentToCreate.setRole(Role.student);

            student = authenticationService.registerStudent(studentToCreate);
        }

        appointment.setStudent(student);
        appointment.setAppointmentStatus("Pending");

        // Assign available counselor based on assigned counselor service
        List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService
                .getByTeacherId(referral.getTeacher().getId());
        CounselorEntity availableCounselor = findAvailableCounselor(assignedCounselors,
                appointment.getAppointmentDate(), appointment.getAppointmentStartTime());
        if (availableCounselor != null) {
            appointment.setCounselor(availableCounselor);
        } else {
            throw new IllegalArgumentException("No available counselor for the selected timeslot.");
        }
        //
        appointment.getReferral().setStatus("Accepted");

        AppointmentEntity appointmentCreated = appointmentRepository.save(appointment);

        return appointmentCreated;
    }

    // Available Counselor
    private CounselorEntity findAvailableCounselor(List<AssignedCounselorEntity> assignedCounselors, LocalDate date,
            String startTime) {
        for (AssignedCounselorEntity assignedCounselor : assignedCounselors) {
            CounselorEntity counselor = assignedCounselorService.getCounselorById(assignedCounselor.getCounselorId())
                    .orElse(null);
            if (counselor != null && !isCounselorBusy(counselor, date, startTime)) {
                return counselor;
            }
        }
        return null;
    }

    private boolean isCounselorBusy(CounselorEntity counselor, LocalDate date, String startTime) {
        List<AppointmentEntity> appointments = appointmentRepository
                .findByCounselorAndAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(counselor, date, startTime);
        return !appointments.isEmpty();
    }

    //
    public AppointmentEntity assignCounselor(String counselorEmail, int appointmentId) {
        Optional<AppointmentEntity> appointmentOpt = appointmentRepository
                .findByAppointmentIdAndIsDeletedFalse(appointmentId);
        if (!appointmentOpt.isPresent()) {
            throw new IllegalArgumentException(
                    "Appointment with ID " + appointmentId + " does not exist or is deleted.");
        }

        CounselorEntity counselor = counselorService.getCounselorByEmail(counselorEmail);
        if (counselor == null) {
            throw new IllegalArgumentException("Counselor with email " + counselorEmail + " does not exist.");
        }

        AppointmentEntity appointment = appointmentOpt.get();
        appointment.setCounselor(counselor);
        appointment.setAppointmentStatus("Assigned");

        String message = "A counselor has been assign to you. Counselor: " + appointment.getCounselor().getFirstName()
                + " " + appointment.getCounselor().getLastName() + " Email: "
                + appointment.getCounselor().getInstitutionalEmail() + " Appointment Details: Date: "
                + appointment.getAppointmentDate() + " Time: " + appointment.getAppointmentStartTime() + " Purpose: "
                + appointment.getAppointmentPurpose() + " Type: " + appointment.getAppointmentType();

        emailService.sendSimpleMessage(appointment.getStudent().getInstitutionalEmail(), "Counselor Assigned", message);

        if (appointment.getReferral() != null) {
            String messageToTeacher = "The student you referred has been assigned to a counselor. Student: "
                    + appointment.getStudent().getFirstName()
                    + " " + appointment.getStudent().getLastName() + " Email: "
                    + appointment.getStudent().getInstitutionalEmail();
            emailService.sendSimpleMessage(appointment.getReferral().getTeacher().getInstitutionalEmail(),
                    "Assigned to a Counselor", messageToTeacher);
        }

        return appointmentRepository.save(appointment);
    }

    public List<AppointmentEntity> getAppointmentsByDate(LocalDate date) {
        List<AppointmentEntity> appointments = appointmentRepository.findByAppointmentDateAndIsDeletedFalse(date);
        LocalDateTime now = LocalDateTime.now();

        for (AppointmentEntity appointment : appointments) {
            LocalDateTime appointmentStart = LocalDateTime.of(appointment.getAppointmentDate(),
                    LocalTime.parse(appointment.getAppointmentStartTime()));
            LocalDateTime appointmentEnd = appointmentStart.plusHours(1);

            if (now.isAfter(appointmentStart) && now.isBefore(appointmentEnd)) {
                appointment.setAppointmentStatus("Ongoing");
                appointmentRepository.save(appointment);
            }
        }

        return appointments;
    }

    public List<AppointmentEntity> getAppointmentsByDateAndCounselor(LocalDate date, int counselorId) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Counselor with ID " + counselorId + " does not exist or is deleted."));

        List<AppointmentEntity> appointments = appointmentRepository
                .findByCounselorAndAppointmentDateAndIsDeletedFalse(counselor, date);
        LocalDateTime now = LocalDateTime.now();

        for (AppointmentEntity appointment : appointments) {
            LocalDateTime appointmentStart = LocalDateTime.of(appointment.getAppointmentDate(),
                    LocalTime.parse(appointment.getAppointmentStartTime()));
            LocalDateTime appointmentEnd = appointmentStart.plusHours(1);

            if (now.isAfter(appointmentStart) && now.isBefore(appointmentEnd)) {
                appointment.setAppointmentStatus("Ongoing");
                appointmentRepository.save(appointment);
            }
        }

        return appointments;
    }

    public boolean checkAppointmentIsTaken(LocalDate date, String startTime) {
        return appointmentRepository.existsByAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(date, startTime);
    }

    public List<AppointmentEntity> getAllAppointments() {
        Sort sort = Sort.by(Sort.Direction.ASC, "appointmentDate", "appointmentStartTime");
        return appointmentRepository.findByIsDeletedFalse(sort);
    }

    public AppointmentEntity getAppointmentByAppointmentId(int id) {
        return appointmentRepository.findByAppointmentIdAndIsDeletedFalse(id).get();
    }

    public List<AppointmentEntity> getAppointmentsByStudent(int studentId) {
        StudentEntity student = studentService.getStudentById(studentId);
        List<AppointmentEntity> appointments = appointmentRepository.findByStudentAndIsDeletedFalse(student);

        // Sort appointments by appointmentDate and appointmentStartTime in descending
        // order
        appointments.sort(Comparator.comparing(AppointmentEntity::getAppointmentDate)
                .thenComparing(AppointmentEntity::getAppointmentStartTime));

        return appointments;
    }

    @SuppressWarnings("finally")
    public AppointmentEntity updateAppointment(int id, AppointmentEntity appointment) {
        AppointmentEntity appointmentToUpdate = new AppointmentEntity();
        try {
            appointmentToUpdate = appointmentRepository.findByAppointmentIdAndIsDeletedFalse(id).get();

            appointmentToUpdate.setAppointmentDate(appointment.getAppointmentDate());
            appointmentToUpdate.setAppointmentStartTime(appointment.getAppointmentStartTime());
            appointmentToUpdate.setAppointmentStatus(appointment.getAppointmentStatus());
            appointmentToUpdate.setStudent(appointment.getStudent());
            appointmentToUpdate.setCounselor(appointment.getCounselor());
            appointmentToUpdate.setAppointmentNotes(appointment.getAppointmentNotes());
            appointmentToUpdate.setAppointmentType(appointment.getAppointmentType());
            appointmentToUpdate.setAppointmentPurpose(appointment.getAppointmentPurpose());
        } catch (Exception e) {
            throw new IllegalArgumentException("Appointment " + id + " does not exist.");
        } finally {
            return appointmentRepository.save(appointmentToUpdate);
        }
    }

    public AppointmentEntity updateAppointmentDetails(int id, LocalDate date, String startTime) {
        AppointmentEntity appointmentToUpdate = appointmentRepository.findByAppointmentIdAndIsDeletedFalse(id).get();
        appointmentToUpdate.setAppointmentDate(date);
        appointmentToUpdate.setAppointmentStartTime(startTime);
        return appointmentRepository.save(appointmentToUpdate);
    }

    public AppointmentEntity markAppointmentAsDone(int appointmentId, String notes, String additionalNotes) {
        AppointmentEntity appointment = appointmentRepository.findByAppointmentIdAndIsDeletedFalse(appointmentId).get();
        appointment.setAppointmentStatus("Done");
        appointment.setAppointmentNotes(notes);
        appointment.setAppointmentAdditionalNotes(additionalNotes);

        // add email part
        String message = "Congratulations on completing you appointment. Feedback: " + notes + " Additional Notes: "
                + additionalNotes;

        emailService.sendSimpleMessage(appointment.getStudent().getInstitutionalEmail(), "Appointment Completed",
                message);

        if (appointment.getReferral() != null) {
            ReferralEntity referral = appointment.getReferral();
            String messageToTeacher = "The student you referred has completed their appointment. Student: "
                    + appointment.getStudent().getFirstName()
                    + " " + appointment.getStudent().getLastName() + " Email: "
                    + appointment.getStudent().getInstitutionalEmail() + " Feedback: " + notes + " Additional Notes: "
                    + additionalNotes;
            emailService.sendSimpleMessage(appointment.getReferral().getTeacher().getInstitutionalEmail(),
                    "Appointment Completed", messageToTeacher);

            referral.setStatus("Completed");
            referralRepository.save(referral);
        }

        return appointmentRepository.save(appointment);
    }

    public boolean deleteAppointment(int id) {
        AppointmentEntity appointment = appointmentRepository.findByAppointmentIdAndIsDeletedFalse(id).get();
        if (appointment != null) {
            appointment.setAppointmentStatus("Cancelled");
            appointment.setIsDeleted(true);

            String message = "Appointment has been cancelled. Date: " + appointment.getAppointmentDate()
                    + " Time: "
                    + appointment.getAppointmentStartTime() + " Purpose: " + appointment.getAppointmentPurpose()
                    + " Type: "
                    + appointment.getAppointmentType();

            emailService.sendSimpleMessage(appointment.getStudent().getInstitutionalEmail(), "Appointment Cancelled",
                    message);

            if (appointment.getReferral() != null) {
                ReferralEntity referral = appointment.getReferral();
                String messageToTeacher = "The appointment for the student you referred has been cancelled. Student: "
                        + appointment.getStudent().getFirstName()
                        + " " + appointment.getStudent().getLastName() + " Email: "
                        + appointment.getStudent().getInstitutionalEmail();
                emailService.sendSimpleMessage(appointment.getReferral().getTeacher().getInstitutionalEmail(),
                        "Appointment Cancelled", messageToTeacher);
                referral.setStatus("Cancelled Appointment");
                referralRepository.save(referral);
            }

            appointmentRepository.save(appointment);
            return true;
        } else {
            System.out.println("Appointment " + id + " does not exist.");
            return false;
        }
    }

    public List<AppointmentEntity> getAppointmentsByCounselorId(int counselorId) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Counselor with ID " + counselorId + " does not exist or is deleted."));
        List<AppointmentEntity> appointments = appointmentRepository.findByCounselorAndIsDeletedFalse(counselor);

        // Sort appointments by appointmentDate and appointmentStartTime
        appointments.sort(Comparator.comparing(AppointmentEntity::getAppointmentDate)
                .thenComparing(AppointmentEntity::getAppointmentStartTime));

        return appointments;
    }

}
