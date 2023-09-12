package com.vf.tickettothemoon_BackEnd.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
public class EmployeeControllerTest {
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
        public void employeesGetExample() throws Exception {

                Map<String, Object> crud = new HashMap<>();
                crud.put("id", 1L);
                crud.put("username", "username1");
                crud.put("password", "password1*");
                crud.put("email", "email1");


                ConstraintDescriptions desc = new ConstraintDescriptions(Venue.class);

                this.mockMvc.perform(get("/api/employees").accept(MediaType.APPLICATION_JSON)
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
                                                                fieldWithPath("username")
                                                                                .description("The username of the input"),
                                                                fieldWithPath("password")
                                                                                .description("The password of the input"),
                                                                fieldWithPath("email").description(
                                                                                "The email of the input"))));
        }


}
