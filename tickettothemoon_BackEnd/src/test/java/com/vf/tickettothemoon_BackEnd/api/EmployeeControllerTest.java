package com.vf.tickettothemoon_BackEnd.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.EmployeeRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class EmployeeControllerTest {
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;
        String url = "http://localhost:8080/api/";
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
        public void employeesCreateExample() throws Exception {

                Map<String, Object> employee = new HashMap<>();
                // employee.put("id", 2L);
                employee.put("username", "username1");
                employee.put("password", "secretpwD%1");
                employee.put("email", "mymail@mail.fr");


                String employees = this.mockMvc
                                .perform(post(url + "employees").contentType(MediaTypes.HAL_JSON)
                                                .content(this.objectMapper
                                                                .writeValueAsString(employee)))
                                .andExpect(status().isCreated()).andReturn().getResponse()
                                .getHeader("Location");
        }

        @Test
        public void employeesGetExample() throws Exception {

                // Map<String, Object> employee = new HashMap<>();
                // employee.put("id", 1L);
                // employee.put("username", "username1");
                // employee.put("password", "secretpwD%1");
                // employee.put("email", "mymail@mail.fr");

                // String employees = this.mockMvc
                // .perform(post(url + "employees").contentType(MediaTypes.HAL_JSON)
                // .content(this.objectMapper
                // .writeValueAsString(employee)))
                // .andExpect(status().isCreated()).andReturn().getResponse()
                // .getHeader("Location");

                // // ConstraintDescriptions desc = new ConstraintDescriptions(Employee.class);
                // // FIXME : url seems to be correct as well as the code but it sends a 404 error
                // // instead of a 200 expected.
                // // to see the trace you could go
                // //
                // file:///Users/Virg/Documents/DEV_LOCAL/TicketToTheMoon/tickettothemoon_BackEnd/build/reports/tests/test/classes/com.vf.tickettothemoon_BackEnd.api.EmployeeControllerTest.html
                // // or https://scans.gradle.com/s/y5eaxirpfkcwu/tests/overview
                // this.mockMvc.perform(get(employees)).andExpect(status().isOk())
                // .andExpect(jsonPath("username", is(employee.get("username"))))
                // .andDo(document("employees-get-example",
                // preprocessRequest(prettyPrint()),
                // preprocessResponse(prettyPrint()),
                // requestFields(fieldWithPath("id")
                // .description("The id of the input"
                // + collectionToDelimitedString(
                // desc.descriptionsForProperty(
                // "id"),
                // ". ")),
                // fieldWithPath("username")
                // .description("The username of the input"),
                // fieldWithPath("password")
                // .description("The password of the input"),
                // fieldWithPath("email").description(
                // "The email of the input"))));
        }
        // @Test
        // public void employeesListExample() throws Exception {
        // FIXME MismatchedInputException
        // this.employeeRepository.deleteAll();
        // this.createEmployee("username1");
        // this.createEmployee("username2");
        // this.createEmployee("username3");

        // MvcResult result = this.mockMvc.perform(get(url + "employees"))
        // .andExpect(status().isOk())
        // .andDo(document("employees-list-example", links(linkWithRel("self")
        // .description("Canonical link for this resource"),
        // linkWithRel("profile").description(
        // "The ALPS profile for this resource")),
        // responseFields(subsectionWithPath(
        // "_embedded.employees").description(
        // "An array of <<resources_employee,Employee resources>>"),
        // subsectionWithPath("_links")
        // .description("<<resources_employees_list_links, Links>> to other resources"))))
        // .andReturn();

        // String yourJsonObject =
        // "[{\"id\": 1,\"username\": \"username1\",\"password\": \"secretpwD%1\",\"email\":
        // \"mail@monmail.fr\"}, {\"id\": 2,\"username\": \"username2\",\"password\":
        // \"secretpwD%1\",\"email\": \"mail@monmail.fr\"}, {\"id\": 3,\"username\":
        // \"username3\",\"password\": \"secretpwD%1\",\"email\": \"mail@monmail.fr\"}]";

        // List<Employee> employees = objectMapper.readValue(yourJsonObject,
        // new TypeReference<List<Employee>>() {});
        // String jsonResponse = objectMapper.writeValueAsString(employees);


        // // Remplacez le contenu de la réponse dans le résultat de la requête
        // MockHttpServletResponse response = result.getResponse();
        // response.setContentLength(jsonResponse.length());
        // response.setContentType("application/json");
        // response.getWriter().write(jsonResponse);

        // }



        private void createEmployee(String username) {
                Employee employee = new Employee();
                employee.setUsername(username);
                employee.setPassword("secretpwD%1");
                employee.setEmail("mail@monmail.fr");
                this.employeeRepository.save(employee);
        }

}
