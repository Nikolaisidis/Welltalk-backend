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

    @Autowired
    private AssignedCounselorService assignedCounselorService;

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
        return;
    }

    public void cancelledAppointmentNotificationByStudent(int appointmentID) {
        String type = "appointment_cancelled";
        AppointmentEntity appointment = appointmentService.getAppointmentByAppointmentId(appointmentID);
        UserEntity student = userService.getUserById(appointment.getStudent().getId());
        UserEntity counselor = userService.getUserById(appointment.getAppointmentId());

        // sender = student, receiver = student
        NotificationsEntity StudentToStudent = new NotificationsEntity(type, student, student, appointment);
        notificationsRepository.save(StudentToStudent);

        // sender = student, receiver = counselor
        NotificationsEntity StudentToCounselor = new NotificationsEntity(type, student, counselor, appointment);
        notificationsRepository.save(StudentToCounselor);

        System.out.println("Appointment cancelled notification created");
        return;
    }

    public void markAppointmentAsDoneNotification(int appointmentID) {
        String type = "appointment_done";
        AppointmentEntity appointment = appointmentService.getAppointmentByAppointmentId(appointmentID);
        UserEntity student = userService.getUserById(appointment.getStudent().getId());
        UserEntity counselor = userService.getUserById(appointment.getAppointmentId());

        // sender = counselor, receiver = student
        NotificationsEntity CounselorToStudent = new NotificationsEntity(type, counselor, student, appointment);
        notificationsRepository.save(CounselorToStudent);

        System.out.println("Appointment marked as done");
        return;
    }

    // Referral
    public void createReferralNotification(int teacherId, ReferralEntity referral) {
        String type = "referral";
        UserEntity teacher = userService.getUserById(teacherId);
        UserEntity counselor = userService.getUserById(referral.getCounselor().getId());

        if (referral.getStudent() != null) {
            UserEntity student = userService.getUserById(referral.getStudent().getId());

            // sender = teacher, receiver = student
            NotificationsEntity CounselorToStudent = new NotificationsEntity(type, teacher, student, referral);
            notificationsRepository.save(CounselorToStudent);

        }

        // sender = teacher, receiver = teacher
        NotificationsEntity CounselorToTeacher = new NotificationsEntity(type, teacher, teacher, referral);
        notificationsRepository.save(CounselorToTeacher);

        // sender = teacher, receiver = counselor
        NotificationsEntity TeacherToCounselor = new NotificationsEntity(type, teacher, counselor, referral);
        notificationsRepository.save(TeacherToCounselor);

        return;
    }

    public void acceptedReferralNotification(ReferralEntity referral) {
        String type = "referral_accepted";
        UserEntity teacher = userService.getUserById(referral.getReferrer().getId());
        UserEntity counselor = userService.getUserById(referral.getCounselor().getId());

        if (referral.getStudent() != null) {
            UserEntity student = userService.getUserById(referral.getStudent().getId());

            // sender = student, receiver = student
            NotificationsEntity StudentToStudent = new NotificationsEntity(type, student,
                    student, referral);
            notificationsRepository.save(StudentToStudent);

            // sender = student, receiver = counselor
            NotificationsEntity StudentToCounselor = new NotificationsEntity(type,
                    student, counselor, referral);
            notificationsRepository.save(StudentToCounselor);
        }

        // sender = counselor, receiver = teacher
        NotificationsEntity CounselorToTeacher = new NotificationsEntity(type,
                counselor, teacher, referral);
        notificationsRepository.save(CounselorToTeacher);

        System.out.println("Referral accepted notification created");

        return;
    }

    // public void declinedReferralNotification(ReferralEntity referral) {
    // String type = "referral_declined";
    // UserEntity teacher = userService.getUserById(referral.getReferrer().getId());
    // UserEntity student = userService.getUserById(referral.getStudent().getId());
    // UserEntity counselor =
    // userService.getUserById(referral.getCounselor().getId());

    // // sender = student, receiver = student
    // NotificationsEntity StudentToStudent = new NotificationsEntity(type, student,
    // student, referral);
    // notificationsRepository.save(StudentToStudent);

    // // sender = student, receiver = counselor
    // NotificationsEntity StudentToCounselor = new NotificationsEntity(type,
    // student, counselor, referral);
    // notificationsRepository.save(StudentToCounselor);

    // // sender = counselor, receiver = teacher
    // NotificationsEntity CounselorToTeacher = new NotificationsEntity(type,
    // counselor, teacher, referral);
    // notificationsRepository.save(CounselorToTeacher);

    // System.out.println("Referral declined, notification created");
    // return;
    // }

    // Post
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

    // Assign Counselor
    // public void createAssignedCounselorNotification(StudentEntity student){
    // String type = "assigned_counselor";
    // List<CounselorEntity> counselors =
    // assignedCounselorService.getCounselorsByStudentId(student.getId());

    // try{
    // for (CounselorEntity counselor:counselors){
    // if (student.getIsVerified()) {
    // NotificationsEntity notification = new NotificationsEntity(type, counselor,
    // student);
    // notificationsRepository.save(notification);
    // }
    // }

    // System.out.println("Assigned Counselor Notification Created");
    // } catch (Error e) {
    // System.out.println("Error creating Assigned Counselor Notification");
    // }

    // }

    // Get Notifications
    public List<NotificationsEntity> getNotificationsByReceiver(int receiverId) {
        UserEntity receiver = userService.getUserById(receiverId);
        return notificationsRepository.findByReceiverAndIsDeletedFalse(receiver);
    }

    // Mark as Read
    public void markAsRead(int id) {
        NotificationsEntity notification = notificationsRepository.findByNotificationId(id);
        if (notification != null) {
            notification.setRead(true);
            notificationsRepository.save(notification);
        } else {
            throw new RuntimeException("Notification not found with ID: " + id);
        }
    }

    // Delete
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

}
