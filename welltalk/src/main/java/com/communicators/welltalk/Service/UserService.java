package com.communicators.welltalk.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Entity.TeacherEntity;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.CounselorRepository;
import com.communicators.welltalk.Repository.StudentRepository;
import com.communicators.welltalk.Repository.TeacherRepository;
import com.communicators.welltalk.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CounselorRepository counselorRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String institutionalEmail) {
        return userRepository.existsByInstitutionalEmail(institutionalEmail);
    }

    public boolean existsByIdNumber(String idNumber) {
        return userRepository.existsByIdNumber(idNumber);
    }

    public List<UserEntity> getAllIsNotDeletedUsers() {
        return userRepository.findByIsDeletedFalse();
    }

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
            userToUpdate.setVerified(user.isVerified());
            userToUpdate.setCollege(user.getCollege());
            userToUpdate.setProgram(user.getProgram());
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByInstitutionalEmailAndIsDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void assignTeacherOrStudentToCounselor(int userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.isVerified()) {
            Optional<CounselorEntity> counselorOpt;

            if (user instanceof TeacherEntity) {
                TeacherEntity teacher = (TeacherEntity) user;
                counselorOpt = counselorRepository.findByProgramAndCollegeAndAssignedYearAndIsDeletedFalse(
                        teacher.getProgram(), teacher.getCollege(), null);

            } else if (user instanceof StudentEntity) {
                StudentEntity student = (StudentEntity) user;
                counselorOpt = counselorRepository.findByProgramAndCollegeAndAssignedYearAndIsDeletedFalse(
                        student.getProgram(), student.getCollege(), String.valueOf(student.getYear()));
            } else {
                throw new IllegalArgumentException("User must be either a teacher or a student.");
            }

            if (counselorOpt.isPresent()) {
                CounselorEntity counselor = counselorOpt.get();
                System.out.println("Assigned " + user.getFirstName() + " to counselor " + counselor.getFirstName());

            } else {
                System.out.println("No matching counselor found for " + user.getFirstName()
                        + ". You may want to create a new counselor.");
            }
        }
    }

}
