package com.example.clinic.service;

import com.example.clinic.entity.Specialization;
import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public List<Specialization> getAll() {
        return specializationRepository.findAll();
    }

    public Specialization getById(Long id) {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with id: " + id));
    }

    public Specialization create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Specialization name is required.");
        }

        Specialization specialization = new Specialization(name);
        return specializationRepository.save(specialization);
    }

    public Specialization update(Long id, String name) {
        Specialization specialization = getById(id);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Specialization name is required.");
        }

        specialization.setName(name);
        return specializationRepository.save(specialization);
    }

    public void delete(Long id) {
        Specialization specialization = getById(id);
        specializationRepository.delete(specialization);
    }
}
