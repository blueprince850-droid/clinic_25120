package com.example.clinic.dto;

import jakarta.validation.constraints.NotBlank;

public class PatientRequest {

    @NotBlank(message = "fullName must not be blank")
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
