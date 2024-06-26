package com.ishimweemmy.templates.springboot.v1.controllers;

import com.ishimweemmy.templates.springboot.v1.models.Customer;
import com.ishimweemmy.templates.springboot.v1.payload.response.ApiResponse;
import com.ishimweemmy.templates.springboot.v1.services.ICustomerService;
import com.ishimweemmy.templates.springboot.v1.utils.Constants;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCustomers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Customers fetched successfully", customerService.getAllCustomers(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Customer fetched successfully", customerService.getCustomerById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Customer created successfully", createdCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Customer updated successfully", updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
