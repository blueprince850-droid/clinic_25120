package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorService doctorService;

    public String savePatient(Patient patient) {
        patientRepo.save(patient);
        return "Patient saved successfully";
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient getPatientById(UUID id) {
        return patientRepo.findById(id).orElse(null);
    }

    public String updatePatient(UUID id, Patient updated) {
        Optional<Patient> existing = patientRepo.findById(id);
        if (existing.isEmpty()) return "Patient with id " + id + " not found";
        Patient p = existing.get();
        p.setFirstName(updated.getFirstName());
        p.setLastName(updated.getLastName());
        p.setDateOfBirth(updated.getDateOfBirth());
        patientRepo.save(p);
        return "Patient updated successfully";
    }

    public String deletePatient(UUID id) {
        if (!patientRepo.existsById(id)) return "Patient with id " + id + " not found";
        patientRepo.deleteById(id);
        return "Patient deleted successfully";
    }

    public List<Patient> getPatientsByLastName(String lastName) {
        return patientRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Patient> getPatientsOfDoctor(UUID doctorId) {
        return patientRepo.findPatientsOfDoctor(doctorId);
    }

    public List<Patient> getFrequentPatients(long min) {
        return patientRepo.findFrequentPatients(min);
    }

    public boolean doctorExists(UUID doctorId) {
        return doctorService.existsById(doctorId);
    }
}
