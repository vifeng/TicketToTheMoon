package com.vf.eventhubserver.api;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.domain.dao.EmployeeRepository;
import com.vf.eventhubserver.domain.model.Employee;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class EmployeeControllerTest {
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;
        String baseUrl = "http://localhost:8080/api/";
        @Autowired
        private EmployeeRepository employeeRepository;


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
        public void employeesCreate() throws Exception {

                Map<String, Object> employee = new HashMap<>();
                // employee.put("id", 2L);
                employee.put("username", "username1");
                employee.put("password", "secretpwD%1");
                employee.put("email", "mymail@mail.fr");


                String employees = this.mockMvc
                                .perform(post(baseUrl + "employees")
                                                .contentType(MediaTypes.HAL_JSON)
                                                .content(this.objectMapper
                                                                .writeValueAsString(employee)))
                                .andExpect(status().isCreated()).andReturn().getResponse()
                                .getHeader("Location");
        }

        // TODISCUSS : this doesn't use repository whereas the hallControllerTest does. which should
        // be the best practice? Can we do something for .andDo(document(... to avoid repetition in
        // the tests?
        @Test
        public void employeesGet() throws Exception {
                Map<String, Object> employee = new HashMap<>();
                employee.put("id", 1L);
                employee.put("username", "username1");
                employee.put("password", "secretpwD%1");
                employee.put("email", "mymail@mail.fr");

                String employees = this.mockMvc
                                .perform(post(baseUrl + "employees")
                                                .contentType(MediaTypes.HAL_JSON)
                                                .content(this.objectMapper
                                                                .writeValueAsString(employee)))
                                .andExpect(status().isCreated()).andReturn().getResponse()
                                .getHeader("Location");
                ConstraintDescriptions desc = new ConstraintDescriptions(Employee.class);

                this.mockMvc.perform(get(baseUrl + "employees")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].id", is(notNullValue())))
                                .andExpect(jsonPath("$[*].username", is(notNullValue())))
                                .andExpect(jsonPath("$[*].username", contains("username1")))
                                .andDo(document("employees-get", responseFields(
                                                fieldWithPath("[]").description(
                                                                "An array of employees"),
                                                fieldWithPath("[].id")
                                                                .description("The id of the input. "
                                                                                + collectionToDelimitedString(
                                                                                                desc.descriptionsForProperty(
                                                                                                                "id"),
                                                                                                ". ")),
                                                fieldWithPath("[].username").description(
                                                                "The username of the input. "
                                                                                + collectionToDelimitedString(
                                                                                                desc.descriptionsForProperty(
                                                                                                                "username"),
                                                                                                ". ")),
                                                fieldWithPath("[].password").description(
                                                                "The password of the input. "
                                                                                + collectionToDelimitedString(
                                                                                                desc.descriptionsForProperty(
                                                                                                                "password"),
                                                                                                ". ")),
                                                fieldWithPath("[].email").description(
                                                                "The email of the input. "
                                                                                + collectionToDelimitedString(
                                                                                                desc.descriptionsForProperty(
                                                                                                                "email"),
                                                                                                ". ")))));
        }



        private void createEmployee(String username) {
                Employee employee = new Employee();
                employee.setUsername(username);
                employee.setPassword("secretpwD%1");
                employee.setEmail("mail@monmail.fr");
                this.employeeRepository.save(employee);
        }

}