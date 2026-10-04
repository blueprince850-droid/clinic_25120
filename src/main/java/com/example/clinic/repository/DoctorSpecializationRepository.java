package com.example.clinic.repository;

import com.example.clinic.entity.DoctorSpecialization;
import com.example.clinic.entity.DoctorSpecializationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, DoctorSpecializationId> {
    List<DoctorSpecialization> findByDoctor_DoctorId(Long id);
    List<DoctorSpecialization> findBySpecialization_SpecializationId(Long id);
}
