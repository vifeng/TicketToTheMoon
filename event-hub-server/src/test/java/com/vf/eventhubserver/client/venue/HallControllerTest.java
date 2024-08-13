package com.vf.eventhubserver.client.venue;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.persona.EmployeeRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPersona;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorVenue;
import com.vf.eventhubserver.venue.Hall;
import com.vf.eventhubserver.venue.HallRepository;
import com.vf.eventhubserver.venue.Venue;
import com.vf.eventhubserver.venue.VenueRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
class HallControllerTest {

  @Autowired private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  String baseUrl = "http://localhost:8080/api/";
  private EntitiesFieldDescriptorVenue entitiesFieldDescriptor = new EntitiesFieldDescriptorVenue();
  private EntitiesFieldDescriptorPersona entitiesFieldDescriptorPersona =
      new EntitiesFieldDescriptorPersona();
  @Autowired EmployeeRepository employeeRepository;
  @Autowired VenueRepository venueRepository;
  @Autowired HallRepository hallRepository;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(
                document(
                    "{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())))
            .build();
  }

  @Test
  void createHallForVenueIdFalseCapacity() throws Exception {
    Venue venue = venueRepository.findById(1L).get();
    Hall hall = new Hall("hall3", -800, venue);
    // capacity of hall is negative
    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "venues/{venue_id}/halls", venue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(hall)))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void createHallForVenueId() throws Exception {
    Venue venue = venueRepository.findById(1L).get();
    Hall hall = new Hall("hall3", 800, venue);

    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "venues/{venue_id}/halls", venue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(hall)))
            .andExpect(status().isCreated())
            .andDo(
                document(
                    "hall-create-for-venue-id",
                    requestFields(entitiesFieldDescriptor.generateHallFields(false))
                        .andWithPrefix("venue.", entitiesFieldDescriptor.generateVenueFields(true))
                        .andWithPrefix(
                            "venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "venue.employees[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))));

    postRequest.andReturn().getResponse().getHeader("Location");
  }

  @Test
  public void hallCreateForVenueIdResponseTest() throws Exception {
    Venue venue = venueRepository.findById(1L).get();
    Hall hall = new Hall("hall4", 800, venue);

    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "venues/{venue_id}/halls", venue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(hall)))
            .andExpect(status().isCreated());

    String location = postRequest.andReturn().getResponse().getHeader("Location");
    this.mockMvc
        .perform(get(location))
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(notNullValue())))
        .andExpect(jsonPath("$.name").value(hall.getName()))
        .andExpect(jsonPath("$.capacityOfHall", is(notNullValue())))
        .andExpect(jsonPath("$.capacityOfHall").value(hall.getCapacityOfHall()))
        .andExpect(jsonPath("$.venue", is(notNullValue())))
        .andExpect(jsonPath("$.venue.employees", not(empty())));
  }

  @Test
  void hallGetById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "halls/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Hall 1"))
        .andExpect(jsonPath("$.capacityOfHall").value(300))
        .andDo(
            document(
                "hall-get-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the hall to be retrieved"))));
  }

  @Test
  void getHallByVenueByIdTest() throws Exception {
    Long hallId = hallRepository.findByName("Hall 1").getId();
    Long venueId = venueRepository.findByName("Le Trianon").getId();
    ResultActions request =
        this.mockMvc
            .perform(
                get(baseUrl + "venues/{venueId}/halls/{id}", venueId, hallId)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(hallId))
        .andExpect(jsonPath("$.name").value("Hall 1"))
        .andExpect(jsonPath("$.capacityOfHall").value(300))
        .andExpect(jsonPath("$.venue.id").value(venueId))
        .andDo(
            document(
                "hall-get-by-venue-by-id",
                pathParameters(
                    parameterWithName("venueId")
                        .description("The id of the venue related to the hall to be retrieved."),
                    parameterWithName("id").description("The id of the hall to be retrieved"))));
  }

  @Test
  void hallsGet() throws Exception {
    ResultActions request = this.mockMvc.perform(get(baseUrl + "halls")).andExpect(status().isOk());
    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$[*].id", is(notNullValue())))
        .andExpect(jsonPath("$[*].name", is(notNullValue())))
        .andExpect(jsonPath("$[*].capacityOfHall", is(notNullValue())))
        .andExpect(jsonPath("$[*].venue", is(notNullValue())))
        .andExpect(jsonPath("$[*].employees", is(notNullValue())))
        .andDo(
            document(
                "halls-get",
                responseFields(fieldWithPath("[]").description("An array of halls"))
                    .andWithPrefix("[].", entitiesFieldDescriptor.generateHallFields(true))
                    .andWithPrefix("[].venue.", entitiesFieldDescriptor.generateVenueFields(true))
                    .andWithPrefix(
                        "[].venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "[].venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))));
  }
}
