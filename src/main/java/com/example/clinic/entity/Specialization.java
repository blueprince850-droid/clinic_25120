package com.example.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a medical specialization (e.g., Cardiology, Neurology).
 * Maps to the SPECIALIZATION table from the ER diagram.
 *
 * Note: like Office, this is a plain entity with no foreign keys.
 * The N-N relationship with Doctor is implemented via the
 * DoctorSpecialization junction entity, not here.
 */
@Entity
@Table(name = "specialization")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialization_id")
    private Long specializationId;

    @Column(name = "name", nullable = false)
    private String name;

    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    public Specialization() {
        // Required by JPA.
    }

    public Specialization(String name) {
        this.name = name;
    }

    // ------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}