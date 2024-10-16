package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService {
    MessageRepository messageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepo){
        this.messageRepository = messageRepo;
    }
    
    public Message persistMessage(Message message){
        int accountPostedBy = message.getPostedBy();
        String text = message.getMessageText();
        long timePosted = message.getTimePostedEpoch();

        Message newMessage = this.messageRepository.save(new Message(accountPostedBy,
                    text, timePosted));

        return newMessage;
    }

    public List<Message> retrieveAllMessages(){
        return this.messageRepository.findAll();
    }

    public Message retrieveMessageById(int id){
        return this.messageRepository.findMessageBymessageId(id);    
    }

    public void deleteMessage(Message message){
        this.messageRepository.delete(message);
    }

    public void updateMessageText(Message message, String text){
        message.setMessageText(text);
    }

    public List<Message> getMessagesFromPostedBy(int postedBy){
        return messageRepository.findMessagesBypostedBy(postedBy);
    }
}
