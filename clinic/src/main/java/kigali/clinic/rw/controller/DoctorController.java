package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ---------- STATIC ROUTES (must come BEFORE /{id}) ----------

    @GetMapping("/expired-appointments")
    public List<Appointment> getExpiredPendingAppointments() {
        return doctorService.getExpiredPendingAppointments();
    }

    @GetMapping("/appointments-count")
    public List<Object[]> countAppointmentsPerDoctor() {
        return doctorService.countAppointmentsPerDoctor();
    }

    @GetMapping("/by-specialization/{specName}")
    public List<Appointment> getAppointmentsBySpecialization(@PathVariable String specName) {
        return doctorService.getAppointmentsBySpecialization(specName);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public String createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    // ---------- DYNAMIC ROUTES WITH /{id} ----------

    @GetMapping("/{id}/pending-appointments")
    public List<Appointment> getPendingAppointments(@PathVariable UUID id) {
        return doctorService.getPendingAppointments(id);
    }

    @GetMapping("/{id}/appointments-page")
    public Page<Appointment> getDoctorAppointmentsPaginated(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return doctorService.getDoctorAppointmentsPaginated(id, page, size);
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable UUID id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public String updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable UUID id) {
        return doctorService.deleteDoctor(id);
    }
}
