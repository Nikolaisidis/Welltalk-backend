package com.communicators.welltalk.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Entity.NotificationsEntity;
import com.communicators.welltalk.Entity.ReferralEntity;
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

    // @Autowired
    // private ReferralService referralService;

    // Appointment
    public NotificationsEntity createAppointmentNotification(int senderId, NotificationsDTO details) {
        String type = "appointment";
        UserEntity sender = userService.getUserById(senderId);
        UserEntity receiver = userService.getUserById(details.getReceiverId());
        AppointmentEntity appointment = appointmentService.getAppointmentByAppointmentId(details.getServiceId());

        NotificationsEntity notification = new NotificationsEntity(type, sender, receiver, appointment);
        return notificationsRepository.save(notification);
    }

    public void createReferralNotification(int teacherId, ReferralEntity referral) {
        String type = "referral";
        UserEntity teacher = userService.getUserById(teacherId);
        // UserEntity student = userService.getUserById(referral.getStudent().getId());
        UserEntity counselor = userService.getUserById(referral.getCounselor().getId());

        // sender = teacher, receiver = teacher (notification to self)
        NotificationsEntity CounselorToTeacher = new NotificationsEntity(type, teacher, teacher, referral);
        notificationsRepository.save(CounselorToTeacher);

        // sender = teacher, receiver = counselor
        NotificationsEntity TeacherToCounselor = new NotificationsEntity(type, teacher, counselor, referral);
        notificationsRepository.save(TeacherToCounselor);

        return;
    }

    public List<NotificationsEntity> getNotificationsByReceiver(int receiverId) {
        UserEntity receiver = userService.getUserById(receiverId);
        return notificationsRepository.findByReceiverAndIsDeletedFalse(receiver);
    }

    public void markAsRead(int id){
        NotificationsEntity notification = notificationsRepository.findByNotificationId(id);
        if (notification != null) {
            notification.setRead(true);
            notificationsRepository.save(notification);
        } else {
            throw new RuntimeException("Notification not found with ID: " + id); 
        }
    }
    
    public boolean deleteNotification(int notificationId) {
        NotificationsEntity notification = notificationsRepository.findByNotificationId(notificationId);

        if (notification != null) {
            notification.setDeleted(true);
            notificationsRepository.save(notification);
            return true;
        } else {
            System.out.println("Notification not found with ID: " + notificationId);
            return false;
        }
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
