package com.example.clinic.controller;

import com.example.clinic.dto.DoctorSpecializationRequest;
import com.example.clinic.entity.DoctorSpecialization;
import com.example.clinic.service.DoctorSpecializationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-specializations")
public class DoctorSpecializationController {

    private final DoctorSpecializationService doctorSpecializationService;

    public DoctorSpecializationController(DoctorSpecializationService doctorSpecializationService) {
        this.doctorSpecializationService = doctorSpecializationService;
    }

    @PostMapping
    public ResponseEntity<DoctorSpecialization> create(@Valid @RequestBody DoctorSpecializationRequest request) {
        DoctorSpecialization saved = doctorSpecializationService.create(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<DoctorSpecialization> getAll() {
        return doctorSpecializationService.getAll();
    }

    @DeleteMapping("/{doctorId}/{specializationId}")
    public ResponseEntity<Void> delete(@PathVariable Long doctorId, @PathVariable Long specializationId) {
        doctorSpecializationService.delete(doctorId, specializationId);
        return ResponseEntity.noContent().build();
    }
}
