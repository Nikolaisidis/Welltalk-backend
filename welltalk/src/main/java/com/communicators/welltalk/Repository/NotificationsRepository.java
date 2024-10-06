package com.communicators.welltalk.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.communicators.welltalk.Entity.NotificationsEntity;
import com.communicators.welltalk.Entity.UserEntity;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationsEntity, Integer> {
    
    // Optional<NotificationsEntity> findByNotificationId(Long id);

    // List<NotificationsEntity> findByUser(UserEntity user);

    // List<NotificationsEntity> findByUserOrderByDateDesc(UserEntity user);

    List<NotificationsEntity> findBySender(UserEntity sender);
    List<NotificationsEntity> findByReceiver(UserEntity receiver);
    NotificationsEntity findByNotificationId(int notificationId);

}
