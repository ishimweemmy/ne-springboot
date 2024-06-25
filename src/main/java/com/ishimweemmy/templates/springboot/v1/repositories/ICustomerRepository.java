
package com.ishimweemmy.templates.springboot.v1.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ishimweemmy.templates.springboot.v1.models.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID> {
    Page<Customer> findAll(Pageable pageable);
}