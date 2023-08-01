package com.ssafy.singstreet.msg.db.repo;

import com.ssafy.singstreet.msg.db.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
