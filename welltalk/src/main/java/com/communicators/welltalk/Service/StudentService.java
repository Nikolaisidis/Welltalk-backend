package com.communicators.welltalk.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.StudentRepository;
import com.communicators.welltalk.dto.StudentResponseDTO;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CounselorService counselorService;

    @Autowired
    AssignedCounselorService assignedCounselorService;

    public List<StudentResponseDTO> getAllStudents() {
        List<StudentEntity> students = studentRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        List<StudentResponseDTO> studentDTOs = new ArrayList<>();

        for (StudentEntity student : students) {
            StudentResponseDTO dto = new StudentResponseDTO();
            dto.setId(student.getId());
            dto.setInstitutionalEmail(student.getInstitutionalEmail());
            dto.setFirstName(student.getFirstName());
            dto.setLastName(student.getLastName());
            dto.setImage(student.getImage());
            dto.setRole(student.getRole());
            dto.setIdNumber(student.getIdNumber());
            dto.setCollege(student.getCollege());
            dto.setProgram(student.getProgram());
            dto.setYear(student.getYear());
            dto.setBirthDate(student.getBirthDate());
            dto.setContactNumber(student.getContactNumber());
            dto.setPermanentAddress(student.getPermanentAddress());
            dto.setCurrentAddress(student.getCurrentAddress());
            dto.setParentGuardianName(student.getParentGuardianName());
            dto.setParentGuardianContactNumber(student.getParentGuardianContactNumber());
            dto.setGuardianRelationship(student.getGuardianRelationship());

            studentDTOs.add(dto);
        }
        return studentDTOs;
    }

    // Returns all students without DTO filtering
    public List<StudentEntity> getAllPrivateStudents() {
        return studentRepository.findByIsDeletedFalseAndIsVerifiedTrue();
    }

    public StudentEntity getStudentById(int id) {
        StudentEntity student = studentRepository.findByIdAndIsDeletedFalse(id).get();
        if (student == null) {
            System.out.println("Student " + id + " does not exist.");
        }
        return student;
    }

    @SuppressWarnings("finally")
    public StudentEntity updateStudent(int id, StudentEntity student) {
        StudentEntity studentToUpdate = new StudentEntity();
        try {
            studentToUpdate = studentRepository.findByIdAndIsDeletedFalse(id).get();

            // studentToUpdate.setInstitutionalEmail(student.getInstitutionalEmail());
            studentToUpdate.setFirstName(student.getFirstName());
            studentToUpdate.setLastName(student.getLastName());
            studentToUpdate.setGender(student.getGender());
            // studentToUpdate.setPassword(student.getPassword());
            studentToUpdate.setImage(student.getImage());
            studentToUpdate.setCollege(student.getCollege());
            studentToUpdate.setProgram(student.getProgram());
            studentToUpdate.setYear(student.getYear());
            studentToUpdate.setBirthDate(student.getBirthDate());
            studentToUpdate.setContactNumber(student.getContactNumber());
            studentToUpdate.setPermanentAddress(student.getPermanentAddress());
            studentToUpdate.setCurrentAddress(student.getCurrentAddress());
            studentToUpdate.setParentGuardianName(student.getParentGuardianName());
            studentToUpdate.setParentGuardianContactNumber(student.getParentGuardianContactNumber());
            studentToUpdate.setGuardianRelationship(student.getGuardianRelationship());

            // Save updated student
            studentToUpdate = studentRepository.save(studentToUpdate);

            // Update assignments
            assignedCounselorService.assignCounselorIfVerified(studentToUpdate);

        } catch (Exception e) {
            throw new IllegalArgumentException("Student " + id + " does not exist.");
        } finally {
            return studentToUpdate;
        }
    }

    public boolean deleteStudent(int id) {
        StudentEntity student = studentRepository.findByIdAndIsDeletedFalse(id).get();
        if (student == null) {
            System.out.println("Student " + id + " does not exist.");
            return false;
        } else {
            student.setIsDeleted(true);
            studentRepository.save(student);
            return true;
        }
    }

    public boolean doesStudentExist(String studentId) {
        return studentRepository.existsByIdNumberAndIsDeletedFalse(studentId);
    }

    public boolean doesStudentExistByInstitutionalEmail(String institutionalEmal) {
        return studentRepository.existsByInstitutionalEmailAndIsDeletedFalse(institutionalEmal);
    }

    public StudentEntity getStudentByStudentId(String studentId) {
        return studentRepository.findByIdNumberAndIsDeletedFalse(studentId);
    }

    public StudentEntity getStudentByInstitutionalEmail(String institutionalEmail) {
        return studentRepository.findByInstitutionalEmailAndIsDeletedFalse(institutionalEmail);
    }

    // public void assignCounselorToStudent(StudentEntity student, TeacherEntity
    // teacher) {
    // if (student.getIsVerified()) {
    // counselorService.assignCounselor(student, teacher);
    // }
    // }
}
