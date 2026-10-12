package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    @PutMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<String> addSpecialization(@PathVariable UUID doctorId,
                                                    @PathVariable UUID specializationId) {
        String msg = doctorService.addSpecialization(doctorId, specializationId);
        if (msg.equals("The doctor with that id does not exist")
                || msg.equals("That specialization does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.equals("The doctor already has that specialization")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        return ResponseEntity.ok(msg);
    }
    @DeleteMapping("/{doctorId}/specializations/{specializationId}")
    public ResponseEntity<String> removeSpecialization(@PathVariable UUID doctorId,
                                                   @PathVariable UUID specializationId) {
        String msg = doctorService.removeSpecialization(doctorId, specializationId);
        if (msg.equals("The doctor with that id does not exist")
                || msg.equals("That specialization does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.equals("The doctor does not have that specialization")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
        return ResponseEntity.ok(msg);
    }
}