package com.communicators.welltalk.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Service.AppointmentService;
import com.communicators.welltalk.Service.NotificationsService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("student-counselor/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/checkAppointmentIsTaken/{date}/{startTime}")
    public ResponseEntity<Boolean> checkAppointmentIsTaken(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable("startTime") String startTime) {
        boolean isTaken = appointmentService.checkAppointmentIsTaken(date, startTime);
        return new ResponseEntity<>(isTaken, HttpStatus.OK);
    }

    @GetMapping("/getAppointmentsByDate")
    public ResponseEntity<?> getAppointmentsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByDate(date), HttpStatus.OK);
    }

    @PostMapping("/counselorSaveAppointment/{counselorId}")
    public ResponseEntity<AppointmentEntity> counselorSaveAppointment(
            @PathVariable int counselorId,
            @RequestParam int studentId,
            @RequestBody AppointmentEntity appointment) {
        AppointmentEntity newAppointment = appointmentService.counselorSaveAppointment(counselorId, studentId,
                appointment);

        notificationsService.createAppointmentByCounselorNotification(newAppointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<AppointmentEntity> createAppointment(@RequestParam int studentId,
            @RequestBody AppointmentEntity appointment) {
        AppointmentEntity newAppointment = appointmentService.saveAppointment(studentId, appointment);

        notificationsService.createAppointmentByStudentNotification(newAppointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/assignCounselor")
    public ResponseEntity<AppointmentEntity> assignCounselor(@RequestParam String counselorEmail,
            @RequestParam int appointmentId) {
        AppointmentEntity updatedAppointment = appointmentService.assignCounselor(counselorEmail, appointmentId);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<?> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/getAppointmentById/{id}")
    public ResponseEntity<AppointmentEntity> getAppointmentById(@PathVariable int id) {
        return new ResponseEntity<>(appointmentService.getAppointmentByAppointmentId(id), HttpStatus.OK);
    }

    @GetMapping("/getAppointmentsByStudent")
    public ResponseEntity<?> getAppointmentsByStudent(@RequestParam int studentId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByStudent(studentId), HttpStatus.OK);
    }

    @PutMapping("/updateAppointment/{id}")
    public ResponseEntity<AppointmentEntity> updateAppointment(@PathVariable int id,
            @RequestBody AppointmentEntity appointment) {
        AppointmentEntity updatedAppointment = appointmentService.updateAppointment(id, appointment);
        if (updatedAppointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @PutMapping("/updateAppointmentDetails/{appointmentId}")
    public ResponseEntity<AppointmentEntity> updateAppointmentDetails(@PathVariable int appointmentId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("startTime") String startTime) {
        AppointmentEntity updatedAppointment = appointmentService.updateAppointmentDetails(appointmentId, date,
                startTime);
        if (updatedAppointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @PutMapping("/markAppointmentAsDone")
    public ResponseEntity<AppointmentEntity> markAppointmentAsDone(@RequestParam int appointmentId,
            @RequestBody AppointmentEntity appointment) {
        AppointmentEntity updatedAppointment = appointmentService.markAppointmentAsDone(appointmentId,
                appointment.getAppointmentNotes(), appointment.getAppointmentAdditionalNotes());
        
        notificationsService.markAppointmentAsDoneNotification(appointmentId);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAppointment/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        int toDelete = id;
        boolean deleted = appointmentService.deleteAppointment(id);
        if (deleted) {
            notificationsService.cancelledAppointmentNotificationByStudent(toDelete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAppointmentsByCounselorId/{counselorId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByCounselorId(@PathVariable int counselorId) {
        List<AppointmentEntity> appointments = appointmentService.getAppointmentsByCounselorId(counselorId);
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

  
    @GetMapping("/getAppointmentsByDateAndCounselor")
    public ResponseEntity<?> getAppointmentsByDateAndCounselor(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("counselorId") int counselorId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByDateAndCounselor(date, counselorId),
                HttpStatus.OK);
    }



}
