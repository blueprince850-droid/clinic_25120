package com.example.clinic.dto;

import jakarta.validation.constraints.NotBlank;

public class OfficeRequest {

    @NotBlank(message = "roomNumber must not be blank")
    private String roomNumber;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
