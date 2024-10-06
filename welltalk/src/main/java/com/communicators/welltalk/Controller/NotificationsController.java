package com.communicators.welltalk.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.communicators.welltalk.Entity.NotificationsEntity;
import com.communicators.welltalk.Service.NotificationsService;
import com.communicators.welltalk.dto.NotificationsDTO;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    // Appointment
    @PostMapping("/createAppointmentNotification")
    public ResponseEntity<NotificationsEntity> createAppointmentNotification(@RequestParam int senderId,
            @RequestBody NotificationsDTO details) {
        NotificationsEntity newNotification = notificationsService.createAppointmentNotification(senderId,
                details);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    // Referral
    @PostMapping("/createReferralTSNotification")
    public ResponseEntity<NotificationsEntity> createReferralTeacherStudentNotification(@RequestParam int teacherId,
            @RequestBody NotificationsDTO details) {
        NotificationsEntity newNotification = notificationsService.createReferralTeacherStudentNotification(teacherId,
                details);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    @GetMapping("/getNotificationsByReceiver")
    public ResponseEntity<List<NotificationsEntity>> getNotificationsForStudents(@RequestParam int receiverId) {
        List<NotificationsEntity> notifications = notificationsService.getNotificationsByReceiver(receiverId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @DeleteMapping("/deleteNotification")
    public ResponseEntity<Void> deleteNotification(@RequestParam int notificationId) {
        notificationsService.deleteNotification(notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @GetMapping("/getAllNotifications")
    // public ResponseEntity<?> getAllNotifications() {
    // return new ResponseEntity<>(notificationsService.getAllNotifications(),
    // HttpStatus.OK);
    // }

    // @PostMapping("/saveNotification")
    // public ResponseEntity<?> saveNotification(@RequestParam String message,
    // @RequestParam int userId, String type) {
    // NotificationsEntity notification =
    // notificationsService.saveNotification(message, userId, type);
    // webSocketNotificationService.sendNotification(notification.getMessage());
    // return new ResponseEntity<>(notification, HttpStatus.OK);
    // }

    // @PutMapping("/markAsRead")
    // public ResponseEntity<?> markAsRead(@RequestParam Long id) {
    // notificationsService.markAsRead(id);
    // return new ResponseEntity<>(HttpStatus.OK);
    // }

    // @PostMapping("/createNotification")
    // public ResponseEntity<NotificationsEntity> createNotification(@RequestParam
    // int userId, @RequestBody NotificationsEntity notificationDetails) {
    // NotificationsEntity newNotification =
    // notificationsService.createNotification(userId, notificationDetails);
    // return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    // }

    // @GetMapping("/getNotificationsForUser")
    // public ResponseEntity<?> getNotificationsForUser(@RequestParam int userId) {
    // return new
    // ResponseEntity<>(notificationsService.getNotificationsForUser(userId),
    // HttpStatus.OK);
    // }

}
