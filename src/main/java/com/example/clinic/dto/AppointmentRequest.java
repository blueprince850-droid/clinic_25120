package com.example.clinic.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AppointmentRequest {

    @NotNull(message = "patientId must not be null")
    private Long patientId;

    @NotNull(message = "doctorId must not be null")
    private Long doctorId;

    @NotNull(message = "date must not be null")
    private LocalDate date;

    private String reason;
    private String status;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
