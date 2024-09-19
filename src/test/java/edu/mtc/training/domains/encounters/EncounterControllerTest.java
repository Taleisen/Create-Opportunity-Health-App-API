package edu.mtc.training.domains.encounters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class EncounterControllerTest {

  private static final String CONTEXT = "/patients/1/encounters";
  @Autowired
  private static MockMvc mockMvc;
  @Autowired
  EncounterRepository encounterRepository;
  ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private WebApplicationContext wac;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Order(1)
  void getEncountersByPatientId() throws Exception {
    MockHttpServletResponse mr = mockMvc.perform(get(CONTEXT)).andExpect(status().isOk())
        .andReturn().getResponse();

    assertEquals("application/json", mr.getContentType());
  }

  @Test
  @Order(2)
  void getEncounterById() throws Exception {
    String retType = mockMvc.perform(get(CONTEXT + "/1"))
        .andExpect(status().isOk()).andReturn()
        .getResponse().getContentType();

    assertEquals("application/json", retType);
  }

  @Test
  @Order(3)
  void addEncounter() throws Exception {
    Date date1 = new Date(2023, 02, 23);
    Encounter encounter1 = new Encounter();
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

    String encounterAsJson = mapper.writeValueAsString(encounter1);

    String retType = mockMvc.perform(
            post(CONTEXT).contentType(MediaType.APPLICATION_JSON).content(encounterAsJson))
        .andExpect(status().isCreated()).andReturn()
        .getResponse().getContentType();

    assertEquals("application/json", retType);
  }

  @Test
  @Order(4)
  void updateEncounter() throws Exception {
    Date date1 = new Date(2023, 02, 23);
    Encounter encounter1 = new Encounter();
    encounter1.setId(1L);
    encounter1.setPatientId(1L);
    encounter1.setNotes("New notes");
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

    String encounterAsJson = mapper.writeValueAsString(encounter1);

    String retType = mockMvc.perform(
            put(CONTEXT + "/1", encounterAsJson).contentType(MediaType.APPLICATION_JSON)
                .content(encounterAsJson)).andExpect(status().isOk())
        .andReturn().getResponse()
        .getContentType();

    assertEquals("application/json", retType);
  }
}