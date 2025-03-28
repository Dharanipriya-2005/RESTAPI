package com.rest.springapp.controller;

import com.rest.springapp.model.Appointment;
import com.rest.springapp.model.Status; 
import com.rest.springapp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController//sending JSON responses to frontend
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired  //automatically inject dependencies
    private AppointmentService appointmentService;

    // Create Appointment
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.createAppointment(appointment));
    }

    // Get All Appointments with Pagination
    @GetMapping
    public ResponseEntity<Page<Appointment>> getAllAppointments(Pageable pageable) {
        return ResponseEntity.ok(appointmentService.getAllAppointments(pageable));
    }

    // Get Appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        Optional<Appointment> updatedAppointment = Optional.ofNullable(appointmentService.updateAppointment(id, appointmentDetails));
        
        // Check if the appointment exists and was updated
        if (updatedAppointment.isPresent()) {
            return ResponseEntity.ok(updatedAppointment.get());
        } else {
            // Return a 404 if the appointment with the given ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted successfully.");
    }

    // Get Appointments by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
    }

    // Get Appointments between dates
    @GetMapping("/dates")
    public ResponseEntity<List<Appointment>> getAppointmentsBetweenDates(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(appointmentService.getAppointmentsBetweenDates(start, end));
    }
}