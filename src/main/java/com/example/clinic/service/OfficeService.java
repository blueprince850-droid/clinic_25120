package com.example.clinic.service;

import com.example.clinic.dto.OfficeRequest;
import com.example.clinic.entity.Office;
import com.example.clinic.exception.ResourceNotFoundException;
import com.example.clinic.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public List<Office> getAll() {
        return officeRepository.findAll();
    }

    public Office getById(Long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Office not found with id: " + id));
    }

    public Office create(OfficeRequest request) {
        if (request == null || request.getRoomNumber() == null || request.getRoomNumber().isBlank()) {
            throw new IllegalArgumentException("Office room number is required.");
        }

        Office office = new Office();
        office.setRoomNumber(request.getRoomNumber());
        return officeRepository.save(office);
    }

    public Office update(Long id, OfficeRequest request) {
        Office office = getById(id);

        if (request == null || request.getRoomNumber() == null || request.getRoomNumber().isBlank()) {
            throw new IllegalArgumentException("Office room number is required.");
        }

        office.setRoomNumber(request.getRoomNumber());
        return officeRepository.save(office);
    }

    public void delete(Long id) {
        Office office = getById(id);
        officeRepository.delete(office);
    }
}
