package edu.mtc.training.domains.patients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class PatientControllerTest {

  private static final String CONTEXT = "/patients";

  @Autowired
  private static MockMvc mockMvc;

  @Autowired
  PatientRepository patientRepository;

  ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private WebApplicationContext wac;

  @BeforeEach
  public void before() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @Order(1)
  void getAllPatientsReturnsOK() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(get(CONTEXT)).andExpect(status().isOk())
        .andReturn().getResponse();
    assertEquals("application/json", response.getContentType());
  }

  @Test
  @Order(2)
  void addPatientReturnIsCreated() throws Exception {
    Patient testPatient = new Patient(
        "Natsu",
        "Dragneel",
        "999-99-9999",
        "salamander@fairytail.org",
        "123 Fairy Tail Ln",
        "Magnolia",
        "MA",
        "28568",
        18,
        73,
        165,
        "Guildcare",
        "Male");

    String patientAsJson = mapper.writeValueAsString(testPatient);

    String returnType = mockMvc.perform(
            post(CONTEXT).contentType(MediaType.APPLICATION_JSON).content(patientAsJson))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentType();

    assertEquals("application/json", returnType);
  }

  @Test
  @Order(3)
  void getPatientByIdReturnsOk() throws Exception {

    String returnType = mockMvc.perform(get(CONTEXT + "/1"))
        .andExpect(status().isOk()).andReturn()
        .getResponse().getContentType();

    assertEquals("application/json", returnType);
  }

  @Test
  @Order(4)
  void updatePatient() throws Exception {
    Patient erza = new Patient(
        "Erza",
        "Scarlet",
        "222-22-2222",
        "erza@fairytail.org",
        "521 Fake St",
        "Magnolia",
        "GA",
        "25863",
        18,
        65,
        140,
        "Guildcare",
        "Female");
    erza.setId(3L);

    String patientAsJson = mapper.writeValueAsString(erza);

    mockMvc.perform(put(CONTEXT + "/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(patientAsJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @Order(99999999)
  void deletePatient() throws Exception {
    mockMvc.perform(delete(CONTEXT + "/3")).andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}