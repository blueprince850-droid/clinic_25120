package com.example.clinic.service;

import com.example.clinic.dto.PatientRequest;
import com.example.clinic.entity.Patient;
import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public Patient create(PatientRequest request) {
        if (request == null || request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Patient full name is required.");
        }

        Patient patient = new Patient();
        patient.setFullName(request.getFullName());
        return patientRepository.save(patient);
    }

    public Patient update(Long id, PatientRequest request) {
        Patient patient = getById(id);

        if (request == null || request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Patient full name is required.");
        }

        patient.setFullName(request.getFullName());
        return patientRepository.save(patient);
    }

    public void delete(Long id) {
        Patient patient = getById(id);
        patientRepository.delete(patient);
    }
}
