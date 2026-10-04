package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    // A1: Derived ? find by last name (case-insensitive), sorted by first name A?Z
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
