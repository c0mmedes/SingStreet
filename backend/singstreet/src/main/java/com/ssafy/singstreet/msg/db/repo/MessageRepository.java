package com.ssafy.singstreet.msg.db.repo;

import com.ssafy.singstreet.msg.db.entity.Message;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT msg FROM Message msg WHERE msg.receiverDeleted = false AND msg.isConfirmed = true AND msg.receiver = :user")
    List<Message> findAllByReceiverAndIsConfirmed(@Param("user") User user);

    @Query(value = "SELECT msg FROM Message msg WHERE msg.receiverDeleted = false AND msg.isConfirmed = false AND msg.receiver = :user")
    List<Message> findAllByReceiverAndIsNotConfirmed(@Param("user") User user);

    @Query(value = "SELECT msg FROM Message msg WHERE msg.senderDeleted = false AND msg.sender = :user")
    List<Message> findAllBySender(@Param("user") User user);
}
