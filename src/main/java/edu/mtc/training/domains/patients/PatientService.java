package edu.mtc.training.domains.patients;

import java.util.List;

/**
 * Patient service interface with crud methods for a patient
 */
public interface PatientService {

  List<Patient> queryPatients(Patient patient);

  Patient addPatient(Patient patient);

  Patient getPatientById(Long patientId);

  Patient updatePatient(Long patientId, Patient patient);

  void deletePatient(Long patientId);

}
