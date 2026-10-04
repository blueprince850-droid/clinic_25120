package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;

@Service
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepo;

    public String saveOffice(Office office) {
        if (office.getId() == null) {
            Optional<Office> existing = officeRepo.findByOfficeNumber(office.getOfficeNumber());
            if (existing.isPresent()) {
                return "Office with this office number " + office.getOfficeNumber() + " already exists";
            }
        }
        officeRepo.save(office);
        return "Office saved successfully";
    }

    public List<Office> getAllOffices() {
        return officeRepo.findAll();
    }

    public Office getOfficeById(UUID id) {
        return officeRepo.findById(id).orElse(null);
    }

    public String updateOffice(UUID id, Office updated) {
        Optional<Office> existing = officeRepo.findById(id);
        if (existing.isEmpty()) {
            return "Office with id " + id + " not found";
        }
        Office o = existing.get();
        o.setName(updated.getName());
        o.setOfficeNumber(updated.getOfficeNumber());
        officeRepo.save(o);
        return "Office updated successfully";
    }

    public String deleteOffice(UUID id) {
        if (!officeRepo.existsById(id)) {
            return "Office with id " + id + " not found";
        }
        officeRepo.deleteById(id);
        return "Office deleted successfully";
    }
}
