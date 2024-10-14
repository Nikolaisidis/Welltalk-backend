package com.communicators.welltalk.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.communicators.welltalk.Entity.AppointmentEntity;
import com.communicators.welltalk.Entity.NotificationsEntity;
import com.communicators.welltalk.Entity.PostEntity;
import com.communicators.welltalk.Entity.ReferralEntity;
import com.communicators.welltalk.Entity.StudentEntity;
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
    private StudentService studentService;

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

    // Appointment
    public void createAppointmentByStudentNotification(AppointmentEntity appointment) {
        String type = "appointment";
        UserEntity student = userService.getUserById(appointment.getStudent().getId());
        UserEntity counselor = userService.getUserById(appointment.getCounselor().getId());

        // sender = student, receiver = student
        NotificationsEntity StudentToStudent = new NotificationsEntity(type, student, student, appointment);
        notificationsRepository.save(StudentToStudent);

        // sender = student, receiver = counselor
        NotificationsEntity StudentToCounselor = new NotificationsEntity(type, student, counselor, appointment);
        notificationsRepository.save(StudentToCounselor);

        System.out.println("Appointment by Student Notification Created");
        return;
    }

    public void createAppointmentByCounselorNotification(AppointmentEntity appointment) {
        String type = "appointment";
        UserEntity counselor = userService.getUserById(appointment.getCounselor().getId());
        UserEntity student = userService.getUserById(appointment.getStudent().getId());
        
        // sender = counselor, receiver = counselor
        NotificationsEntity CounselorToCounselor = new NotificationsEntity(type, counselor, counselor, appointment);
        notificationsRepository.save(CounselorToCounselor);

        // sender = counselor, receiver = student
        NotificationsEntity CounselorToStudent = new NotificationsEntity(type, counselor, student, appointment);
        notificationsRepository.save(CounselorToStudent);

        System.out.println("Appointment by Counselor Notification Created");
        return;
    }

    // Referral
    public void createReferralNotification(int teacherId, ReferralEntity referral) {
        String type = "referral";
        UserEntity teacher = userService.getUserById(teacherId);
        UserEntity student = userService.getUserById(referral.getStudent().getId());
        UserEntity counselor = userService.getUserById(referral.getCounselor().getId());

        // sender = teacher, receiver = teacher
        NotificationsEntity CounselorToTeacher = new NotificationsEntity(type, teacher, teacher, referral);
        notificationsRepository.save(CounselorToTeacher);

        // sender = teacher, receiver = counselor 
        NotificationsEntity TeacherToCounselor = new NotificationsEntity(type, teacher, counselor, referral);
        notificationsRepository.save(TeacherToCounselor);

        // sender = teacher, receiver = student
        NotificationsEntity CounselorToStudent = new NotificationsEntity(type, teacher, student, referral);
        notificationsRepository.save(CounselorToStudent);

        return;
    }

    public void acceptedReferralNotification(ReferralEntity referral) {
        String type = "referral_accepted";
        UserEntity teacher = userService.getUserById(referral.getTeacher().getId());
        UserEntity student = userService.getUserById(referral.getStudent().getId());
        UserEntity counselor = userService.getUserById(referral.getCounselor().getId());

        // sender = student, receiver = student 
        NotificationsEntity StudentToStudent = new NotificationsEntity(type, student, student, referral);
        notificationsRepository.save(StudentToStudent);

        // sender = student, receiver = counselor 
        NotificationsEntity StudentToCounselor = new NotificationsEntity(type, student, counselor, referral);
        notificationsRepository.save(StudentToCounselor);

        // sender = counselor, receiver = teacher 
        NotificationsEntity CounselorToTeacher = new NotificationsEntity(type, counselor, teacher, referral);
        notificationsRepository.save(CounselorToTeacher);

        System.out.println("Referral accepted notification created");

        return;
    }

    public void declinedReferralNotification(ReferralEntity referral) {
        String type = "referral_declined";
        UserEntity teacher = userService.getUserById(referral.getTeacher().getId());
        UserEntity student = userService.getUserById(referral.getStudent().getId());
        UserEntity counselor = userService.getUserById(referral.getCounselor().getId());

        // sender = student, receiver = student 
        NotificationsEntity StudentToStudent = new NotificationsEntity(type, student, student, referral);
        notificationsRepository.save(StudentToStudent);

        // sender = student, receiver = counselor 
        NotificationsEntity StudentToCounselor = new NotificationsEntity(type, student, counselor, referral);
        notificationsRepository.save(StudentToCounselor);

        // sender = counselor, receiver = teacher 
        NotificationsEntity CounselorToTeacher = new NotificationsEntity(type, counselor, teacher, referral);
        notificationsRepository.save(CounselorToTeacher);

        System.out.println("Referral declined, notification created");
        return;
    }

    public void createPostNotification(int counselorId, PostEntity post) {
        String type = "post";
        UserEntity counselor = userService.getUserById(counselorId);

        List<StudentEntity> students = studentService.getAllStudents();
        for (StudentEntity student : students) {
            NotificationsEntity notification = new NotificationsEntity(type, counselor, student, post);
            notificationsRepository.save(notification);
        }

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
