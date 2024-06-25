package com.ishimweemmy.templates.springboot.v1.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ishimweemmy.templates.springboot.v1.models.Customer;

public interface ICustomerService {
    Page<Customer> getAllCustomers(Pageable pageable);
    Customer getCustomerById(UUID id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(UUID id, Customer customer);
    void deleteCustomer(UUID id);
}