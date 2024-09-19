package edu.mtc.training.domains.patients;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.mtc.training.domains.encounters.Encounter;
import edu.mtc.training.domains.encounters.EncounterRepository;
import edu.mtc.training.exceptions.BadDataResponse;
import edu.mtc.training.exceptions.ResourceNotFound;
import edu.mtc.training.exceptions.ServiceUnavailable;
import edu.mtc.training.exceptions.UniqueFieldViolation;
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

class PatientServiceImplTest {

  @Mock
  PatientRepository mockPatientRepo;

  @Mock
  EncounterRepository mockEncounterRepo;

  @InjectMocks
  PatientServiceImpl patientService;

  List<Patient> patientList = new ArrayList<>();
  Patient zaref = new Patient(
      "Zaref",
      "Dragneel",
      "111-11-1111",
      "spriggan@alvarez.gov",
      "1 Royal Way",
      "Alvarez",
      "AZ",
      "12345",
      425,
      68,
      150,
      "Curse of Ankhseram",
      "Male");
  List<Encounter> encounterList = new ArrayList<>();
  List<Encounter> emptyEncounterList = new ArrayList<>();
  Date date1 = new Date(2023, 02, 23);
  Encounter encounter = new Encounter(
      1L,
      "notes",
      "visit code",
      "provider",
      "billing code",
      "icd10",
      1.00,
      2.00,
      "complaint",
      60,
      180,
      90,
      date1);

  @BeforeEach
  @SuppressWarnings("unchecked")
    //we put this here to allow the generic Example
  void setUp() {
    MockitoAnnotations.openMocks(this);
    zaref.setId(1L);
    patientList.add(zaref);
    encounter.setId(1L);
    encounterList.add(encounter);

    when(mockPatientRepo.findAll()).thenReturn(patientList);
    when(mockPatientRepo.findAll(any(Example.class))).thenReturn(patientList);
    when(mockPatientRepo.findById(any(Long.class))).thenReturn(Optional.of(zaref));
    when(mockPatientRepo.existsByEmail(any(String.class))).thenReturn(false);
    when(mockPatientRepo.existsById(any(Long.class))).thenReturn(true);
    when(mockPatientRepo.save(any(Patient.class))).thenReturn(zaref);
  }

  @Test
  public void testQueryPatientsNullExample() {
    List<Patient> actualResult = patientService.queryPatients(new Patient());
    Assertions.assertEquals(patientList, actualResult);
  }

  @Test
  public void testQueryPatientsNonNullExample() {
    List<Patient> actualResult = mockPatientRepo.findAll(Example.of(zaref));
    assertEquals(patientList, actualResult);
  }

  @Test
  public void testQueryPatientsDBError() {
    when(mockPatientRepo.findAll(Example.of(zaref))).thenThrow(ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class,
        () -> patientService.queryPatients(zaref));
  }

  @Test
  public void testQueryPatientsServiceUnavailable() {
    when(mockPatientRepo.findAll(Example.of(zaref))).thenThrow(ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class,
        () -> patientService.queryPatients(zaref));
  }

  @Test
  public void testGetPatientByIdReturnsPatient() {
    Optional<Patient> actualResult = mockPatientRepo.findById(1L);
    Assertions.assertEquals(Optional.of(zaref), actualResult);
  }

