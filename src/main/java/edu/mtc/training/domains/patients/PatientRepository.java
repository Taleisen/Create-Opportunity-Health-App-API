package edu.mtc.training.domains.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Patient repository to store patients
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

  Boolean existsByEmail(String email);

}
