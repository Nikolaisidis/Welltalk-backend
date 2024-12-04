package com.communicators.welltalk.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
import com.communicators.welltalk.dto.AppointmentGetDateResponseDTO;
import com.communicators.welltalk.dto.AppointmentResponseDTO;
import com.communicators.welltalk.dto.CounselorResponseDTO;
import com.communicators.welltalk.dto.StudentResponseDTO;

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

    @Autowired
    UserService userService;

    @Autowired
    EmailTemplates emailTemplates;

    public AppointmentEntity counselorSaveAppointment(int counselorId, int studentId, AppointmentEntity appointment) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Counselor with ID " + counselorId + " does not exist or is deleted."));
        StudentEntity student = studentService.getStudentById(studentId);

        // Check if the counselor matches the student's program, college, and assigned
        // year
        boolean isMatchingCounselor = counselor.getProgram().contains(student.getProgram()) &&
                counselor.getCollege().equals(student.getCollege()) &&
                counselor.getAssignedYear().contains(String.valueOf(student.getYear()));

        // Set counselor or outside counselor based on the match
        if (isMatchingCounselor) {
            appointment.setCounselor(counselor);
        } else {
            appointment.setOutsideCounselor(counselor);
        }

        appointment.setStudent(student);
        appointment.setAppointmentStatus("Pending");

        // Save the appointment
        AppointmentEntity appointmentCreated = appointmentRepository.save(appointment);

        // Prepare email message
        String emailAddress;
        if (appointmentCreated.getCounselor() != null) {
            emailAddress = appointmentCreated.getCounselor().getInstitutionalEmail();
        } else if (appointmentCreated.getOutsideCounselor() != null) {
            emailAddress = appointmentCreated.getOutsideCounselor().getInstitutionalEmail();
        } else {
            // Handle case where both are null
            throw new IllegalStateException("Neither counselor nor outside counselor is set.");
        }

        String message = "An appointment has been created for you. Date: " + appointment.getAppointmentDate()
                + " Time: " + appointment.getAppointmentStartTime() + " Purpose: " + appointment.getAppointmentPurpose()
                + " Type: " + appointment.getAppointmentType() + " Please wait for the counselor to assign you a time.";

        emailService.sendSimpleMessage(emailAddress, "Appointment Created", message);
        emailTemplates.studentCreateAppointment(appointmentCreated);

        return appointmentCreated;
    }

    public AppointmentEntity saveAppointment(int id, AppointmentEntity appointment) {
        StudentEntity student = studentService.getStudentById(id);
        appointment.setStudent(student);
        appointment.setAppointmentStatus("Pending");

        List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService.getByStudentId(student.getId());
        List<CounselorEntity> availableCounselors = findAvailableCounselors(assignedCounselors,
                appointment.getAppointmentDate(), appointment.getAppointmentStartTime());

        if (!availableCounselors.isEmpty()) {
            Random random = new Random();
            CounselorEntity selectedCounselor = availableCounselors.get(random.nextInt(availableCounselors.size()));
            appointment.setCounselor(selectedCounselor);
        } else {
            throw new IllegalArgumentException("No available counselor for the selected timeslot.");
        }

        AppointmentEntity appointmentCreated = appointmentRepository.save(appointment);
        emailTemplates.studentCreateAppointment(appointmentCreated);

        return appointmentCreated;
    }

    public AppointmentEntity saveReferralAppointment(int referralId, int counselorId, AppointmentEntity appointment) {
        if (checkAppointmentIsTaken(appointment.getAppointmentDate(), appointment.getAppointmentStartTime())) {
            throw new IllegalArgumentException("Date and Time is already taken");
        }

        ReferralEntity referral = referralRepository.findByReferralIdAndIsDeletedFalse(referralId);
        if (referral == null) {
            throw new IllegalArgumentException("Referral with ID " + referralId + " does not exist or is deleted.");
        }
        appointment.setReferral(referral);

        StudentEntity student;

        appointment.setAppointmentStatus("Pending");
        appointment.setCounselor(counselorService.getCounselorById(counselorId));

        if (studentService.doesStudentExist(referral.getStudentId())) {
            student = studentService.getStudentByStudentId(referral.getStudentId());
            appointment.setStudent(student);
            emailTemplates.studentCreateAppointment(appointment);
        } else if (studentService.doesStudentExistByInstitutionalEmail(referral.getStudentEmail())) {
            student = studentService.getStudentByInstitutionalEmail(referral.getStudentEmail());
            appointment.setStudent(student);
            emailTemplates.studentCreateAppointment(appointment);
        } else {
            StudentEntity studentToCreate = new StudentEntity();
            studentToCreate.setIdNumber(referral.getStudentId());
            studentToCreate.setInstitutionalEmail(referral.getStudentEmail());
            studentToCreate.setFirstName(referral.getStudentFirstName());
            studentToCreate.setLastName(referral.getStudentLastName());
            studentToCreate.setCollege(referral.getStudentCollege());
            studentToCreate.setProgram(referral.getStudentProgram());
            studentToCreate.setIsDeleted(false);
            studentToCreate.setPassword("12345678");
            studentToCreate.setRole(Role.student);
            studentToCreate.setYear(Integer.parseInt(referral.getStudentYear()));
            studentToCreate.setImage("https://ui-avatars.com/api/?name=" + referral.getStudentFirstName() + "+"
                    + referral.getStudentLastName());

            student = authenticationService.registerStudent(studentToCreate);
            appointment.setStudent(student);
            userService.verifyUserAccount(student.getId());
            emailTemplates.referralCreateAccountAppointment(appointment);
        }

        // // Assign available counselor based on assigned counselor service
        // List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService
        // .getByTeacherId(referral.getReferrer().getId());
        // CounselorEntity availableCounselor =
        // findAvailableCounselor(assignedCounselors,
        // appointment.getAppointmentDate(), appointment.getAppointmentStartTime());
        // if (availableCounselor != null) {
        // appointment.setCounselor(availableCounselor);
        // } else {
        // throw new IllegalArgumentException("No available counselor for the selected
        // timeslot.");
        // }
        // //
        appointment.getReferral().setStatus("Appointment Created");

        AppointmentEntity appointmentCreated = appointmentRepository.save(appointment);

        // email student
        emailTemplates.studentCreateAppointment(appointmentCreated);

        // email referrer
        String subject = "Appointment Created for Referred Student";
        String message = "An appointment has been created for the student you referred.";
        emailTemplates.updateToReferrer(subject, message, referral);

        return appointmentCreated;
    }

    // Available Counselor
    private List<CounselorEntity> findAvailableCounselors(List<AssignedCounselorEntity> assignedCounselors,
            LocalDate date,
            String startTime) {
        List<CounselorEntity> availableCounselors = new ArrayList<>();
        for (AssignedCounselorEntity assignedCounselor : assignedCounselors) {
            CounselorEntity counselor = assignedCounselor.getCounselorId();
            if (counselor != null && !isCounselorBusy(counselor, date, startTime)) {
                availableCounselors.add(counselor);
            }
        }
        return availableCounselors;
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
            emailService.sendSimpleMessage(appointment.getReferral().getReferrer().getInstitutionalEmail(),
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

            if (now.isAfter(appointmentStart.plusHours(3))) {
                appointment.setAppointmentStatus("Cancelled");
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

            if (now.isAfter(appointmentStart.plusHours(3))) {
                appointment.setAppointmentStatus("Cancelled");
                appointmentRepository.save(appointment);
            }
        }

        return appointments;
    }

   public List<AppointmentGetDateResponseDTO> getAppointmentsByDateAndAssignedCounselors(LocalDate date, int studentId) {
    List<AssignedCounselorEntity> assignedCounselors = assignedCounselorService.getByStudentId(studentId);
    List<AppointmentGetDateResponseDTO> appointments = new ArrayList<>();

    for (AssignedCounselorEntity assignedCounselor : assignedCounselors) {
        CounselorEntity counselor = assignedCounselor.getCounselorId();
        if (counselor != null) {
            List<AppointmentEntity> counselorAppointments = appointmentRepository
                    .findByCounselorAndAppointmentDateAndIsDeletedFalse(counselor, date);
            if (counselorAppointments.isEmpty()) {
                // Add a placeholder to indicate the counselor is available
                AppointmentGetDateResponseDTO placeholderAppointment = new AppointmentGetDateResponseDTO();
                placeholderAppointment.setAppointmentDate(date);
                placeholderAppointment.setAppointmentStartTime("00:00");
                appointments.add(placeholderAppointment);
            } else {
                appointments.addAll(counselorAppointments.stream()
                        .map(appointment -> new AppointmentGetDateResponseDTO(
                                appointment.getAppointmentDate(),
                                appointment.getAppointmentStartTime(),
                                appointment.getStudent().getId()))
                        .collect(Collectors.toList()));
            }
        }
    }

    return appointments;
}


    public boolean checkAppointmentIsTaken(LocalDate date, String startTime) {
        return appointmentRepository.existsByAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(date, startTime);
    }

    // TO NOTE: This method is temporarily commented out to TEST Appointment DTO
    // public List<AppointmentEntity> getAllAppointments() {
    // Sort sort = Sort.by(Sort.Direction.ASC, "appointmentDate",
    // "appointmentStartTime");
    // return appointmentRepository.findByIsDeletedFalse(sort);
    // }

    private StudentResponseDTO convertStudentToDTO(StudentEntity student) {
        if (student == null)
            return null;
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setInstitutionalEmail(student.getInstitutionalEmail());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setImage(student.getImage());
        dto.setBirthDate(student.getBirthDate());
        dto.setGender(student.getGender());
        dto.setCollege(student.getCollege());
        dto.setProgram(student.getProgram());
        dto.setYear(student.getYear());
        dto.setContactNumber(student.getContactNumber());
        dto.setParentGuardianContactNumber(student.getParentGuardianContactNumber());
        dto.setParentGuardianName(student.getParentGuardianName());
        dto.setCurrentAddress(student.getCurrentAddress());
        dto.setGuardianRelationship(student.getGuardianRelationship());
        dto.setPermanentAddress(student.getPermanentAddress());

        return dto;
    }

    private CounselorResponseDTO convertCounselorToDTO(CounselorEntity counselor) {
        if (counselor == null)
            return null;
        CounselorResponseDTO dto = new CounselorResponseDTO();
        dto.setId(counselor.getId());
        dto.setInstitutionalEmail(counselor.getInstitutionalEmail());
        dto.setFirstName(counselor.getFirstName());
        dto.setLastName(counselor.getLastName());
        dto.setImage(counselor.getImage());
        dto.setCollege(counselor.getCollege());
        dto.setProgram(counselor.getProgram());
        dto.setAssignedYear(counselor.getAssignedYear());
        dto.setIdNumber(counselor.getIdNumber());

        return dto;
    }

    private AppointmentResponseDTO convertToDTO(AppointmentEntity appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentStartTime(appointment.getAppointmentStartTime());
        dto.setAppointmentStatus(appointment.getAppointmentStatus());
        dto.setAppointmentType(appointment.getAppointmentType());
        dto.setAppointmentPurpose(appointment.getAppointmentPurpose());
        dto.setAppointmentAdditionalNotes(appointment.getAppointmentAdditionalNotes());

        // Map nested Student and Counselor
        dto.setStudent(convertStudentToDTO(appointment.getStudent()));
        dto.setCounselor(convertCounselorToDTO(appointment.getCounselor()));

        return dto;
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        Sort sort = Sort.by(Sort.Direction.ASC, "appointmentDate", "appointmentStartTime");
        List<AppointmentEntity> appointments = appointmentRepository.findByIsDeletedFalse(sort);

        // Convert entities to DTOs
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AppointmentEntity getAppointmentByAppointmentId(int id) {
        return appointmentRepository.findByAppointmentIdAndIsDeletedFalse(id).get();
    }

    public List<AppointmentResponseDTO> getAppointmentsByStudent(int studentId) {
        StudentEntity student = studentService.getStudentById(studentId);
        List<AppointmentEntity> appointments = appointmentRepository.findByStudent(student);
    
        appointments.sort(Comparator.comparing(AppointmentEntity::getAppointmentDate)
                .thenComparing(AppointmentEntity::getAppointmentStartTime));
    
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    public AppointmentEntity markAppointmentAsDone(int appointmentId, AppointmentEntity updatedAppointment) {
        AppointmentEntity appointment = appointmentRepository.findByAppointmentIdAndIsDeletedFalse(appointmentId).get();
        appointment.setAppointmentStatus("Done");
        appointment.setAppointmentNotes(updatedAppointment.getAppointmentNotes());
        appointment.setAppointmentAdditionalNotes(updatedAppointment.getAppointmentAdditionalNotes());

        // add email part
        String message = "Congratulations on completing you appointment. Feedback: "
                + updatedAppointment.getAppointmentNotes();

        emailService.sendSimpleMessage(appointment.getStudent().getInstitutionalEmail(), "Appointment Completed",
                message);

        if (appointment.getReferral() != null) {
            if (updatedAppointment.getReferral() != null) {
                appointment.getReferral().setFeedback(updatedAppointment.getReferral().getFeedback());
            }
            appointment.getReferral().setStatus("Completed");
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
                emailService.sendSimpleMessage(appointment.getReferral().getReferrer().getInstitutionalEmail(),
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

        List<AppointmentEntity> appointments = appointmentRepository.findByCounselorOrOutsideCounselor(counselor,
                counselor);

        appointments.sort(Comparator.comparing(AppointmentEntity::getAppointmentDate)
                .thenComparing(AppointmentEntity::getAppointmentStartTime));

        return appointments;
    }

    public long getAppointmentCountByCounselorId(int counselorId) {
        CounselorEntity counselor = counselorRepository.findByIdAndIsDeletedFalse(counselorId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Counselor with ID " + counselorId + " does not exist or is deleted."));

        List<AppointmentEntity> appointments = appointmentRepository.findByCounselorOrOutsideCounselor(counselor,
                counselor);

        return appointments.size();
    }

    public boolean checkCounselorAppointmentIsTaken(LocalDate date, String startTime, int counselorId) {
        CounselorEntity counselor = counselorService.getCounselorById(counselorId);
        return appointmentRepository.existsByCounselorAndAppointmentDateAndAppointmentStartTimeAndIsDeletedFalse(
                counselor,
                date, startTime);
    }

    public AppointmentEntity rescheduleAppointment(int appointmentId, LocalDate date, String startTime) {
        AppointmentEntity appointment = appointmentRepository.findByAppointmentIdAndIsDeletedFalse(appointmentId).get();
        appointment.setAppointmentDate(date);
        appointment.setAppointmentStartTime(startTime);
        appointment.setAppointmentStatus("Pending");

        return appointmentRepository.save(appointment);
    }

    public List<AppointmentResponseDTO> getDoneAppointments() {
        List<AppointmentEntity> appointments = appointmentRepository.findByAppointmentStatusAndIsDeletedFalse("Done");
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
