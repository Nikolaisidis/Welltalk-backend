package com.communicators.welltalk.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AuthenticationResponse;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Entity.TeacherEntity;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.CounselorRepository;
import com.communicators.welltalk.Repository.StudentRepository;
import com.communicators.welltalk.Repository.TeacherRepository;
import com.communicators.welltalk.Repository.UserRepository;
import com.communicators.welltalk.dto.PasswordChangeDTO;
import com.communicators.welltalk.dto.PasswordVerificationDTO;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CounselorRepository counselorRepository;
    private final AuthenticationManager authenticationManager;
    private final AssignedCounselorService assignmentService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager, StudentRepository studentRepository,
            TeacherRepository teacherRepository, CounselorRepository counselorRepository,
            AssignedCounselorService assignmentService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.counselorRepository = counselorRepository;
        this.assignmentService = assignmentService;
    }

    public boolean existsByEmail(String institutionalEmail) {
        return userRepository.existsByInstitutionalEmail(institutionalEmail);
    }

    public UserEntity register(UserEntity request) {
        if (existsByEmail(request.getInstitutionalEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID number already exists");
        }

        UserEntity user = new UserEntity();

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(request);

        assignmentService.assignCounselorIfVerified(user);

        return user;
    }

    public StudentEntity registerStudent(StudentEntity request) {
        if (existsByEmail(request.getInstitutionalEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID number already exists");
        }

        StudentEntity student = new StudentEntity();

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        student = studentRepository.save(request);

        assignmentService.assignCounselorIfVerified(student);

        return student;
    }

    public TeacherEntity registerTeacher(TeacherEntity request) {
        if (existsByEmail(request.getInstitutionalEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID number already exists");
        }

        TeacherEntity teacher = new TeacherEntity();

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        teacher = teacherRepository.save(request);

        assignmentService.assignCounselorIfVerified(teacher);

        return teacher;
    }

    public CounselorEntity registerCounselor(CounselorEntity request) {
        if (existsByEmail(request.getInstitutionalEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID number already exists");
        }

        CounselorEntity counselor = new CounselorEntity();

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        counselor = counselorRepository.save(request);

        return counselor;
    }

    public AuthenticationResponse authenticate(UserEntity request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getInstitutionalEmail(), request.getPassword()

                ));

        UserEntity user = userRepository.findByInstitutionalEmail(request.getInstitutionalEmail());
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);

    }

    public boolean changePassword(PasswordChangeDTO request) {
        UserEntity user;
        if (existsByEmail(request.getEmail())) {
            user = userRepository.findByInstitutionalEmail(request.getEmail());
        } else {
            throw new RuntimeException("Email does not exist: " + request.getEmail());
        }

        // Check if the old password matches
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        // Encode the new password and set it to the user
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Save the updated user
        userRepository.save(user);
        return true;
    }

    public boolean verifyPassword(PasswordVerificationDTO request) {
        UserEntity user;
        if (existsByEmail(request.getEmail())) {
            user = userRepository.findByInstitutionalEmail(request.getEmail());
        } else {
            throw new RuntimeException("Email does not exist: " + request.getEmail());
        }

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

}
