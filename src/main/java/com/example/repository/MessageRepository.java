package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{
    public Message findMessageBymessageId(int id);
    public List<Message> findMessagesBypostedBy(int postedBy);
}
