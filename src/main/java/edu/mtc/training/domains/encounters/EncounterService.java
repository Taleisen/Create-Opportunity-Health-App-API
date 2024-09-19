package edu.mtc.training.domains.encounters;

import java.util.List;

/**
 * Encounter service interface with crud methods for an encounter
 */
public interface EncounterService {

  List<Encounter> queryEncounters(Encounter encounter) throws Exception;

  List<Encounter> queryEncounterByPatientId(Long patientId) throws Exception;

  Encounter getEncounterById(Long id) throws Exception;

  Encounter addEncounter(Encounter encounter) throws Exception;

  Encounter updateEncounterById(Long id, Encounter encounter) throws Exception;

}
