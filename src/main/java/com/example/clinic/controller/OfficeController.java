package com.example.clinic.controller;

import com.example.clinic.dto.OfficeRequest;
import com.example.clinic.entity.Office;
import com.example.clinic.service.OfficeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping
    public ResponseEntity<Office> create(@Valid @RequestBody OfficeRequest request) {
        Office saved = officeService.create(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Office> getAll() {
        return officeService.getAll();
    }

    @GetMapping("/{id}")
    public Office getById(@PathVariable Long id) {
        return officeService.getById(id);
    }

    @PutMapping("/{id}")
    public Office update(@PathVariable Long id, @Valid @RequestBody OfficeRequest request) {
        return officeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        officeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
