package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String saveAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
        return "Appointment saved successfully";
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Appointment getAppointmentById(UUID id) {
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        return appointment.orElse(null);
    }

    public String updateAppointment(UUID id, Appointment updated) {
        Optional<Appointment> existing = appointmentRepo.findById(id);
        if (existing.isEmpty()) {
            return "Appointment with id " + id + " not found";
        }
        Appointment a = existing.get();
        a.setAppointmentDate(updated.getAppointmentDate());
        a.setReason(updated.getReason());
        a.setStatus(updated.getStatus());
        a.setPatient(updated.getPatient());
        a.setDoctor(updated.getDoctor());
        appointmentRepo.save(a);
        return "Appointment updated successfully";
    }

    public String deleteAppointment(UUID id) {
        if (!appointmentRepo.existsById(id)) {
            return "Appointment with id " + id + " not found";
        }
        appointmentRepo.deleteById(id);
        return "Appointment deleted successfully";
    }
}