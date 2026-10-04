package com.example.clinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor_specialization")
public class DoctorSpecialization {

    @EmbeddedId
    private DoctorSpecializationId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("doctorId")
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne
    @MapsId("specializationId")
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    public DoctorSpecialization() {
    }

    public DoctorSpecialization(Doctor doctor, Specialization specialization) {
        this.doctor = doctor;
        this.specialization = specialization;
        this.id = new DoctorSpecializationId(doctor.getDoctorId(), specialization.getSpecializationId());
    }

    public DoctorSpecializationId getId() {
        return id;
    }

    public void setId(DoctorSpecializationId id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
