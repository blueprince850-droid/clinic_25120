package com.example.clinic.service;

import com.example.clinic.dto.AppointmentRequest;
import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    public Appointment create(AppointmentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Appointment request is required.");
        }
        if (request.getPatientId() == null) {
            throw new IllegalArgumentException("Patient id is required.");
        }
        if (request.getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor id is required.");
        }
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Appointment date is required.");
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(request.getDate());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, AppointmentRequest request) {
        Appointment appointment = getById(id);

        if (request == null) {
            throw new IllegalArgumentException("Appointment request is required.");
        }
        if (request.getPatientId() == null) {
            throw new IllegalArgumentException("Patient id is required.");
        }
        if (request.getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor id is required.");
        }
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Appointment date is required.");
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(request.getDate());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        return appointmentRepository.save(appointment);
    }

    public void delete(Long id) {
        Appointment appointment = getById(id);
        appointmentRepository.delete(appointment);
    }
}
