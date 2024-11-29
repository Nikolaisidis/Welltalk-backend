package com.communicators.welltalk.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.StudentEntity;
import com.communicators.welltalk.Service.AuthenticationService;
import com.communicators.welltalk.Service.StudentService;
import com.communicators.welltalk.dto.StudentResponseDTO;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // @Autowired
    // private TeacherService teacherService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable int id) {
        StudentEntity student = studentService.getStudentById(id);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createStudent")
    public ResponseEntity<StudentEntity> insertStudent(@RequestBody StudentEntity student) {
        StudentEntity newStudent = authenticationService.registerStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable int id, @RequestBody StudentEntity student) {
        StudentEntity updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @PutMapping("/assignCounselor/{studentId}/{teacherId}")
    // public ResponseEntity<Void> assignCounselor(@PathVariable int studentId,
    // @PathVariable int teacherId) {
    // StudentEntity student = studentService.getStudentById(studentId);
    // TeacherEntity teacher = teacherService.getTeacherById(teacherId);
    // if (student != null && teacher != null) {
    // studentService.assignCounselorToStudent(student, teacher);
    // return new ResponseEntity<>(HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
    // }
}
