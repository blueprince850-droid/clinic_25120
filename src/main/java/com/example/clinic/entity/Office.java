package com.example.clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a doctor's office room.
 * Maps to the OFFICE table from the ER diagram.
 *
 * Note: Office has no foreign keys. The relationship with Doctor
 * is owned by the Doctor entity (Doctor has office_id).
 */
@Entity
@Table(name = "office")
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "office_id")
    private Long officeId;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    public Office() {
        // Required by JPA.
    }

    public Office(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    // ------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}