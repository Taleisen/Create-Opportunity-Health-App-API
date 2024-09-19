package edu.mtc.training.data;

import edu.mtc.training.domains.encounters.Encounter;
import edu.mtc.training.domains.encounters.EncounterRepository;
import edu.mtc.training.domains.patients.Patient;
import edu.mtc.training.domains.patients.PatientRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired
  private PatientRepository patientRepository;

  @Autowired
  private EncounterRepository encounterRepository;

  @Override
  public void run(String... args) throws Exception {
    loadPatients();
    loadEncounters();
  }

  private void loadPatients() {
    Patient patient0 = patientRepository.save(new Patient(
        "John",
        "Smith",
        "123-45-6789",
        "test@email.com",
        "123 Sesame St.",
        "Salt Lake City",
        "UT",
        "84084",
        43,
        68,
        165,
        "Blue Cross",
        "Male"));

    Patient patient1 = patientRepository.save(new Patient(
        "Jane",
        "Doe",
        "987-65-4321",
        "test2@email.com",
        "321 Main St",
        "New York",
        "NY",
        "10001",
        35,
        57,
        98,
        "Holy Cross",
        "Female"));

    Patient patient2 = patientRepository.save(new Patient(
        "Pat",
        "O'Malley",
        "555-55-5555",
        "test3@email.com",
        "745 S 1350 E",
        "Wallace",
        "NJ",
        "65482",
        57,
        98,
        257,
        "None",
        "Other"));

  }

  private void loadEncounters() {
    Date date1 = new Date(2023, 02, 23);
    Date date2 = new Date(2023, 03, 12);
    Date date3 = new Date(2023, 04, 15);

    Encounter encounter1 = new Encounter();
    encounter1.setPatientId(1L);
    encounter1.setNotes("These are the notes for encounter 1");
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
    encounterRepository.save(encounter1);

    Encounter encounter2 = new Encounter();
    encounter2.setPatientId(1L);
    encounter2.setNotes("These are the notes for encounter 2");
    encounter2.setVisitCode("X9Z 7G6");
    encounter2.setProvider("Walter");
    encounter2.setBillingCode("111.111.111-11");
    encounter2.setIcd10("AZ45");
    encounter2.setTotalCost(154.36);
    encounter2.setCopay(0.00);
    encounter2.setChiefComplaint("Chief complaint");
    encounter2.setPulse(60);
    encounter2.setSystolic(180);
    encounter2.setDiastolic(98);
    encounter2.setDate(date2);
    encounterRepository.save(encounter2);

    Encounter encounter3 = new Encounter();
    encounter3.setPatientId(1L);
    encounter3.setNotes("Notes");
    encounter3.setVisitCode("A9G 6B4");
    encounter3.setProvider("River");
    encounter3.setBillingCode("456.123.987-15");
    encounter3.setIcd10("A12");
    encounter3.setTotalCost(87.96);
    encounter3.setCopay(0.00);
    encounter3.setChiefComplaint("Chief complaint");
    encounter3.setPulse(60);
    encounter3.setSystolic(180);
    encounter3.setDiastolic(98);
    encounter3.setDate(date3);
    encounterRepository.save(encounter3);

    Encounter encounter4 = new Encounter();
    encounter4.setPatientId(2L);
    encounter4.setNotes("Notes");
    encounter4.setVisitCode("M8R 6Q6");
    encounter4.setProvider("Rocket");
    encounter4.setBillingCode("999.999.999-99");
    encounter4.setIcd10("Z99");
    encounter4.setTotalCost(154.36);
    encounter4.setCopay(0.00);
    encounter4.setChiefComplaint("Chief complaint");
    encounter4.setPulse(60);
    encounter4.setSystolic(180);
    encounter4.setDiastolic(98);
    encounter4.setDate(date2);
    encounterRepository.save(encounter4);

    Encounter encounter5 = new Encounter();
    encounter5.setPatientId(2L);
    encounter5.setNotes("Notes");
    encounter5.setVisitCode("U8H 6W4");
    encounter5.setProvider("Overlord");
    encounter5.setBillingCode("456.123.987-15");
    encounter5.setIcd10("A12");
    encounter5.setTotalCost(87.96);
    encounter5.setCopay(0.00);
    encounter5.setChiefComplaint("Chief complaint");
    encounter5.setPulse(60);
    encounter5.setSystolic(180);
    encounter5.setDiastolic(98);
    encounter5.setDate(date3);
    encounterRepository.save(encounter5);

  }
}

