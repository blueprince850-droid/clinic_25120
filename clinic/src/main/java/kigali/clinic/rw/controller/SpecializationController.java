package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.SpecializationService;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @PostMapping
    public String createSpecialization(@RequestBody Specialization specialization) {
        return specializationService.saveSpecialization(specialization);
    }

    @GetMapping
    public List<Specialization> getAllSpecializations() {
        return specializationService.getAllSpecializations();
    }

    @GetMapping("/{id}")
    public Specialization getSpecializationById(@PathVariable UUID id) {
        return specializationService.getSpecializationById(id);
    }

    @PutMapping("/{id}")
    public String updateSpecialization(@PathVariable UUID id, @RequestBody Specialization specialization) {
        return specializationService.updateSpecialization(id, specialization);
    }

    @DeleteMapping("/{id}")
    public String deleteSpecialization(@PathVariable UUID id) {
        return specializationService.deleteSpecialization(id);
    }
}

