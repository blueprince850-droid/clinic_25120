package kigali.clinic.rw.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Prescription;
import kigali.clinic.rw.service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID appointmentId, @RequestBody Prescription prescription) {
        String msg = prescriptionService.savePrescription(appointmentId, prescription);
        if (msg.equals("The appointment with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.equals("Prescriptions are only for COMPLETED appointments")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
    @GetMapping("/appointment/{appointmentId}")
    public Object getByAppointment(@PathVariable UUID appointmentId) {
        return prescriptionService.getByAppointment(appointmentId);
    }

    @GetMapping("/patient/{ssNumber}")
    public Object getByPatientSs(@PathVariable String ssNumber) {
        return prescriptionService.getByPatientSs(ssNumber);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePrescription(@PathVariable UUID id, @RequestBody Prescription prescription) {
        String msg = prescriptionService.updatePrescription(id, prescription);
        if (msg.equals("The prescription with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable UUID id) {
        String msg = prescriptionService.deletePrescription(id);
        if (msg.equals("The prescription with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        return ResponseEntity.ok(msg);
    }
}