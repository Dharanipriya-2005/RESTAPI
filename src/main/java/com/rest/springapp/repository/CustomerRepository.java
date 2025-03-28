package com.rest.springapp.repository;

import com.rest.springapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customers by username
    List<Customer> findByUsername(String username);

    // Find customers by email
    List<Customer> findByEmail(String email);

    // Pagination and sorting
    @SuppressWarnings("null")
    Page<Customer> findAll(Pageable pageable);

    // JPQL Query for custom operation
    @Query("SELECT c FROM Customer c WHERE c.username LIKE %:username%")
    List<Customer> findCustomersByUsernameLike(String username);

    // Corrected method to find customers by username, ignoring case
    List<Customer> findByUsernameContainingIgnoreCase(String username);
}