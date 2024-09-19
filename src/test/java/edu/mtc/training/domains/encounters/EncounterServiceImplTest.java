package edu.mtc.training.domains.encounters;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.mtc.training.exceptions.BadDataResponse;
import edu.mtc.training.exceptions.ResourceNotFound;
import edu.mtc.training.exceptions.ServiceUnavailable;
import jakarta.validation.UnexpectedTypeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.server.ResponseStatusException;

class EncounterServiceImplTest {

  @Mock
  EncounterRepository mockEncounterRepo;

  @InjectMocks
  EncounterServiceImpl encounterService;

  List<Encounter> encounterList = new ArrayList<>();
  Date date1 = new Date(2023, 02, 23);
  Encounter encounter1 = new Encounter();
  Encounter encounter2 = new Encounter(
      1L,
      "notes",
      "visitCode",
      "provider",
      "billingCode",
      "icd10",
      0.0,
      0.0,
      "complaint",
      0,
      0,
      0,
      null);
  Encounter encounter3 = new Encounter(
      1L,
      "",
      "",
      "",
      "",
      "",
      0.0,
      0.0,
      "",
      0,
      0,
      0,
      new Date());
  Encounter encounter4 = new Encounter(
      1L,
      "notes",
      "visitCode",
      "provider",
      "billingCode",
      "icd10",
      0.0,
      0.0,
      "complaint",
      0,
      0,
      0,
      new Date("December 23 1978"));


  Encounter emptyEncounter = new Encounter(
      null,
      null,
      null,
      null,
      null,
      null,
      0.0,
      0.0,
      null,
      0,
      0,
      0,
      null);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    encounter1.setId(1L);
    encounter1.setPatientId(1L);
    encounter1.setNotes("Notes");
    encounter1.setVisitCode("A1B 2C3");
    encounter1.setProvider("Provider");
    encounter1.setBillingCode("123.456.789-10");
    encounter1.setIcd10("A12");
    encounter1.setTotalCost(154.36);
    encounter1.setCopay(50.00);
    encounter1.setChiefComplaint("Chief complaint");
    encounter1.setPulse(60);
    encounter1.setSystolic(180);
    encounter1.setDiastolic(98);
    encounter1.setDate(date1);
    encounterList.add(encounter1);

    encounter2.setId(2L);
    encounter3.setId(3L);

