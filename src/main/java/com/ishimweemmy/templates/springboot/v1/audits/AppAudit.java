package com.ishimweemmy.templates.springboot.v1.audits;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AppAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", updatable = false, insertable = true)
    protected Instant timestamp;
}
