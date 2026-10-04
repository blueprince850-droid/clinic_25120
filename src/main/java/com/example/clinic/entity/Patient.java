package com.example.clinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient in the clinic.
 * Maps to the PATIENT table from the ER diagram.
 */
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    public Patient() {
        // No-arg constructor is REQUIRED by JPA.
    }

    public Patient(String fullName) {
        this.fullName = fullName;
    }

    // ------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Appointment> getAppointments() {
    return appointments;
}

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}