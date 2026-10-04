package kigali.clinic.rw.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String saveAppointment(Appointment appointment) {
        boolean alreadyBooked = appointmentRepo.existsByDoctorIdAndAppointmentDateAndStatusNot(
                appointment.getDoctor().getId(),
                appointment.getAppointmentDate(),
                AppointmentStatus.CANCELLED);
        if (alreadyBooked) return "Doctor is already booked on that date";
        appointmentRepo.save(appointment);
        return "Appointment saved successfully";
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Appointment getAppointmentById(UUID id) {
        return appointmentRepo.findById(id).orElse(null);
    }

    public String updateAppointment(UUID id, Appointment updated) {
        Optional<Appointment> existing = appointmentRepo.findById(id);
        if (existing.isEmpty()) return "Appointment with id " + id + " not found";
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
        if (!appointmentRepo.existsById(id)) return "Appointment with id " + id + " not found";
        appointmentRepo.deleteById(id);
        return "Appointment deleted successfully";
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepo.findByStatusOrderByAppointmentDateAsc(status);
    }

    public List<Appointment> getAppointmentsBetween(Date start, Date end) {
        return appointmentRepo.findByAppointmentDateBetweenOrderByAppointmentDateAsc(start, end);
    }

    public List<Object[]> countByStatus() {
        return appointmentRepo.countAppointmentsByStatus();
    }

    public List<Object[]> getBusiestOffice() {
        return appointmentRepo.findBusiestOffice();
    }

    @Transactional
    public int cancelAppointmentsOfDay(UUID doctorId, Date date) {
        return appointmentRepo.cancelAppointmentsOfDay(doctorId, date);
    }

    public Page<Appointment> getPage(int page, int size, String field, Sort.Direction dir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, field));
        return appointmentRepo.findAll(pageable);
    }

    @Transactional
    public int cleanCancelledBefore(Date date) {
        return appointmentRepo.deleteCancelledBefore(date);
    }
}
