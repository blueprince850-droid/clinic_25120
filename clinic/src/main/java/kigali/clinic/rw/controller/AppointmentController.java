package kigali.clinic.rw.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
        String msg = appointmentService.saveAppointment(appointment);
        if (msg.equals("Doctor is already booked on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/by-status")
    public List<Appointment> getByStatus(@RequestParam AppointmentStatus status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    @GetMapping("/between")
    public List<Appointment> getBetween(@RequestParam String start, @RequestParam String end) {
        Date startDate = Date.valueOf(LocalDate.parse(start));
        Date endDate   = Date.valueOf(LocalDate.parse(end));
        return appointmentService.getAppointmentsBetween(startDate, endDate);
    }

    @GetMapping("/stats/by-status")
    public List<Object[]> statsByStatus() {
        return appointmentService.countByStatus();
    }

    @GetMapping("/stats/busiest-office")
    public ResponseEntity<?> busiestOffice() {
        List<Object[]> rows = appointmentService.getBusiestOffice();
        if (rows.isEmpty()) return ResponseEntity.ok("No appointments yet");
        return ResponseEntity.ok(rows.get(0));
    }

    @PatchMapping("/cancel-day")
    public String cancelDay(@RequestParam UUID doctorId, @RequestParam String date) {
        int n = appointmentService.cancelAppointmentsOfDay(doctorId, Date.valueOf(LocalDate.parse(date)));
        return n + " appointments cancelled";
    }

    @GetMapping("/page")
    public Page<Appointment> page(@RequestParam int page,
                                  @RequestParam int size,
                                  @RequestParam String sort) {
        return appointmentService.getPage(page, size, sort);
    }

    @DeleteMapping("/cancelled-before")
    public String cleanCancelledBefore(@RequestParam String date) {
        int n = appointmentService.cleanCancelledBefore(Date.valueOf(LocalDate.parse(date)));
        return n + " appointments deleted";
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
