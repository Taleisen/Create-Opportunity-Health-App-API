package edu.mtc.training.domains.patients;

import edu.mtc.training.constants.StringConstants;
import edu.mtc.training.domains.encounters.EncounterRepository;
import edu.mtc.training.exceptions.BadDataResponse;
import edu.mtc.training.exceptions.ResourceNotFound;
import edu.mtc.training.exceptions.ServiceUnavailable;
import edu.mtc.training.exceptions.UniqueFieldViolation;
import edu.mtc.training.helpers.ValidStates;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * service class that implements the PatientService interface
 */
@Service
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patientRepository;
  private final EncounterRepository encounterRepository;

  private String errorMessage = "";

  @Autowired
  public PatientServiceImpl(PatientRepository patientRepository,
      EncounterRepository encounterRepository) {
    this.patientRepository = patientRepository;
    this.encounterRepository = encounterRepository;
  }

  /**
   * gets patients from repository
   *
   * @param patient an optional sample for query comparison
   * @return list of patients
   * @throws ServiceUnavailable
   */
  @Override
  public List<Patient> queryPatients(Patient patient) throws ServiceUnavailable {

    try {
      if (patient.isEmpty()) {
        return patientRepository.findAll();
      } else {
        Example<Patient> patientExample = Example.of(patient);
        return patientRepository.findAll(patientExample);
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * adds patient to repository
   *
   * @param patient patient to be added
   * @return patient if successful
   */
  @Override
  public Patient addPatient(Patient patient) {
    boolean emailAlreadyExists;

    if (!validatePatient(patient)) {
      throw new BadDataResponse(errorMessage);
    }

    try {
      // check if email already exists
      emailAlreadyExists = patientRepository.existsByEmail(patient.getEmail());

      if (!emailAlreadyExists) {

        return patientRepository.save(patient);

      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if made it to this point, email is not unique
    throw new UniqueFieldViolation(StringConstants.EMAIL_CONFLICT);
  }

  /**
   * retrieves patient with matching id
   *
   * @param patientId id of patient to retrieve
   * @return patient on success
   */
  @Override
  public Patient getPatientById(Long patientId) {

    try {
      Patient patient = patientRepository.findById(patientId).orElse(null);
      if (patient != null) {
        return patient;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound(StringConstants.NOT_FOUND);
  }

  /**
   * updates patient in repository
   *
   * @param patientId id of patient to update
   * @param patient   patient with updated information
   * @return updated patient on success
   */
  @Override
  public Patient updatePatient(Long patientId, Patient patient) {
    Patient existingPatient;

    boolean emailIsSame;
    boolean newEmailIsUnique;
    String currentEmail;

    // get the new email from the patient passed in
    String newEmail = patient.getEmail();

    // check if id in path matches id in request body
    if (!patient.getId().equals(patientId)) {
      throw new BadDataResponse(StringConstants.BAD_REQUEST_ID);
    }

    if (!validatePatient(patient)) {
      throw new BadDataResponse(errorMessage);
    }

    try {

      // get the existing patient from the database
      existingPatient = patientRepository.findById(patientId).orElse(null);

      if (existingPatient != null) {

        // get the current email from the database
        currentEmail = existingPatient.getEmail();

        // see if new email already exists
        newEmailIsUnique = !patientRepository.existsByEmail(newEmail);

        // set local for email is same
        emailIsSame = currentEmail.equals(newEmail);

        // only continue if email has not changed, or new email is unique
        if (emailIsSame || newEmailIsUnique) {

          return patientRepository.save(patient);
        }
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    // if patient was not found...
    if (existingPatient == null) {
      throw new ResourceNotFound(StringConstants.NOT_FOUND);
    }
    // patient was found, so it must because of an email conflict
    else {
      throw new UniqueFieldViolation(StringConstants.EMAIL_CONFLICT);
    }
  }

  private Boolean validatePatient(Patient patient) {
    errorMessage = "";

    if (patient.getFirstName().isEmpty()) {
      errorMessage += ("First Name" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getFirstName().matches("^[a-zA-Z'-]+$")) {
      errorMessage += StringConstants.BAD_REQUEST_FIRST_NAME;
    }

    if (patient.getLastName().isEmpty()) {
      errorMessage += ("Last Name" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getLastName().matches("^[a-zA-Z'-]+$")) {
      errorMessage += StringConstants.BAD_REQUEST_LAST_NAME;
    }

    if (patient.getSsn().isEmpty()) {
      errorMessage += ("Social Security Number" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getSsn().matches("^[0-9]{3}-[0-9]{2}-[0-9]{4}$")) {
      errorMessage += StringConstants.BAD_REQUEST_SSN;
    }

    if (Objects.equals(patient.getStreet(), "")) {
      errorMessage += ("Street" + StringConstants.REQUIRED_FIELD);
    }

    if (Objects.equals(patient.getCity(), "")) {
      errorMessage += ("City" + StringConstants.REQUIRED_FIELD);
    }

    if (Objects.equals(patient.getState(), "")) {
      errorMessage += ("State" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getState().matches("^[a-zA-Z]{2}$")) {
      errorMessage += StringConstants.BAD_REQUEST_STATECODE;
    }

    // check patient state is valid
    else if (!ValidStates.validStatesList.contains(patient.getState())) {
      errorMessage += StringConstants.BAD_REQUEST_STATE;
    }

    if (Objects.equals(patient.getPostal(), "")) {
      errorMessage += ("Zip Code" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getPostal().matches("^[0-9]{5}$") && !patient.getPostal()
        .matches("^[0-9]{5}-[0-9]{4}$")) {
      errorMessage += StringConstants.BAD_REQUEST_ZIPCODE;
    }

    if (Objects.equals(patient.getEmail(), "")) {
      errorMessage += ("Email" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getEmail().matches("^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$")) {
      errorMessage += StringConstants.BAD_REQUEST_EMAIL;
    }

    if (!(patient.getAge() > 0)) {
      errorMessage += ("Age" + StringConstants.REQUIRED_FIELD);
    }

    if (!(patient.getHeight() > 0)) {
      errorMessage += ("Height" + StringConstants.REQUIRED_FIELD);
    }

    if (!(patient.getWeight() > 0)) {
      errorMessage += ("Weight" + StringConstants.REQUIRED_FIELD);
    }

    if (Objects.equals(patient.getInsurance(), "")) {
      errorMessage += ("Insurance" + StringConstants.REQUIRED_FIELD);
    }

    if (Objects.equals(patient.getGender(), "")) {
      errorMessage += ("Gender" + StringConstants.REQUIRED_FIELD);
    } else if (!patient.getGender().equals("Male") && !patient.getGender().equals("Female")
        && !patient.getGender().equals("Other")) {
      errorMessage += StringConstants.BAD_REQUEST_GENDER;
    }

    return errorMessage.isEmpty();
  }

  /**
   * deletes patient with matching id
   *
   * @param patientId id of patient to delete
   */
  @Override
  public void deletePatient(Long patientId) {
    try {

      // if a patient exists for that id, and the patient has no encounters, delete it. else throw 409
      if (patientRepository.existsById(patientId)) {
        if (encounterRepository.findEncountersByPatientId(patientId).isEmpty()) {
          patientRepository.deleteById(patientId);
          return;
        } else {
          throw new UniqueFieldViolation("Unable to delete patient ");
        }
      }
    } catch (ServiceUnavailable e) {
      throw new ServiceUnavailable(e);
    }

    // if we made it to this point, return a 404
    throw new ResourceNotFound(StringConstants.NOT_FOUND);
  }
}