    when(mockEncounterRepo.findAll()).thenReturn(encounterList);
    when(mockEncounterRepo.findAll(any(Example.class))).thenReturn(encounterList);
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.of(encounter1));
    when(mockEncounterRepo.existsById(any(Long.class))).thenReturn(true);
    when(mockEncounterRepo.save(any(Encounter.class))).thenReturn(encounter1);
  }

  @Test
  public void testQueryEncountersNullExample() throws Exception {
    List<Encounter> actualResult = encounterService.queryEncounters(emptyEncounter);
    Assertions.assertEquals(encounterList, actualResult);
  }

  @Test
  public void testQueryEncountersNonNullExample() {
    List<Encounter> actualResult = mockEncounterRepo.findAll(Example.of(encounter1));
    assertEquals(encounterList, actualResult);
  }

  @Test
  public void testQueryEncountersDBError() {
    when(mockEncounterRepo.findAll(Example.of(encounter1))).thenThrow(ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class,
        () -> encounterService.queryEncounters(encounter1));
  }

  @Test
  public void testQueryEncountersServiceUnavailable() {
    when(mockEncounterRepo.findAll(Example.of(encounter1))).thenThrow(ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class,
        () -> encounterService.queryEncounters(encounter1));
  }

  @Test
  public void testGetEncountersByPatientIdReturnsEncounters() throws Exception {
    when(mockEncounterRepo.findEncountersByPatientId(any(Long.class))).thenReturn(encounterList);
    List<Encounter> actualResult = encounterService.queryEncounterByPatientId(1L);
    Assertions.assertEquals(encounterList, actualResult);
  }

  @Test
  public void testGetEncountersByPatientIdIdNotFound() {
    when(mockEncounterRepo.findEncountersByPatientId(any(Long.class))).thenReturn(
        new ArrayList<>());
    assertThrows(ResourceNotFound.class, () -> encounterService.queryEncounterByPatientId(1L));
  }

  @Test
  public void testGetEncountersByPatientIdDBError() {
    when(mockEncounterRepo.findEncountersByPatientId(any(Long.class))).thenThrow(
        ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class, () -> encounterService.queryEncounterByPatientId(1L));
  }

  @Test
  public void testGetEncountersByPatientIdServiceUnavailable() {
    when(mockEncounterRepo.findEncountersByPatientId(any(Long.class))).thenThrow(
        ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class, () -> encounterService.queryEncounterByPatientId(1L));
  }

  @Test
  public void testGetEncounterByIdReturnsEncounter() {
    Optional<Encounter> actualResult = mockEncounterRepo.findById(1L);
    Assertions.assertEquals(Optional.of(encounter1), actualResult);
  }

  @Test
  public void testGetEncounterByIdIdNotFound() {
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThrows(ResourceNotFound.class, () -> encounterService.getEncounterById(1L));
  }

  @Test
  public void testGetEncounterByIdDBError() {
    when(mockEncounterRepo.findById(any(Long.class))).thenThrow(
        ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class, () -> encounterService.getEncounterById(1L));
  }

  @Test
  public void testGetEncounterByIdServiceUnavailable() {
    when(mockEncounterRepo.findById(any(Long.class))).thenThrow(ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class, () -> encounterService.getEncounterById(1L));
  }

  @Test
  public void testAddEncounterReturnsEncounter() throws Exception {
    Encounter actualResult = encounterService.addEncounter(encounter1);
    Assertions.assertEquals(encounter1, actualResult);
  }

  @Test
  public void testAddEncounterBadData() {
    when(mockEncounterRepo.save(any(Encounter.class))).thenThrow(BadDataResponse.class);
    assertThrows(BadDataResponse.class, () -> encounterService.addEncounter(encounter2));
  }

  @Test
  public void testAddEncounterDBError() {
    when(mockEncounterRepo.save(any(Encounter.class))).thenThrow(
        CannotCreateTransactionException.class);
    assertThrows(ServiceUnavailable.class, () -> encounterService.addEncounter(encounter1));
  }

  @Test
  public void testAddEncounterUnexpectedError() {
    when(mockEncounterRepo.save(any(Encounter.class))).thenThrow(UnexpectedTypeException.class);
    assertThrows(ServiceUnavailable.class, () -> encounterService.addEncounter(encounter1));
  }

  @Test
  public void testUpdateEncounterByIdReturnsEncounter() throws Exception {
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.of(encounter1));
    Assertions.assertEquals(encounter1, encounterService.updateEncounterById(1L, encounter1));
  }

  @Test
  public void testUpdateEncounterByIdIdNotFound() {
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThrows(ResourceNotFound.class,
        () -> encounterService.updateEncounterById(1L, encounter1));
  }

  @Test
  public void testUpdateEncounterBadData() {
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.of(encounter1));
    assertThrows(BadDataResponse.class, () -> encounterService.addEncounter(encounter2));
  }

  @Test
  public void testUpdateEncounterBadData2() {
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.of(encounter1));
    assertThrows(BadDataResponse.class, () -> encounterService.addEncounter(encounter3));
  }

  @Test
  public void testUpdateEncounterBadData3() {
    when(mockEncounterRepo.findById(any(Long.class))).thenReturn(Optional.of(encounter1));
    assertThrows(BadDataResponse.class, () -> encounterService.addEncounter(encounter4));
  }

  @Test
  public void testUpdateEncounterByIdIdDoesNotMatch() {
    encounter1.setId(3L);
    assertThrows(BadDataResponse.class,
        () -> encounterService.updateEncounterById(1L, encounter1));
  }

  @Test
  public void testUpdateEncounterByIdDBError() {
    when(mockEncounterRepo.findById(any(Long.class))).thenThrow(
        ServiceUnavailable.class);
    encounter1.setProvider("Natsu");
    assertThrows(ServiceUnavailable.class,
        () -> encounterService.updateEncounterById(1L, encounter1));
  }

  @Test
  public void testUpdateEncounterByIdServiceUnavailable() {
    when(mockEncounterRepo.findById(any(Long.class))).thenThrow(ServiceUnavailable.class);
    encounter1.setProvider("Natsu");
    assertThrows(ServiceUnavailable.class,
        () -> encounterService.updateEncounterById(1L, encounter1));
  }
}