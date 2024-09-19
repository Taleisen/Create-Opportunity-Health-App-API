package edu.mtc.training.domains.encounters;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Encounter repository to store encounters
 */
@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

  List<Encounter> findEncountersByPatientId(Long id);
}
