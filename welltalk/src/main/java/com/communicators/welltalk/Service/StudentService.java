package com.communicators.welltalk.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CounselorService counselorService;

    @Autowired
    AssignedCounselorService assignedCounselorService;

    public List<StudentEntity> getAllStudents() {
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
            studentToUpdate.setParentGuardianName(student.getParentGuardianName());
            studentToUpdate.setParentGuardianContactNumber(student.getParentGuardianContactNumber());

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
