package com.example.clinic.controller;

import com.example.clinic.entity.Specialization;
import com.example.clinic.service.SpecializationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping
    public ResponseEntity<Specialization> create(@RequestBody Specialization specialization) {
        Specialization saved = specializationService.create(specialization.getName());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Specialization> getAll() {
        return specializationService.getAll();
    }

    @GetMapping("/{id}")
    public Specialization getById(@PathVariable Long id) {
        return specializationService.getById(id);
    }

    @PutMapping("/{id}")
    public Specialization update(@PathVariable Long id, @RequestBody Specialization specialization) {
        return specializationService.update(id, specialization.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specializationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
