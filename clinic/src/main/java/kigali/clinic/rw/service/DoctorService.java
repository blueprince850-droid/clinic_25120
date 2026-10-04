package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
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
        Optional<Doctor> doctor = doctorRepo.findById(id);
        return doctor.orElse(null);
    }

    public String updateDoctor(UUID id, Doctor updated) {
        Optional<Doctor> existing = doctorRepo.findById(id);
        if (existing.isEmpty()) {
            return "Doctor with id " + id + " not found";
        }
        Doctor d = existing.get();
        d.setFirstName(updated.getFirstName());
        d.setLastName(updated.getLastName());
        d.setDateOfBirth(updated.getDateOfBirth());
        doctorRepo.save(d);
        return "Doctor updated successfully";
    }

    public String deleteDoctor(UUID id) {
        if (!doctorRepo.existsById(id)) {
            return "Doctor with id " + id + " not found";
        }
        doctorRepo.deleteById(id);
        return "Doctor deleted successfully";
    }

    public List<Appointment> getPendingAppointments(UUID doctorId) {
        return doctorRepo.findByDoctorIdAndStatusOrderByDateAsc(doctorId, AppointmentStatus.SCHEDULED);
    }

    public List<Appointment> getExpiredPendingAppointments() {
        return doctorRepo.findExpiredPendingAppointments();
    }

    public List<Object[]> countAppointmentsPerDoctor() {
        return doctorRepo.countAppointmentsPerDoctor();
    }

    public List<Appointment> getAppointmentsBySpecialization(String specName) {
        return doctorRepo.findAppointmentsByDoctorSpecialization(specName);
    }

    public Page<Appointment> getDoctorAppointmentsPaginated(UUID doctorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return doctorRepo.findAppointmentsByDoctorId(doctorId, pageable);
    }
}
