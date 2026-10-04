package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    public String saveDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
        return "Doctor saved successfully";
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctorById(UUID id) {
        return doctorRepo.findById(id).orElse(null);
    }

    public String updateDoctor(UUID id, Doctor updated) {
        Optional<Doctor> existing = doctorRepo.findById(id);
        if (existing.isEmpty()) return "Doctor with id " + id + " not found";
        Doctor d = existing.get();
        d.setFirstName(updated.getFirstName());
        d.setLastName(updated.getLastName());
        d.setDateOfBirth(updated.getDateOfBirth());
        doctorRepo.save(d);
        return "Doctor updated successfully";
    }

    public String deleteDoctor(UUID id) {
        if (!doctorRepo.existsById(id)) return "Doctor with id " + id + " not found";
        doctorRepo.deleteById(id);
        return "Doctor deleted successfully";
    }

    public List<Doctor> getDoctorsBySpecialization(String name) {
        return doctorRepo.findBySpecializationNameIgnoreCase(name);
    }

    public List<Doctor> getDoctorsWithoutOffice() {
        return doctorRepo.findDoctorsWithoutOffice();
    }

    public boolean existsById(UUID id) {
        return doctorRepo.existsById(id);
    }
}
