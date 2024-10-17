package com.communicators.welltalk.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.Role;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignedCounselorService assignmentService;

    @Autowired
    EmailTemplates emailTemplates;

    public List<UserEntity> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    public List<UserEntity> getAllUnverifiedUsers() {
        return userRepository.findByIsDeletedFalseAndIsVerifiedFalse();
    }

    public List<UserEntity> getAllUnverifiedStudents(int counselorId) {
        UserEntity counselor = userRepository.findByIdAndIsDeletedFalse(counselorId).orElseThrow(() -> 
            new RuntimeException("Counselor not found"));

        String counselorDepartment = counselor.getCollege();

        // Fetch all unverified students in the same department
        return userRepository.findByIsDeletedFalseAndIsVerifiedFalseAndRoleAndCollege(Role.student, counselorDepartment);
    }

    public List<UserEntity> getAllVerifiedUsers() {
        return userRepository.findByIsDeletedFalseAndIsVerifiedTrue();
    }

    public boolean existsByEmail(String institutionalEmail) {
        return userRepository.existsByInstitutionalEmail(institutionalEmail);
    }

    public boolean existsByIdNumber(String idNumber) {
        return userRepository.existsByIdNumber(idNumber);
    }

    // public List<UserEntity> getAllIsNotDeletedUsers() {
    // return userRepository.findByIsDeletedFalse();
    // }

    public UserEntity getUserById(int id) {
        return userRepository.findByIdAndIsDeletedFalse(id).get();
    }

    public UserEntity getUserByIdAndIsNotDeleted(int id) {
        return userRepository.findByIdAndIsDeletedFalse(id).get();
    }

    public boolean isInstitutionalEmailPresent(String institutionalEmail) {
        return userRepository.findByInstitutionalEmailAndIsDeletedFalse(institutionalEmail).isPresent();
    }

    @SuppressWarnings("finally")
    public UserEntity updateUser(int id, UserEntity user) {
        UserEntity userToUpdate = new UserEntity();
        try {
            userToUpdate = userRepository.findByIdAndIsDeletedFalse(id).get();

            userToUpdate.setInstitutionalEmail(user.getInstitutionalEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setGender(user.getGender());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setImage(user.getImage());
        } catch (Exception e) {
            throw new IllegalArgumentException("User " + user.getId() + " does not exist.");
        } finally {
            return userRepository.save(userToUpdate);
        }
    }

    public boolean deleteUser(int id) {
        UserEntity user = userRepository.findById(id).get();
        if (user != null) {
            user.setIsDeleted(true);
            userRepository.save(user);
            return true;
        } else {
            System.out.println("User " + id + " does not exist.");
            return false;
        }
    }

    public boolean verifyUserAccount(int id) {
        UserEntity user = userRepository.findById(id).get();
        if (user != null) {
            user.setIsVerified(true);
            userRepository.save(user);
            assignmentService.assignCounselorIfVerified(user);
            emailTemplates.sendVerificationEmail(user.getInstitutionalEmail());
            return true;
        } else {
            System.out.println("User " + id + " does not exist.");
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByInstitutionalEmailAndIsDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
