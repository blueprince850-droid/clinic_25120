package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private SpecializationRepository specializationRepo;

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
    public String addSpecialization(UUID doctorId, UUID specializationId) {
        Optional<Doctor> docOpt = doctorRepo.findById(doctorId);
        if (docOpt.isEmpty()) {
            return "The doctor with that id does not exist";
        }
        Optional<Specialization> specOpt = specializationRepo.findById(specializationId);
        if (specOpt.isEmpty()) {
            return "That specialization does not exist";
        }
        Doctor doctor = docOpt.get();
        Specialization spec = specOpt.get();
        if (doctor.getSpecializations().contains(spec)) {
            return "The doctor already has that specialization";
        }
        doctor.getSpecializations().add(spec);
        doctorRepo.save(doctor);
        return "Specialization added to the doctor";
    }
}
