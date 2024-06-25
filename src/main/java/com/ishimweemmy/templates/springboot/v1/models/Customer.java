package com.ishimweemmy.templates.springboot.v1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}), @UniqueConstraint(columnNames = {"mobile"})})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "mobile")
    private String mobile;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "balance")
    private double balance;

    @Column(name = "last_update_date_time")
    private LocalDateTime lastUpdateDateTime;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();
}