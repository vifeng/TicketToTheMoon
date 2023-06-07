package com.vf.tickettothemoon_BackEnd.api;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class VenueControllerTest {
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;


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
        public void venuesGetExample() throws Exception {

                Map<String, Object> crud = new HashMap<>();
                crud.put("id", 1L);
                crud.put("name", "name1");
                crud.put("address", "address1");
                crud.put("zipCode", "zipCode1");
                crud.put("town", "town1");
                crud.put("contact", "contact1");
                crud.put("mail", "mail1");



                ConstraintDescriptions desc = new ConstraintDescriptions(Venue.class);

                this.mockMvc.perform(get("/api/venues").accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(crud)))
                                .andExpect(status().isOk())
                                .andDo(document("venues-get-example",
                                                preprocessRequest(prettyPrint()),
                                                preprocessResponse(prettyPrint()),
                                                requestFields(fieldWithPath("id")
                                                                .description("The id of the input"
                                                                                + collectionToDelimitedString(
                                                                                                desc.descriptionsForProperty(
                                                                                                                "id"),
                                                                                                ". ")),
                                                                fieldWithPath("name").description(
                                                                                "The name of the input"),
                                                                fieldWithPath("address")
                                                                                .description("The address of the input"),
                                                                fieldWithPath("zipCode")
                                                                                .description("The zipCode of the input"),
                                                                fieldWithPath("town").description(
                                                                                "The town of the input"),
                                                                fieldWithPath("contact")
                                                                                .description("The contact of the input"),
                                                                fieldWithPath("mail").description(
                                                                                "mail of the input"))));
        }
}
