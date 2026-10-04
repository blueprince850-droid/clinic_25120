package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public String createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/by-specialization")
    public List<Doctor> getBySpecialization(@RequestParam String name) {
        return doctorService.getDoctorsBySpecialization(name);
    }

    @GetMapping("/without-office")
    public List<Doctor> getWithoutOffice() {
        return doctorService.getDoctorsWithoutOffice();
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
