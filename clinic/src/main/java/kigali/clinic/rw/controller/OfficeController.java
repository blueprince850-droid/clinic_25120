package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.AppointmentService;
import kigali.clinic.rw.service.OfficeService;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public String createOffice(@RequestBody Office office) {
        return officeService.saveOffice(office);
    }

    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAllOffices();
    }

    @GetMapping("/busiest")
    public ResponseEntity<?> busiest() {
        List<Object[]> rows = appointmentService.getBusiestOffice();
        if (rows.isEmpty()) return ResponseEntity.ok("No appointments yet");
        return ResponseEntity.ok(rows.get(0));
    }

    @GetMapping("/{id}")
    public Office getOfficeById(@PathVariable UUID id) {
        return officeService.getOfficeById(id);
    }

    @PutMapping("/{id}")
    public String updateOffice(@PathVariable UUID id, @RequestBody Office office) {
        return officeService.updateOffice(id, office);
    }

    @DeleteMapping("/{id}")
    public String deleteOffice(@PathVariable UUID id) {
        return officeService.deleteOffice(id);
    }
}
