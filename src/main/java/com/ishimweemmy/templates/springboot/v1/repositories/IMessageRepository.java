package com.ishimweemmy.templates.springboot.v1.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ishimweemmy.templates.springboot.v1.models.Message;

@Repository
public interface IMessageRepository extends JpaRepository<Message, UUID> {
}