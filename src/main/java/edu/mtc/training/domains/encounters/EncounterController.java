package edu.mtc.training.domains.encounters;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * holds crud methods for encounter entity
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/patients/{patientId}/encounters")
public class EncounterController {

  @Autowired
  private final EncounterServiceImpl encounterService;

  public EncounterController(EncounterServiceImpl encounterService) {
    this.encounterService = encounterService;
  }

  /**
   * returns all encounters
   *
   * @return List of encounters associated with patient with matching id
   * @throws Exception
   */
  @CrossOrigin(origins = "*")
  @GetMapping()
  public ResponseEntity<List<Encounter>> getEncountersByPatientId(@PathVariable Long patientId) throws Exception {
    return new ResponseEntity<>(encounterService.queryEncounterByPatientId(patientId), HttpStatus.OK);
  }

  /**
   * saves encounter to repository
   *
   * @param encounter encounter to be saved
   * @return encounter on success
   * @throws Exception
   */
  @CrossOrigin(origins = "*")
  @PostMapping()
  public ResponseEntity<Encounter> addEncounter(@Valid @RequestBody Encounter encounter)
      throws Exception {
    return new ResponseEntity<>(encounterService.addEncounter(encounter), HttpStatus.CREATED);
  }

  /**
   * returns encounter with matching id
   *
   * @param encounterId id of encounter to retrieve
   * @return encounter upon success
   * @throws Exception
   */
  @CrossOrigin(origins = "*")
  @GetMapping("/{encounterId}")
  public ResponseEntity<Encounter> getEncounterById(@PathVariable Long encounterId)
      throws Exception {
    return new ResponseEntity<>(encounterService.getEncounterById(encounterId), HttpStatus.OK);
  }

  /**
   * updates an encounter
   *
   * @param encounterId id of encounter to update
   * @param encounter   encounter with new values
   * @return updated encounter if successful
   * @throws Exception
   */
  @CrossOrigin(origins = "*")
  @PutMapping("/{encounterId}")
  public ResponseEntity<Encounter> updateEncounter(@PathVariable Long encounterId,
      @RequestBody Encounter encounter)
      throws Exception {
    return new ResponseEntity<>(encounterService.updateEncounterById(encounterId, encounter),
        HttpStatus.OK);
  }
}
