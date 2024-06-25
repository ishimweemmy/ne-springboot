package com.ishimweemmy.templates.springboot.v1.services;

import com.ishimweemmy.templates.springboot.v1.models.Message;

public interface IMessageService {
    Message createMessage(Message message);
}