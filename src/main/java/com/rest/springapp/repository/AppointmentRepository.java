package com.rest.springapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rest.springapp.model.Appointment;
import com.rest.springapp.model.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find by status
    List<Appointment> findByStatus(Status status);

    // Pagination and sorting with Pageable parameter
    @SuppressWarnings("null")
    Page<Appointment> findAll(org.springframework.data.domain.Pageable pageable);

    // JPQL Queries   //Fetch salons by name, price range, or rating.
    //Count total appointments for a salon.
    @Query("SELECT a FROM Appointment a WHERE a.serviceName = ?1")
    List<Appointment> findAppointmentsByServiceName(String serviceName);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentTime BETWEEN ?1 AND ?2")
    List<Appointment> findAppointmentsBetweenDates(LocalDateTime start, LocalDateTime end);
}