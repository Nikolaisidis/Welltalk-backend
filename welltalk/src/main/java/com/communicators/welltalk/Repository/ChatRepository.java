package com.communicators.welltalk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.communicators.welltalk.Entity.ChatEntity;

import java.util.List;
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
      @Query("SELECT c FROM ChatEntity c WHERE (c.senderId = :senderId AND c.receiverId = :receiverId) OR (c.senderId = :receiverId AND c.receiverId = :senderId)")
    List<ChatEntity> findChatBetweenUsers(@Param("senderId") int senderId, @Param("receiverId") int receiverId);

    List<ChatEntity> findByReceiverId(int receiverId);

}


