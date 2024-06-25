package com.ishimweemmy.templates.springboot.v1.serviceImpls;

import org.springframework.stereotype.Service;

import com.ishimweemmy.templates.springboot.v1.models.Message;
import com.ishimweemmy.templates.springboot.v1.repositories.IMessageRepository;
import com.ishimweemmy.templates.springboot.v1.services.IMessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {

    private final IMessageRepository messageRepository;

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}