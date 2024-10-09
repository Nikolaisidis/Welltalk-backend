package com.communicators.welltalk.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AssignedCounselorEntity;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Entity.ReferralTokenEntity;
import com.communicators.welltalk.Entity.TeacherEntity;
import com.communicators.welltalk.Repository.ReferralRepository;
import com.communicators.welltalk.Repository.CounselorRepository;

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

    @Autowired
    CounselorRepository counselorRepository;

    public List<ReferralEntity> getAllReferrals() {
        return referralRepository.findByIsDeletedFalse();
    }

    public ReferralEntity getReferralById(int id) {
        return referralRepository.findByReferralIdAndIsDeletedFalse(id);
    }

    public List<ReferralEntity> getReferralsByReferredById(int id) {
        return referralRepository.findByTeacher_IdAndIsDeletedFalseOrderByReferralIdDesc(id);
    }

    public List<ReferralEntity> getReferralsByCounselorId(int id) {
        return referralRepository.findByCounselor_IdAndIsDeletedFalseOrderByReferralIdDesc(id);
    }

    public ReferralEntity saveReferral(int teacherId, ReferralEntity referral) {
        TeacherEntity teacher = teacherService.getTeacherById(teacherId);
        referral.setTeacher(teacher);

        List<CounselorEntity> counselors = counselorRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        for (CounselorEntity counselor : counselors) {
            if (counselor.getProgram().contains(referral.getStudentProgram()) &&
                    counselor.getCollege().equals(referral.getStudentCollege()) &&
                    counselor.getAssignedYear().contains(String.valueOf(referral.getStudentYear()))) {
                referral.setCounselor(counselor);
                System.out.println("Counselor assigned to referral: " + counselor.getInstitutionalEmail());
            }

        }

        ReferralEntity newReferral = referralRepository.save(referral);

        String token = UUID.randomUUID().toString();
        referralTokenService.createReferralTokenForUser(newReferral, token);

        emailTemplates.sendReferralInvitation(newReferral, token);

        // if student is present, make it a notification and an email

        return newReferral;

    }

    // public ReferralEntity markReferralAsAccepted(int id) {
    // ReferralEntity referral =
    // referralRepository.findByReferralIdAndIsDeletedFalse(id);
    // referral.setStatus("Replied");
    // if (!userService.existsByIdNumber(referral.getStudentId())) {
    // StudentEntity studentToCreate = new StudentEntity();
    // studentToCreate.setIdNumber(referral.getStudentId());
    // studentToCreate.setInstitutionalEmail(referral.getStudentEmail());
    // studentToCreate.setFirstName(referral.getStudentFirstName());
    // studentToCreate.setLastName(referral.getStudentLastName());
    // studentToCreate.setIsDeleted(false);
    // studentToCreate.setPassword("12345678");
    // studentToCreate.setRole(Role.student);

    // authenticationService.registerStudent(studentToCreate);
    // }
    // return referralRepository.save(referral);
    // }

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

    public ReferralEntity referralAcceptedByStudent(String token) {
        ReferralTokenEntity referralTokenEntity = referralTokenService.getReferralToken(token);
        ReferralEntity referral = referralRepository
                .findByReferralIdAndIsDeletedFalse(referralTokenEntity.getReferral().getReferralId());
        referral.setAccepted(true);
        referral.setStatus("Responded");
        referralRepository.save(referral);
        referralTokenService.deleteReferralToken(referralTokenEntity);
        return referral;
    }

    public ReferralEntity referralDeclinedByStudent(String token) {
        ReferralTokenEntity referralTokenEntity = referralTokenService.getReferralToken(token);
        ReferralEntity referral = referralRepository
                .findByReferralIdAndIsDeletedFalse(referralTokenEntity.getReferral().getReferralId());
        referral.setAccepted(false);
        referral.setStatus("Responded");
        referralRepository.save(referral);
        referralTokenService.deleteReferralToken(referralTokenEntity);
        return referral;
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