  @Test
  public void testGetPatientByIdIdNotFound() {
    when(mockPatientRepo.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThrows(ResourceNotFound.class, () -> patientService.getPatientById(1L));
  }

  @Test
  public void testGetPatientByIdDBError() {
    when(mockPatientRepo.findById(any(Long.class))).thenThrow(
        ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class, () -> patientService.getPatientById(1L));
  }

  @Test
  public void testGetPatientByIdServiceUnavailable() {
    when(mockPatientRepo.findById(any(Long.class))).thenThrow(ServiceUnavailable.class);
    assertThrows(ServiceUnavailable.class, () -> patientService.getPatientById(1L));
  }

  @Test
  public void testAddPatientReturnsPatient() {
    Patient actualResult = patientService.addPatient(zaref);
    Assertions.assertEquals(zaref, actualResult);
  }

  @Test
  public void testAddPatientEmailConflict() {
    when(mockPatientRepo.existsByEmail(any(String.class))).thenReturn(true);
    assertThrows(UniqueFieldViolation.class,
        () -> patientService.addPatient(zaref));
  }

  @Test
  public void testAddPatientInvalidState() {
    zaref.setState("ZX");
    assertThrows(BadDataResponse.class, () -> patientService.addPatient(zaref));
  }

  @Test
  public void testAddPatientDBError() {
    when(mockPatientRepo.save(any(Patient.class))).thenThrow(
        CannotCreateTransactionException.class);
    assertThrows(ServiceUnavailable.class, () -> patientService.addPatient(zaref));
  }

  @Test
  public void testAddPatientUnexpectedError() {
    when(mockPatientRepo.save(any(Patient.class))).thenThrow(UnexpectedTypeException.class);
    assertThrows(ServiceUnavailable.class, () -> patientService.addPatient(zaref));
  }

  @Test
  public void testUpdatePatientByIdReturnsPatient() {
    when(mockPatientRepo.findById(any(Long.class))).thenReturn(Optional.of(zaref));
    Assertions.assertEquals("Zaref", zaref.getFirstName());
  }

  @Test
  public void testUpdatePatientByIdIdNotFound() {
    when(mockPatientRepo.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThrows(ResourceNotFound.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testUpdatePatientByIdIdDoesNotMatch() {
    zaref.setId(3L);
    assertThrows(BadDataResponse.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testUpdatePatientByIdInvalidState() {
    zaref.setState("ZX");
    assertThrows(BadDataResponse.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testUpdatePatientByIdInvalidPatient() {
    zaref.setFirstName("");
    zaref.setLastName("");
    zaref.setSsn("");
    zaref.setEmail("");
    zaref.setStreet("");
    zaref.setCity("");
    zaref.setState("");
    zaref.setPostal("");
    zaref.setAge(0);
    zaref.setHeight(0);
    zaref.setWeight(0);
    zaref.setInsurance("");
    zaref.setGender("");
    assertThrows(BadDataResponse.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testUpdatePatientByIdInvalidPatient2() {
    zaref.setFirstName("654");
    zaref.setLastName("654");
    zaref.setSsn("35");
    zaref.setEmail("null");
    zaref.setStreet("null");
    zaref.setCity("null");
    zaref.setState("null");
    zaref.setPostal("null");
    zaref.setAge(0);
    zaref.setHeight(0);
    zaref.setWeight(0);
    zaref.setInsurance(null);
    zaref.setGender("null");
    assertThrows(BadDataResponse.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testUpdatePatientByIdEmailConflict() {
    Patient updatedPatient = new Patient(
        "Erza",
        "Scarlet",
        "222-11-3333",
        "salamander@fairytail.org",
        "1 Royal Way",
        "Alvarez",
        "AZ",
        "12345",
        425,
        68,
        150,
        "Curse of Ankhseram",
        "Female");
    when(mockPatientRepo.existsByEmail(any(String.class))).thenReturn(true);
    updatedPatient.setId(1L);
    updatedPatient.setFirstName("Natsu");
    //email doesn't have to exist
    updatedPatient.setEmail("salamander@fairytail.org");
    assertThrows(UniqueFieldViolation.class,
        () -> patientService.updatePatient(1L, updatedPatient));
  }

  @Test
  public void testUpdatePatientByIdDBError() {
    when(mockPatientRepo.findById(any(Long.class))).thenThrow(
        ServiceUnavailable.class);
    zaref.setFirstName("Natsu");
    assertThrows(ServiceUnavailable.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testUpdatePatientByIdServiceUnavailable() {
    when(mockPatientRepo.findById(any(Long.class))).thenThrow(ServiceUnavailable.class);
    zaref.setFirstName("Natsu");
    assertThrows(ServiceUnavailable.class,
        () -> patientService.updatePatient(1L, zaref));
  }

  @Test
  public void testDeletePatientById204Returned() {
    when(mockEncounterRepo.findEncountersByPatientId(2L)).thenReturn(emptyEncounterList);
    patientService.deletePatient(2L);
    //check method was called
    verify(mockPatientRepo).deleteById(any());
  }

  @Test
  public void testDeletePatientById409Returned() {
    when(mockEncounterRepo.findEncountersByPatientId(1L)).thenReturn(encounterList);
    assertThrows(UniqueFieldViolation.class,
        () -> patientService.deletePatient(1L));
  }

  @Test
  public void testDeletePatientByIdIdNotFound() {
    when(mockPatientRepo.existsById(any(Long.class))).thenReturn(false);
    doThrow(ResponseStatusException.class).when(mockPatientRepo).deleteById(any(Long.class));
    assertThrows(ResourceNotFound.class, () -> patientService.deletePatient(1L));
  }

  @Test
  public void testDeletePatientByIdDBError() {
    doThrow(ServiceUnavailable.class).when(mockPatientRepo)
        .existsById(any(Long.class));
    assertThrows(ServiceUnavailable.class, () -> patientService.deletePatient(1L));
  }

  @Test
  public void testDeletePatientByIdServiceUnavailable() {
    doThrow(ServiceUnavailable.class).when(mockPatientRepo).existsById(any(Long.class));
    assertThrows(ServiceUnavailable.class, () -> patientService.deletePatient(1L));
  }
}