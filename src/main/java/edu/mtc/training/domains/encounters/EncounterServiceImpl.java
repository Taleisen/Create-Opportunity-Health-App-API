package edu.mtc.training.domains.encounters;

import edu.mtc.training.constants.StringConstants;
import edu.mtc.training.exceptions.BadDataResponse;
import edu.mtc.training.exceptions.ResourceNotFound;
import edu.mtc.training.exceptions.ServiceUnavailable;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * service class which implements EncounterService interface
 */
@Service
public class EncounterServiceImpl implements EncounterService {

  private final EncounterRepository encounterRepository;

  private String errorMessage = "";

  @Autowired
  public EncounterServiceImpl(EncounterRepository encounterRepository) {
    this.encounterRepository = encounterRepository;
  }

  /**
   * returns all encounters that match given encounter or all encounters if encounter is null
   *
   * @param encounter optional encounter for comparison
   * @return list of matching encounters or all encounters
   * @throws Exception
   */
  @Override
  public List<Encounter> queryEncounters(Encounter encounter) throws Exception {
    try {

      if (encounter.isEmpty()) {
        return encounterRepository.findAll();
      } else {
        Example<Encounter> encounterExample = Example.of(encounter);
        return encounterRepository.findAll(encounterExample);
      }

    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * returns all encounters associated with matching patient
   *
   * @param patientId id of patient
   * @return list of encounters associated with matching patient
   * @throws Exception
   */
  @Override
  public List<Encounter> queryEncounterByPatientId(Long patientId) throws Exception {
    try {
      List<Encounter> encounters = encounterRepository.findEncountersByPatientId(patientId);
      if (!encounters.isEmpty()) {
        return encounters;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound(StringConstants.NOT_FOUND);
  }

  /**
   * returns encounter with matching id
   *
   * @param id id of encounter to retrieve
   * @return encounter with matching id
   * @throws Exception
   */
  @Override
  public Encounter getEncounterById(Long id) throws Exception {
    try {
      Encounter encounter = (Encounter) encounterRepository.findById(id).orElse(null);
      if (encounter != null) {
        return encounter;
      }
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }

    throw new ResourceNotFound(StringConstants.NOT_FOUND);
  }

  /**
   * adds encounter to repository
   *
   * @param encounter encounter to be added
   * @return encounter on success
   * @throws Exception
   */
  @Override
  public Encounter addEncounter(@Valid Encounter encounter) throws Exception {

    if (!validateEncounter(encounter)) {
      throw new BadDataResponse(errorMessage);
    }

    try {
      return encounterRepository.save(encounter);
    } catch (Exception e) {
      throw new ServiceUnavailable(e);
    }
  }

  /**
   * updates an encounter with matching id
   *
   * @param id        id of encounter to update
   * @param encounter encounter with updated information
   * @return updated encounter on success
   * @throws Exception
   */
  @Override
  public Encounter updateEncounterById(Long id, @Valid Encounter encounter) throws Exception {

    Encounter existingEncounter;

    // check if id in path matches id in request body
    if (!encounter.getId().equals(id)) {
      throw new BadDataResponse(StringConstants.BAD_REQUEST_ID);
    }

    if (!validateEncounter(encounter)) {
      throw new BadDataResponse(errorMessage);
    }

    try {

      // get the existing encounter from the database
      existingEncounter = encounterRepository.findById(id).orElse(null);
    } catch (ServiceUnavailable e) {
      throw new ServiceUnavailable(e);
    }

    // if the encounter exists, call the addEncounter method to save it to the database
    if (existingEncounter != null) {
      return this.addEncounter(encounter);
    } else {
      throw new ResourceNotFound(StringConstants.NOT_FOUND);
    }
  }

  private Boolean validateEncounter(Encounter encounter) {
    errorMessage = "";

    if (encounter.getVisitCode().isEmpty()) {
      errorMessage += ("Visit code" + StringConstants.REQUIRED_FIELD);
    } else if (!encounter.getVisitCode().matches("^[a-zA-Z][0-9][a-zA-Z] [0-9][a-zA-Z][0-9]$")) {
      errorMessage += "Invalid visit code ";
    }

    if (encounter.getProvider().isEmpty()) {
      errorMessage += ("Provider" + StringConstants.REQUIRED_FIELD);
    }

    if (encounter.getBillingCode().isEmpty()) {
      errorMessage += ("Billing code" + StringConstants.REQUIRED_FIELD);
    } else if (!encounter.getBillingCode().matches("^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$")) {
      errorMessage += "Invalid billing code ";
    }

    if (encounter.getIcd10().isEmpty()) {
      errorMessage += ("Icd10" + StringConstants.REQUIRED_FIELD);
    } else if (!encounter.getIcd10().matches("^[a-zA-Z][0-9]{2}$")) {
      errorMessage += "Invalid icd10 ";
    }

    if (!(encounter.getTotalCost() > 0.0)) {
      errorMessage += ("Total cost" + StringConstants.REQUIRED_FIELD);
    }

    if (encounter.getChiefComplaint().isEmpty()) {
      errorMessage += ("Chief complaint" + StringConstants.REQUIRED_FIELD);
    }

    if (encounter.getDate() == null) {
      errorMessage += ("Date" + StringConstants.REQUIRED_FIELD);
    }

    return errorMessage.isEmpty();
  }
}
