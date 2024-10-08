package com.communicators.welltalk.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AssignedCounselorEntity;
import com.communicators.welltalk.Entity.ChatEntity;
import com.communicators.welltalk.Entity.CounselorEntity;
import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Entity.TeacherEntity;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.AssignedCounselorRepository;
import com.communicators.welltalk.Repository.ChatRepository;
import com.communicators.welltalk.Repository.CounselorRepository;
import com.communicators.welltalk.Repository.StudentRepository;
import com.communicators.welltalk.Repository.TeacherRepository;

@Service
public class AssignedCounselorService {

    @Autowired
    CounselorRepository counselorRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AssignedCounselorRepository assignedCounselorRepository;

    @Autowired
    ChatRepository chatRepository;

    public List<CounselorEntity> getCounselorsByReceiverId(int receiverId) {
        List<ChatEntity> chats = chatRepository.findByReceiverId(receiverId);
        List<Integer> counselorIds = chats.stream()
                .map(ChatEntity::getSenderId)
                .distinct()
                .collect(Collectors.toList());

        return counselorIds.stream()
                .map(counselorRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void assignCounselorIfVerified(UserEntity user) {
        if (user.getIsVerified()) {
            if (user instanceof StudentEntity) {
                assignCounselorToStudent((StudentEntity) user);
            } else if (user instanceof TeacherEntity) {
                assignCounselorToTeacher((TeacherEntity) user);
            } else if (user instanceof CounselorEntity) {
                assignCounselorToExistingUsers((CounselorEntity) user);
            }
        }
    }

    public void assignCounselorToStudent(StudentEntity student) {
        // Remove existing assignments for the student
        List<AssignedCounselorEntity> existingAssignments = assignedCounselorRepository
                .findByStudentId(student.getId());
        assignedCounselorRepository.deleteAll(existingAssignments);

        List<CounselorEntity> counselors = counselorRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        for (CounselorEntity counselor : counselors) {
            if (counselor.getProgram().contains(student.getProgram()) &&
                    counselor.getCollege().equals(student.getCollege()) &&
                    counselor.getAssignedYear().contains(String.valueOf(student.getYear()))) {

                AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
                assignedCounselor.setStudentId(student.getId());
                assignedCounselor.setCounselorId(counselor.getId());
                assignedCounselor.setProgram(student.getProgram());
                assignedCounselor.setYear(student.getYear());
                assignedCounselor.setCollege(student.getCollege());

                assignedCounselorRepository.save(assignedCounselor);
            }
        }
    }

    private void assignCounselorToTeacher(TeacherEntity teacher) {
        List<AssignedCounselorEntity> existingAssignments = assignedCounselorRepository
                .findByTeacherId(teacher.getId());
        assignedCounselorRepository.deleteAll(existingAssignments);

        List<CounselorEntity> counselors = counselorRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        for (CounselorEntity counselor : counselors) {
            if (counselor.getProgram().contains(teacher.getProgram()) &&
                    counselor.getCollege().equals(teacher.getCollege())) {

                AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
                assignedCounselor.setTeacherId(teacher.getId());
                assignedCounselor.setCounselorId(counselor.getId());
                assignedCounselor.setProgram(teacher.getProgram());
                assignedCounselor.setCollege(teacher.getCollege());

                assignedCounselorRepository.save(assignedCounselor);
            }
        }
    }

    private void assignCounselorToExistingUsers(CounselorEntity counselor) {
        List<AssignedCounselorEntity> existingAssignments = assignedCounselorRepository
                .findByCounselorId(counselor.getId());
        assignedCounselorRepository.deleteAll(existingAssignments);

        // Assign to existing verified students
        List<StudentEntity> students = studentRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        for (StudentEntity student : students) {
            if (counselor.getProgram().contains(student.getProgram()) &&
                    counselor.getCollege().equals(student.getCollege()) &&
                    counselor.getAssignedYear().contains(String.valueOf(student.getYear()))) {

                AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
                assignedCounselor.setStudentId(student.getId());
                assignedCounselor.setCounselorId(counselor.getId());
                assignedCounselor.setProgram(student.getProgram());
                assignedCounselor.setYear(student.getYear());
                assignedCounselor.setCollege(student.getCollege());

                assignedCounselorRepository.save(assignedCounselor);
            }
        }

        // Assign to existing verified teachers
        List<TeacherEntity> teachers = teacherRepository.findByIsDeletedFalseAndIsVerifiedTrue();
        for (TeacherEntity teacher : teachers) {
            if (counselor.getProgram().contains(teacher.getProgram()) &&
                    counselor.getCollege().equals(teacher.getCollege())) {

                AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
                assignedCounselor.setTeacherId(teacher.getId());
                assignedCounselor.setCounselorId(counselor.getId());
                assignedCounselor.setProgram(teacher.getProgram());
                assignedCounselor.setCollege(teacher.getCollege());

                assignedCounselorRepository.save(assignedCounselor);
            }
        }
    }

    public void updateAssignmentsForCounselor(CounselorEntity counselor) {
        // Remove existing assignments
        List<AssignedCounselorEntity> existingAssignments = assignedCounselorRepository
                .findByCounselorId(counselor.getId());
        assignedCounselorRepository.deleteAll(existingAssignments);

        // Reassign students
        List<StudentEntity> students = studentRepository.findByIsDeletedFalse();
        for (StudentEntity student : students) {
            if (counselor.getProgram().contains(student.getProgram()) &&
                    counselor.getCollege().equals(student.getCollege()) &&
                    counselor.getAssignedYear().contains(String.valueOf(student.getYear()))) {

                AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
                assignedCounselor.setStudentId(student.getId());
                assignedCounselor.setCounselorId(counselor.getId());
                assignedCounselor.setProgram(student.getProgram());
                assignedCounselor.setYear(student.getYear());
                assignedCounselor.setCollege(student.getCollege());

                assignedCounselorRepository.save(assignedCounselor);
            }
        }

        // Reassign teachers
        List<TeacherEntity> teachers = teacherRepository.findByIsDeletedFalse();
        for (TeacherEntity teacher : teachers) {
            if (counselor.getProgram().contains(teacher.getProgram()) &&
                    counselor.getCollege().equals(teacher.getCollege())) {

                AssignedCounselorEntity assignedCounselor = new AssignedCounselorEntity();
                assignedCounselor.setTeacherId(teacher.getId());
                assignedCounselor.setCounselorId(counselor.getId());
                assignedCounselor.setProgram(teacher.getProgram());
                assignedCounselor.setCollege(teacher.getCollege());

                assignedCounselorRepository.save(assignedCounselor);
            }
        }
    }

    public List<AssignedCounselorEntity> getByCounselorId(int counselorId) {
        return assignedCounselorRepository.findByCounselorId(counselorId);
    }

    public List<AssignedCounselorEntity> getByStudentId(int studentId) {
        return assignedCounselorRepository.findByStudentId(studentId);
    }

    public List<AssignedCounselorEntity> getByTeacherId(int teacherId) {
        return assignedCounselorRepository.findByTeacherId(teacherId);
    }

    public Optional<StudentEntity> getStudentById(int studentId) {
        return studentRepository.findById(studentId);
    }

    public Optional<TeacherEntity> getTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public Optional<CounselorEntity> getCounselorById(int counselorId) {
        return counselorRepository.findById(counselorId);
    }
}
