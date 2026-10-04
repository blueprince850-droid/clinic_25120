package com.example.clinic.repository;

import com.example.clinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_PatientId(Long id);
    List<Appointment> findByDoctor_DoctorId(Long id);
}
