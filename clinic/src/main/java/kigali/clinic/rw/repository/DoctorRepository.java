package kigali.clinic.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.status = :status ORDER BY a.appointmentDate ASC")
    List<Appointment> findByDoctorIdAndStatusOrderByDateAsc(
            @Param("doctorId") UUID doctorId,
            @Param("status") AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.status = 'SCHEDULED' AND a.appointmentDate < CURRENT_DATE")
    List<Appointment> findExpiredPendingAppointments();

    @Query("SELECT a.doctor.id, COUNT(a) FROM Appointment a GROUP BY a.doctor.id ORDER BY COUNT(a) DESC")
    List<Object[]> countAppointmentsPerDoctor();

    @Query("SELECT a FROM Appointment a JOIN a.doctor d JOIN d.specializations s WHERE s.name = :specName")
    List<Appointment> findAppointmentsByDoctorSpecialization(@Param("specName") String specName);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId ORDER BY a.appointmentDate ASC")
    Page<Appointment> findAppointmentsByDoctorId(@Param("doctorId") UUID doctorId, Pageable pageable);
}
