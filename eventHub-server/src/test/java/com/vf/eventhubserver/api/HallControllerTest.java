package com.vf.eventhubserver.api;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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
import java.util.HashSet;
import java.util.Set;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.domain.dao.EmployeeRepository;
import com.vf.eventhubserver.domain.dao.HallRepository;
import com.vf.eventhubserver.domain.dao.VenueRepository;
import com.vf.eventhubserver.domain.model.Address;
import com.vf.eventhubserver.domain.model.Employee;
import com.vf.eventhubserver.domain.model.Hall;
import com.vf.eventhubserver.domain.model.Venue;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptor;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class HallControllerTest {

        @Autowired
        private ObjectMapper objectMapper;
        private MockMvc mockMvc;
        String baseUrl = "http://localhost:8080/api/";
        private EntitiesFieldDescriptor entitiesFieldDescriptor = new EntitiesFieldDescriptor();
        @Autowired
        EmployeeRepository employeeRepository;
        @Autowired
        VenueRepository venueRepository;
        @Autowired
        HallRepository hallRepository;

        @BeforeEach
        public void setUp(WebApplicationContext webApplicationContext,
                        RestDocumentationContextProvider restDocumentation) {
                this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(documentationConfiguration(restDocumentation))
                                .alwaysDo(document("{method-name}",
                                                preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint())))
                                .build();
        }

        @Test
        public void createHallForVenueId() throws Exception {
                Address address =
                                new Address("testStreet", "testCity", "testZipCode", "testCountry");
                Employee employee = new Employee("testUsername", "testPassword1&",
                                "testEmail@example.com");
                Set<Employee> employees = new HashSet<>();
                employees.add(employee);
                Venue venue = new Venue("testName", address, employees);
                venueRepository.save(venue);
                Hall hall = new Hall("testName", 500, venue);
                // TODISCUSS: venue_id is 2 because we are using /init/DbInitializer.java to
                // populate the database. How to get the id of the venue created in the test? Or
                // maybe I shouldn't have some fixtures in the database?
                this.mockMvc.perform(post(baseUrl + "venues/{venue_id}/halls", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hall)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id", is(notNullValue())))
                                .andExpect(jsonPath("$.name", is(notNullValue())))
                                .andExpect(jsonPath("$.capacityOfHall", is(notNullValue())))
                                .andExpect(jsonPath("$.venue", is(notNullValue())))
                                .andExpect(jsonPath("$.venue.employees", not(empty())))
                                .andDo(document("hall-create-for-venue-id",
                                                preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint())));
        }

        @Test
        public void hallGetById() throws Exception {
                // reset the database if needed
                // create a hall if needed
                this.mockMvc.perform(get(baseUrl + "halls/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
                                .andExpect(jsonPath("$.id").value(1))
                                .andDo(document("hall-get-by-id",
                                                pathParameters(parameterWithName("id").description(
                                                                "The id of the hall to be retrieved"))));

        }

        @Test
        public void hallsGet() throws Exception {
                this.mockMvc.perform(get(baseUrl + "halls").accept(MediaTypes.HAL_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].id", is(notNullValue())))
                                .andExpect(jsonPath("$[*].name", is(notNullValue())))
                                .andExpect(jsonPath("$[*].capacityOfHall", is(notNullValue())))
                                .andExpect(jsonPath("$[*].venue", is(notNullValue())))
                                .andExpect(jsonPath("$[*].employees", is(notNullValue())))
                                .andDo(document("halls-get", responseFields(fieldWithPath("[]")
                                                .description("An array of halls")).andWithPrefix(
                                                                "[].",
                                                                entitiesFieldDescriptor
                                                                                .getHallResponseFields())
                                                                .andWithPrefix("[].venue.",
                                                                                entitiesFieldDescriptor
                                                                                                .getVenueResponseFields())
                                                                .andWithPrefix("[].venue.address.",
                                                                                entitiesFieldDescriptor
                                                                                                .getAddressResponseFields())
                                                                .andWithPrefix("[].venue.employees.[].",
                                                                                entitiesFieldDescriptor
                                                                                                .getEmployeeResponseFields())));
        }



        @Test
        public void deleteHall() throws Exception {
                this.mockMvc.perform(delete(baseUrl + "halls/{id}", 1L))
                                .andExpect(status().isNoContent())
                                .andDo(document("hall-delete", preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint())));
        }



}