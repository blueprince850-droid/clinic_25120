package kigali.clinic.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Prescription;
import kigali.clinic.rw.repository.AppointmentRepository;
import kigali.clinic.rw.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    public String savePrescription(UUID appointmentId, Prescription prescription) {
        Optional<Appointment> apptOpt = appointmentRepo.findById(appointmentId);
        if (apptOpt.isEmpty()) return "The appointment with that id does not exist";
        Appointment appt = apptOpt.get();
        if (appt.getStatus() != AppointmentStatus.COMPLETED) {
            return "Prescriptions are only for COMPLETED appointments";
        }
        prescription.setIssuedDate(LocalDate.now());
        prescription.setAppointment(appt);
        prescriptionRepo.save(prescription);
        return "Prescription saved successfully";
    }
    public Object getByAppointment(UUID appointmentId) {
        if (!appointmentRepo.existsById(appointmentId)) return "The appointment with that id does not exist";
        return prescriptionRepo.findByAppointmentId(appointmentId);
    }

    public Object getByPatientSs(String ss) {
        return prescriptionRepo.findByPatientSsNumber(ss);
    }
    public String updatePrescription(UUID id, Prescription updated) {
        Optional<Prescription> existing = prescriptionRepo.findById(id);
        if (existing.isEmpty()) return "The prescription with that id does not exist";
        Prescription p = existing.get();
        p.setMedicineName(updated.getMedicineName());
        p.setDosage(updated.getDosage());
        p.setDurationDays(updated.getDurationDays());
        prescriptionRepo.save(p);
        return "Prescription updated successfully";
    }

    public String deletePrescription(UUID id) {
        if (!prescriptionRepo.existsById(id)) return "The prescription with that id does not exist";
        prescriptionRepo.deleteById(id);
        return "Prescription deleted successfully";
    }

    public List<Object[]> getTopMedicines() {
        return prescriptionRepo.topMedicines();
    }
}