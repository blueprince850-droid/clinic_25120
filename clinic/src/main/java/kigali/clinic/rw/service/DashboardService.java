package kigali.clinic.rw.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.repository.AppointmentRepository;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.repository.OfficeRepository;
import kigali.clinic.rw.repository.PatientRepository;
import kigali.clinic.rw.repository.PrescriptionRepository;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private OfficeRepository officeRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> m = new LinkedHashMap<>();
        m.put("patients", patientRepo.count());
        m.put("doctors", doctorRepo.count());
        m.put("freeOffices", (long) officeRepo.findFreeOffices().size());
        m.put("appointmentsToday", appointmentRepo.countByAppointmentDate(Date.valueOf(LocalDate.now())));
        m.put("scheduled", appointmentRepo.countByStatus(AppointmentStatus.SCHEDULED));
        m.put("prescriptions", prescriptionRepo.count());
        return m;
    }
}