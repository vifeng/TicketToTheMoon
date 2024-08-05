package com.vf.eventhubserver.client.venue;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
public class VenueControllerTest {

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
  void venueGetById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "venues/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Le Trianon"))
        .andExpect(jsonPath("$.address.street").value("sesame street"))
        .andDo(
            document(
                "venue-get-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the venue to be retrieved"))));
  }

  @Test
  void venueGetAll() throws Exception {
    createVenue();
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "venues").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Le Trianon"))
        .andExpect(jsonPath("$[0].address.street").value("sesame street"))
        .andExpect(jsonPath("$[0].employees", is(notNullValue())))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("VenueName1"))
        .andExpect(jsonPath("$[1].address.street").value("testStreet"))
        .andExpect(jsonPath("$[1].employees", is(notNullValue())))
        .andDo(
            document(
                "venues-get",
                responseFields(fieldWithPath("[]").description("An array of venues"))
                    .andWithPrefix("[].", entitiesFieldDescriptor.generateVenueFields(true))
                    .andWithPrefix(
                        "[].address.", entitiesFieldDescriptor.generateAddressFields(false))
                    .andWithPrefix(
                        "[].employees.[].", entitiesFieldDescriptor.generateEmployeeFields(true))));
  }

  //   test  updateVenue, patchVenue, deleteVenue, addEmployee, removeEmployee

  @Test
  public void createVenueTest() throws Exception {
    Address address = new Address("testStreet2", "testCity", "testZipCode", "testCountry");
    Employee employee = new Employee("username1", "testPassword1&", "testEmail2@example.com");
    Set<Employee> employees = new HashSet<>();
    employees.add(employee);
    Venue venue = new Venue("VenueName2", address, employees);

    ResultActions request =
        this.mockMvc
            .perform(
                post(baseUrl + "venues")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(venue)))
            .andExpect(status().isCreated());

    String location = request.andReturn().getResponse().getHeader("Location");
    this.mockMvc
        .perform(get(location))
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(notNullValue())))
        .andExpect(jsonPath("$.name").value(venue.getName()))
        .andExpect(jsonPath("$.address.street", is(notNullValue())))
        .andExpect(jsonPath("$.address.street").value(venue.getAddress().getStreet()))
        .andExpect(jsonPath("$.employees", is(notNullValue())));
  }

  @Test
  public void updateVenueTest() throws Exception {
    Venue venueToUpdate = venueRepository.findById(1L).get();
    venueToUpdate.setName("changedName");
    Address address = new Address("changedStreet", "testCity", "testZipCode", "testCountry");
    venueToUpdate.setAddress(address);
    Employee employee =
        new Employee("changedUsername", "changedPassword1&", "changedMail@example.com");
    Set<Employee> employees = new HashSet<>();
    employees.add(employee);
    venueToUpdate.setEmployees(employees);

    ResultActions request =
        this.mockMvc
            .perform(
                put(baseUrl + "venues/{id}", venueToUpdate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(venueToUpdate)))
            .andExpect(status().isOk());

    request
        .andExpect(jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.name", is(notNullValue())))
        .andExpect(jsonPath("$.name").value(venueToUpdate.getName()))
        .andExpect(jsonPath("$.address.street", is(notNullValue())))
        .andExpect(jsonPath("$.address.street").value(venueToUpdate.getAddress().getStreet()))
        .andExpect(jsonPath("$.employees", is(notNullValue())));
  }

  @Test
  void venueDelete() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(delete(baseUrl + "venues/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isNoContent())
            .andDo(
                document(
                    "venue-delete-by-id",
                    pathParameters(
                        parameterWithName("id").description("The id of the venue to be deleted"))));

    this.mockMvc
        .perform(get(baseUrl + "venues/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }

  private void createVenue() {
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
  }
}
