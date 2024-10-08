package com.communicators.welltalk.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Entity.Role;
import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Entity.TeacherEntity;
import com.communicators.welltalk.Repository.ReferralRepository;

@Service
public class ReferralService {

    @Autowired
    ReferralRepository referralRepository;

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ReferralTokenService referralTokenService;

    @Autowired
    StudentService studentService;

    @Autowired
    AssignedCounselorService assignedCounselorService;

    @Autowired
    CounselorService counselorService;

    @Autowired
    EmailTemplates emailTemplates;

    public List<ReferralEntity> getAllReferrals() {
        return referralRepository.findByIsDeletedFalse();
    }

    public ReferralEntity getReferralById(int id) {
        return referralRepository.findByReferralIdAndIsDeletedFalse(id);
    }

    public List<ReferralEntity> getReferralsByReferredById(int id) {
        return referralRepository.findByTeacher_IdAndIsDeletedFalse(id);
    }

    public ReferralEntity saveReferral(int teacherId, ReferralEntity referral) {
        TeacherEntity teacher = teacherService.getTeacherById(teacherId);
        referral.setTeacher(teacher);

        // if (userService.existsByIdNumber(referral.getStudentId())) {
        // StudentEntity student =
        // studentService.getStudentByStudentId(referral.getStudentId());
        // referral.setStudent(student);
        // assignedCounselorService.assignCounselorToStudent(student);
        // } else if (userService.existsByEmail(referral.getStudentEmail())) {
        // StudentEntity student =
        // studentService.getStudentByInstitutionalEmail(referral.getStudentEmail());
        // referral.setStudent(student);
        // assignedCounselorService.assignCounselorToStudent(student);
        // }

        CounselorEntity counselor = counselorService.getCounselorAssigned(referral.getStudentProgram(),
                referral.getStudentYear(), referral.getStudentCollege());

        referral.setCounselor(counselor);

        ReferralEntity newReferral = referralRepository.save(referral);

        String token = UUID.randomUUID().toString();
        referralTokenService.createReferralTokenForUser(newReferral, token);

        emailTemplates.sendReferralInvitation(newReferral, token);

        // if student is present, make it a notification and an email

        return newReferral;

    }

    public ReferralEntity markReferralAsAccepted(int id) {
        ReferralEntity referral = referralRepository.findByReferralIdAndIsDeletedFalse(id);
        referral.setStatus("Replied");
        if (!userService.existsByIdNumber(referral.getStudentId())) {
            StudentEntity studentToCreate = new StudentEntity();
            studentToCreate.setIdNumber(referral.getStudentId());
            studentToCreate.setInstitutionalEmail(referral.getStudentEmail());
            studentToCreate.setFirstName(referral.getStudentFirstName());
            studentToCreate.setLastName(referral.getStudentLastName());
            studentToCreate.setIsDeleted(false);
            studentToCreate.setPassword("12345678");
            studentToCreate.setRole(Role.student);

            authenticationService.registerStudent(studentToCreate);
        }
        return referralRepository.save(referral);
    }

    @SuppressWarnings("finally")
    public ReferralEntity updateReferral(int id, ReferralEntity referral) {
        ReferralEntity referralToUpdate = new ReferralEntity();
        try {
            referralToUpdate = referralRepository.findByReferralIdAndIsDeletedFalse(id);

            referralToUpdate.setStudentId(referral.getStudentId());
            referralToUpdate.setStudentEmail(referral.getStudentEmail());
            referralToUpdate.setStudentFirstName(referral.getStudentFirstName());
            referralToUpdate.setStudentLastName(referral.getStudentLastName());
            referralToUpdate.setStudentCollege(referral.getStudentCollege());
            referralToUpdate.setStudentProgram(referral.getStudentProgram());
            referralToUpdate.setReason(referral.getReason());
            referralToUpdate.setStatus("Pending");

        } catch (Exception e) {
            throw new IllegalArgumentException("Referral " + referral.getReferralId() + " does not exist.");
        } finally {
            return referralRepository.save(referralToUpdate);
        }
    }

    public boolean deleteReferral(int id) {
        ReferralEntity referral = referralRepository.findByReferralIdAndIsDeletedFalse(id);
        if (referral != null) {
            referral.setIsDeleted(true);
            referralRepository.save(referral);
            return true;
        } else {
            System.out.println("Referral " + id + " does not exist.");
            return false;
        }
    }

}
