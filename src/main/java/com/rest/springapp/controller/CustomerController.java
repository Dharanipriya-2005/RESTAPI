package com.rest.springapp.controller;

import com.rest.springapp.model.Customer;
import com.rest.springapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Create a new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all customers with pagination and sorting
    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Page<Customer> customers = customerService.getAllCustomers(page, size, sortBy, direction);
        return ResponseEntity.ok(customers);
    }

    // Get customers by username (using JPQL)
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> getCustomersByUsernameLike(@RequestParam String username) {
        List<Customer> customers = customerService.getCustomersByUsernameLike(username);
        return ResponseEntity.ok(customers);
    }

    // Delete customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        // Check if the customer exists first
        if (customerService.getCustomerById(id) != null) {
            customerService.deleteCustomer(id);
            // Return a success message
            return ResponseEntity.ok("Customer with ID " + id + " successfully deleted.");
        } else {
            // Return not found if the customer does not exist
            return ResponseEntity.status(404).body("Customer not found with ID " + id);
        }
    }

    // Update customer by ID
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}