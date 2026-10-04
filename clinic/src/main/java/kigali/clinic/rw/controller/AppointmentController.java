package kigali.clinic.rw.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public String createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // A2
    @GetMapping("/by-status")
    public List<Appointment> getByStatus(@RequestParam AppointmentStatus status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    // A3 ? parse strings into LocalDate here, then convert to java.sql.Date
    @GetMapping("/between")
    public List<Appointment> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate localStart = LocalDate.parse(start);
        LocalDate localEnd   = LocalDate.parse(end);
        Date startDate = Date.valueOf(localStart);
        Date endDate   = Date.valueOf(localEnd);
        return appointmentService.getAppointmentsBetween(startDate, endDate);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable UUID id) {
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}")
    public String updateAppointment(@PathVariable UUID id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable UUID id) {
        return appointmentService.deleteAppointment(id);
    }
}
