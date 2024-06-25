package com.ishimweemmy.templates.springboot.v1.serviceImpls;

import com.ishimweemmy.templates.springboot.v1.exceptions.ResourceNotFoundException;
import com.ishimweemmy.templates.springboot.v1.models.Customer;
import com.ishimweemmy.templates.springboot.v1.repositories.*;
import com.ishimweemmy.templates.springboot.v1.services.*;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(UUID id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setMobile(customer.getMobile());
        existingCustomer.setDob(customer.getDob());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        customerRepository.deleteById(id);
    }
}
