package kigali.clinic.rw.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);

    List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(Date start, Date end);

    boolean existsByDoctorIdAndAppointmentDateAndStatusNot(UUID doctorId, Date date, AppointmentStatus status);

    @Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
    List<Object[]> countAppointmentsByStatus();

    @Query("SELECT a.doctor.office.name, a.doctor.office.officeNumber, COUNT(a) " +
           "FROM Appointment a WHERE a.doctor.office IS NOT NULL " +
           "GROUP BY a.doctor.office.name, a.doctor.office.officeNumber " +
           "ORDER BY COUNT(a) DESC")
    List<Object[]> findBusiestOffice();

    @Modifying
    @Query("UPDATE Appointment a SET a.status = kigali.clinic.rw.domain.AppointmentStatus.CANCELLED " +
           "WHERE a.doctor.id = :doctorId AND a.appointmentDate = :date " +
           "AND a.status <> kigali.clinic.rw.domain.AppointmentStatus.COMPLETED")
    int cancelAppointmentsOfDay(@Param("doctorId") UUID doctorId, @Param("date") Date date);

    Page<Appointment> findAll(Pageable pageable);

    @Modifying
    @Query("DELETE FROM Appointment a WHERE a.status = kigali.clinic.rw.domain.AppointmentStatus.CANCELLED " +
           "AND a.appointmentDate < :date")
    int deleteCancelledBefore(@Param("date") Date date);
}
