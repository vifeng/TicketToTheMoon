package com.vf.eventhubserver.show;

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
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.persona.Address;
import com.vf.eventhubserver.persona.Employee;
import com.vf.eventhubserver.persona.EmployeeRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptor;
import com.vf.eventhubserver.venue.Hall;
import com.vf.eventhubserver.venue.HallRepository;
import com.vf.eventhubserver.venue.Venue;
import com.vf.eventhubserver.venue.VenueRepository;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class HallControllerTest {

  @Autowired private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  String baseUrl = "http://localhost:8080/api/";
  private EntitiesFieldDescriptor entitiesFieldDescriptor = new EntitiesFieldDescriptor();
  @Autowired EmployeeRepository employeeRepository;
  @Autowired VenueRepository venueRepository;
  @Autowired HallRepository hallRepository;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    Address address = new Address("testStreet", "testCity", "testZipCode", "testCountry");
    Employee employee = new Employee("username1", "testPassword1&", "testEmail@example.com");
    Set<Employee> employees = new HashSet<>();
    employees.add(employee);
    Venue venue = new Venue("VenueName1", address, employees);
    venueRepository.save(venue);
    Hall hall1 = new Hall("hall1", 300, venue);
    Hall hall2 = new Hall("hall2", 500, venue);

    hallRepository.save(hall1);
    hallRepository.save(hall2);

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

  @AfterEach
  void tearDown() {
    hallRepository.deleteAll();
    venueRepository.deleteAll();
  }

  @Test
  void createHallForVenueId() throws Exception {
    Employee employee = new Employee("username2", "testPassword2&", "email2@example.com");
    Set<Employee> employees = new HashSet<>();
    employees.add(employee);
    Venue venue = venueRepository.findById(1L).get();
    Hall hall = new Hall("hall3", 800, venue);

    ResultActions request =
        this.mockMvc
            .perform(
                post(baseUrl + "venues/{venue_id}/halls", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(hall)))
            .andExpect(status().isCreated());

    request
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(notNullValue())))
        .andExpect(jsonPath("$.name").value(hall.getName()))
        .andExpect(jsonPath("$.capacityOfHall", is(notNullValue())))
        .andExpect(jsonPath("$.capacityOfHall").value(hall.getCapacityOfHall()))
        .andExpect(jsonPath("$.venue", is(notNullValue())))
        .andExpect(jsonPath("$.venue.employees", not(empty())))
        .andDo(
            document(
                "hall-create-for-venue-id",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
  }

  @Test
  void hallGetById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "halls/{id}", 4L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.name").value("hall1"))
        .andExpect(jsonPath("$.capacityOfHall").value(300))
        .andDo(
            document(
                "hall-get-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the hall to be retrieved"))));
  }

  @Test
  void hallsGet() throws Exception {
    this.mockMvc
        .perform(get(baseUrl + "halls").accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id", is(notNullValue())))
        .andExpect(jsonPath("$[*].name", is(notNullValue())))
        .andExpect(jsonPath("$[*].capacityOfHall", is(notNullValue())))
        .andExpect(jsonPath("$[*].venue", is(notNullValue())))
        .andExpect(jsonPath("$[*].employees", is(notNullValue())))
        .andDo(
            document(
                "halls-get",
                responseFields(fieldWithPath("[]").description("An array of halls"))
                    .andWithPrefix("[].", entitiesFieldDescriptor.getHallResponseFields())
                    .andWithPrefix("[].venue.", entitiesFieldDescriptor.getVenueResponseFields())
                    .andWithPrefix(
                        "[].venue.address.", entitiesFieldDescriptor.getAddressResponseFields())
                    .andWithPrefix(
                        "[].venue.employees.[].",
                        entitiesFieldDescriptor.getEmployeeResponseFields())));
  }
}
