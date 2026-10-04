package com.example.clinic.service;

import com.example.clinic.dto.DoctorRequest;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Office;
import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final OfficeRepository officeRepository;

    public DoctorService(DoctorRepository doctorRepository, OfficeRepository officeRepository) {
        this.doctorRepository = doctorRepository;
        this.officeRepository = officeRepository;
    }

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    public Doctor create(DoctorRequest request) {
        if (request == null || request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Doctor full name is required.");
        }
        if (request.getOfficeId() == null) {
            throw new IllegalArgumentException("Office id is required.");
        }

        Office office = officeRepository.findById(request.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with id: " + request.getOfficeId()));

        Doctor doctor = new Doctor();
        doctor.setFullName(request.getFullName());
        doctor.setOffice(office);
        return doctorRepository.save(doctor);
    }

    public Doctor update(Long id, DoctorRequest request) {
        Doctor doctor = getById(id);

        if (request == null || request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Doctor full name is required.");
        }
        if (request.getOfficeId() == null) {
            throw new IllegalArgumentException("Office id is required.");
        }

        Office office = officeRepository.findById(request.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with id: " + request.getOfficeId()));

        doctor.setFullName(request.getFullName());
        doctor.setOffice(office);
        return doctorRepository.save(doctor);
    }

    public void delete(Long id) {
        Doctor doctor = getById(id);
        doctorRepository.delete(doctor);
    }
}
