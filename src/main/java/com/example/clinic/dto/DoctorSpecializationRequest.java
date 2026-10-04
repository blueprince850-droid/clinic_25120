package com.example.clinic.dto;

import jakarta.validation.constraints.NotNull;

public class DoctorSpecializationRequest {

    @NotNull(message = "doctorId must not be null")
    private Long doctorId;

    @NotNull(message = "specializationId must not be null")
    private Long specializationId;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }
}
