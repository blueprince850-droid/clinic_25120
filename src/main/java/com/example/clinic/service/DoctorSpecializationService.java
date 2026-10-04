package com.example.clinic.service;

import com.example.clinic.dto.DoctorSpecializationRequest;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.DoctorSpecialization;
import com.example.clinic.entity.DoctorSpecializationId;
import com.example.clinic.entity.Specialization;
import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.DoctorSpecializationRepository;
import com.example.clinic.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorSpecializationService {

    private final DoctorSpecializationRepository doctorSpecializationRepository;
    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    public DoctorSpecializationService(DoctorSpecializationRepository doctorSpecializationRepository,
                                      DoctorRepository doctorRepository,
                                      SpecializationRepository specializationRepository) {
        this.doctorSpecializationRepository = doctorSpecializationRepository;
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

    public List<DoctorSpecialization> getAll() {
        return doctorSpecializationRepository.findAll();
    }

    public DoctorSpecialization getById(Long doctorId, Long specializationId) {
        DoctorSpecializationId id = new DoctorSpecializationId(doctorId, specializationId);
        return doctorSpecializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DoctorSpecialization not found with doctorId: " + doctorId + " and specializationId: " + specializationId));
    }

    public DoctorSpecialization create(DoctorSpecializationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Doctor specialization request is required.");
        }
        if (request.getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor id is required.");
        }
        if (request.getSpecializationId() == null) {
            throw new IllegalArgumentException("Specialization id is required.");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + request.getDoctorId()));
        Specialization specialization = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with id: " + request.getSpecializationId()));

        DoctorSpecializationId id = new DoctorSpecializationId(doctor.getDoctorId(), specialization.getSpecializationId());
        if (doctorSpecializationRepository.existsById(id)) {
            throw new RuntimeException("Doctor specialization pair already exists.");
        }

        DoctorSpecialization doctorSpecialization = new DoctorSpecialization(doctor, specialization);
        return doctorSpecializationRepository.save(doctorSpecialization);
    }

    public void delete(Long doctorId, Long specializationId) {
        DoctorSpecializationId id = new DoctorSpecializationId(doctorId, specializationId);
        if (!doctorSpecializationRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "DoctorSpecialization not found with doctorId: " + doctorId + " and specializationId: " + specializationId);
        }
        doctorSpecializationRepository.deleteById(id);
    }
}
