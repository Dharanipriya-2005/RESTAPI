
package com.rest.springapp.service;

import com.rest.springapp.model.Customer;
import com.rest.springapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Save new customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Get all customers with pagination and sorting
    public Page<Customer> getAllCustomers(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
        return customerRepository.findAll(pageable);
    }

    // Get customers by username like
    public List<Customer> getCustomersByUsernameLike(String username) {
        return customerRepository.findByUsernameContainingIgnoreCase(username);
    }

    // Delete customer by ID
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Update customer by ID
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            
            // Update the customer fields
            existingCustomer.setUsername(customerDetails.getUsername());
            existingCustomer.setEmail(customerDetails.getEmail());
            existingCustomer.setPassword(customerDetails.getPassword()); // Assuming you want to update the password as well

            return customerRepository.save(existingCustomer); // Save and return the updated customer
        } else {
            return null; // Return null if customer doesn't exist
        }
    }
}
