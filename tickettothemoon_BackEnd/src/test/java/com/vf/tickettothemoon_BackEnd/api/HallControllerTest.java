package com.vf.tickettothemoon_BackEnd.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class HallControllerTest {

        @Autowired
        private ObjectMapper objectMapper;
        private MockMvc mockMvc;
        String baseUrl = "http://localhost:8080/api/";
        private EntitiesFieldDescriptor entitiesFieldDescriptor = new EntitiesFieldDescriptor();

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
        public void hallsGetById() throws Exception {
                // reset the database if needed
                // create a hall if needed
                this.mockMvc.perform(get(baseUrl + "halls/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
                                .andExpect(jsonPath("$.id").value(1))
                                .andDo(document("hall-get-by-id", preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint())));

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
                                .andDo(document("hall-get", responseFields(fieldWithPath("[]")
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



        // @Test
        // public void updateHall() throws Exception {
        // Venue venue = new Venue("testName", "testAddress", "testCity", "testCountry");
        // Hall hall = new Hall("testName", 500, venue);
        // // Set the properties of hallDTO for update

        // this.mockMvc.perform(put(baseUrl + "halls/{id}", 1L)
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(hallDTO)))
        // .andExpect(status().isOk())
        // .andExpect(jsonPath("$.id", is(notNullValue())))
        // .andExpect(jsonPath("$.name", is(notNullValue())))
        // .andExpect(jsonPath("$.capacityOfHall", is(notNullValue())))
        // .andExpect(jsonPath("$.venue", is(notNullValue())))
        // .andExpect(jsonPath("$.employees", is(notNullValue())))
        // .andDo(document("hall-update", preprocessRequest(prettyPrint()),
        // preprocessResponse(prettyPrint())));
        // }

        // @Test
        // public void patchHall() throws Exception {
        // Map<String, Object> hallPatch = new HashMap<>();
        // // Set the properties of hallPatch for patch

        // this.mockMvc.perform(patch(baseUrl + "halls/{id}", 1L)
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(hallPatch)))
        // .andExpect(status().isOk())
        // .andExpect(jsonPath("$.id", is(notNullValue())))
        // .andExpect(jsonPath("$.name", is(notNullValue())))
        // .andExpect(jsonPath("$.capacityOfHall", is(notNullValue())))
        // .andExpect(jsonPath("$.venue", is(notNullValue())))
        // .andExpect(jsonPath("$.employees", is(notNullValue())))
        // .andDo(document("hall-patch", preprocessRequest(prettyPrint()),
        // preprocessResponse(prettyPrint())));
        // }

        @Test
        public void deleteHall() throws Exception {
                this.mockMvc.perform(delete(baseUrl + "halls/{id}", 1L))
                                .andExpect(status().isNoContent())
                                .andDo(document("hall-delete", preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint())));
        }

        // @Test
        // public void createHallForVenueId() throws Exception {
        // HallDTO hallDTO = new HallDTO();
        // // Set the properties of hallDTO for creation

        // this.mockMvc.perform(post(baseUrl + "venues/{venue_id}/halls", 1L)
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(hallDTO)))
        // .andExpect(status().isCreated())
        // .andExpect(jsonPath("$.id", is(notNullValue())))
        // .andExpect(jsonPath("$.name", is(notNullValue())))
        // .andExpect(jsonPath("$.capacityOfHall", is(notNullValue())))
        // .andExpect(jsonPath("$.venue", is(notNullValue())))
        // .andExpect(jsonPath("$.employees", is(notNullValue())))
        // .andDo(document("hall-create", preprocessRequest(prettyPrint()),
        // preprocessResponse(prettyPrint())));
        // }

        @Test
        public void getHallsByVenueId() throws Exception {
                this.mockMvc.perform(get(baseUrl + "venues/{venue_id}/halls", 1L))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))))
                                .andDo(document("hall-get-by-venue",
                                                preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint())));
        }
}
