package com.rest.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rest.springapp.model.Appointment;
import com.rest.springapp.repository.AppointmentRepository;
import com.rest.springapp.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Create Appointment
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment); // Correct usage for saving a single appointment
    }

    // Create Multiple Appointments
    public List<Appointment> createAppointments(List<Appointment> appointments) {
        return appointmentRepository.saveAll(appointments); // Correct usage for saving multiple appointments
    }

    // Get All Appointments with Pagination
    public Page<Appointment> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    // Get Appointment by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Update Appointment
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setServiceName(appointmentDetails.getServiceName());
        appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        appointment.setStatus(appointmentDetails.getStatus());

        return appointmentRepository.save(appointment);
    }

    // Delete Appointment
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Find Appointments by Status
    public List<Appointment> getAppointmentsByStatus(Status status) {
        return appointmentRepository.findByStatus(status);
    }

    // Find Appointments between dates
    public List<Appointment> getAppointmentsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findAppointmentsBetweenDates(start, end);
    }
}