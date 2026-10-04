package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public String createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/by-last-name")
    public List<Patient> getByLastName(@RequestParam String lastName) {
        return patientService.getPatientsByLastName(lastName);
    }

    @GetMapping("/of-doctor/{doctorId}")
    public ResponseEntity<?> getPatientsOfDoctor(@PathVariable UUID doctorId) {
        if (!patientService.doctorExists(doctorId)) {
            return ResponseEntity.status(404).body("The doctor with that id does not exist");
        }
        return ResponseEntity.ok(patientService.getPatientsOfDoctor(doctorId));
    }

    @GetMapping("/frequent")
    public List<Patient> getFrequent(@RequestParam long min) {
        return patientService.getFrequentPatients(min);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable UUID id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/{id}")
    public String updatePatient(@PathVariable UUID id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable UUID id) {
        return patientService.deletePatient(id);
    }
}
