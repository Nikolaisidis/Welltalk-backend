package com.communicators.welltalk.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Entity.NotificationsEntity;
import com.communicators.welltalk.Entity.UserEntity;
import com.communicators.welltalk.Repository.NotificationsRepository;
import com.communicators.welltalk.dto.NotificationsDTO;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    // Appointment
    public NotificationsEntity createAppointmentNotification(int senderId, NotificationsDTO notificationDetails) {
        String type = "appointment";
        UserEntity sender = userService.getUserById(senderId);
        UserEntity receiver = userService.getUserById(notificationDetails.getReceiverId());
        AppointmentEntity appointment = appointmentService.getAppointmentByAppointmentId(notificationDetails.getAppointmentId());

        NotificationsEntity notification = new NotificationsEntity(type, sender, receiver, appointment);
        return notificationsRepository.save(notification);
    }

    public List<NotificationsEntity> getNotificationsForStudents(int receiverId) {
        UserEntity receiver = userService.getUserById(receiverId);
        return notificationsRepository.findByReceiver(receiver);
    }

    public List<NotificationsEntity> getNotificationsForCounselors(int senderId, int receiverId) {
        UserEntity sender = userService.getUserById(senderId);
        UserEntity receiver = userService.getUserById(receiverId);
       
        
        List<NotificationsEntity> senderNotifications = notificationsRepository.findBySender(sender);
        List<NotificationsEntity> receiverNotifications = notificationsRepository.findByReceiver(receiver);
        
        List<NotificationsEntity> notifications = new ArrayList<>();
        notifications.addAll(senderNotifications);
        notifications.addAll(receiverNotifications);
        
        return notifications;
    }

    public void deleteNotification(int notificationId) {
        notificationsRepository.deleteById(notificationId);
    }
    // public List<NotificationsEntity> getNotificationsForUser(int userId) {
    //     UserEntity user = userService.getUserById(userId);
    //     return notificationsRepository.findByUser(user);
    // }

    // public List<NotificationsEntity> getNotificationsForUser(int userId) {
    //     UserEntity user = userService.getUserById(userId);
    //     return notificationsRepository.findByUser(user);
    // }

    // public List<NotificationsEntity> getAllNotifications() {
    //     return notificationsRepository.findAll();
    // }

    // public NotificationsEntity saveNotification(String message, int userId, String type) {
    //     UserEntity user = userService.getUserById(userId);
    //     NotificationsEntity notification = new NotificationsEntity(message, user, type);
    //     notification.setUser(user);

    //     return notificationsRepository.save(notification);
    // }

    // public void markAsRead(Long id) {
    //     NotificationsEntity notification = notificationsRepository.findByNotificationId(id)
    //             .orElseThrow(() -> new RuntimeException("Notification not found"));
    //     notification.setRead(true);
    //     notificationsRepository.save(notification);
    // }

    

}
