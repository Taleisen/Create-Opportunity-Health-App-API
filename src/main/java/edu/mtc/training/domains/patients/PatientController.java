package edu.mtc.training.domains.patients;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * holds crud methods for patient entity
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/patients")
public class PatientController {

  @Autowired
  private final PatientServiceImpl patientService;

  public PatientController(PatientServiceImpl patientService) {
    this.patientService = patientService;
  }

  /**
   * provides all patients
   *
   * @return List of all patient objects
   */
  @CrossOrigin(origins = "*")
  @GetMapping()
  public ResponseEntity<List<Patient>> getAllPatients() {
    return new ResponseEntity<>(patientService.queryPatients(new Patient()), HttpStatus.OK);
  }

  /**
   * adds a patient to the repository
   *
   * @param patient patient to be added to the repository
   * @return the patient if successfully added
   */
  @CrossOrigin(origins = "*")
  @PostMapping()
  public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
    return new ResponseEntity<>(patientService.addPatient(patient), HttpStatus.CREATED);
  }

  /**
   * returns patient with matching id
   *
   * @param patientId Long id of the patient to be retrieved
   * @return patient if successful
   */
  @CrossOrigin(origins = "*")
  @GetMapping("/{patientId}")
  public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
    return new ResponseEntity<>(patientService.getPatientById(patientId), HttpStatus.OK);
  }

  /**
   * updates a patient in the repository
   *
   * @param patientId Long id of patient to be updated
   * @param patient   Patient to be updated
   * @return updated patient if successful
   */
  @CrossOrigin(origins = "*")
  @PutMapping("/{patientId}")
  public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId,
      @RequestBody Patient patient) {
    return new ResponseEntity<>(patientService.updatePatient(patientId, patient), HttpStatus.OK);
  }

  /**
   * deletes a patient from the database
   *
   * @param patientId Long id of patient to be deleted
   * @return http status verifying success
   */
  @CrossOrigin(origins = "*")
  @DeleteMapping("/{patientId}")
  public ResponseEntity deletePatient(@PathVariable Long patientId) {
    patientService.deletePatient(patientId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
