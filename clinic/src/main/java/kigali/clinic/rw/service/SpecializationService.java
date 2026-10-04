package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepo;

    public String saveSpecialization(Specialization specialization) {
        specializationRepo.save(specialization);
        return "Specialization saved successfully";
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepo.findAll();
    }

    public Specialization getSpecializationById(UUID id) {
        Optional<Specialization> s = specializationRepo.findById(id);
        return s.orElse(null);
    }

    public String updateSpecialization(UUID id, Specialization updated) {
        Optional<Specialization> existing = specializationRepo.findById(id);
        if (existing.isEmpty()) {
            return "Specialization with id " + id + " not found";
        }
        Specialization s = existing.get();
        s.setName(updated.getName());
        specializationRepo.save(s);
        return "Specialization updated successfully";
    }

    public String deleteSpecialization(UUID id) {
        if (!specializationRepo.existsById(id)) {
            return "Specialization with id " + id + " not found";
        }
        specializationRepo.deleteById(id);
        return "Specialization deleted successfully";
    }
}

