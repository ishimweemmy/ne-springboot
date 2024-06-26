package com.ishimweemmy.templates.springboot.v1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }),
        @UniqueConstraint(columnNames = { "mobile" }) })
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid mobile number")
    @Column(name = "mobile", unique = true, nullable = false)
    private String mobile;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Column(name = "last_update_date_time", nullable = false)
    private LocalDateTime lastUpdateDateTime;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();

    @PreUpdate
    public void setLastUpdateDateTime() {
        this.lastUpdateDateTime = LocalDateTime.now();
    }
}
