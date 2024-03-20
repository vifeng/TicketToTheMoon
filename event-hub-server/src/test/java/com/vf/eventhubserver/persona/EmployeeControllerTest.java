package com.vf.eventhubserver.persona;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class EmployeeControllerTest {
  private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  String baseUrl = "http://localhost:8080/api/";
  @Autowired private EmployeeService employeeService;
  @Autowired private EmployeeMapper employeeMapper;

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

    Employee employee1 = new Employee(2L, "username1", "secretpwD%1", "email1@mail.com");
    Employee employee2 = new Employee(3L, "username2", "secretpwD%2", "email2@mail.com");
    EmployeeDTO employeeDTO1 = employeeMapper.toDTO(employee1);
    EmployeeDTO employeeDTO2 = employeeMapper.toDTO(employee2);
    this.employeeService.createEmployee(employeeDTO1);
    this.employeeService.createEmployee(employeeDTO2);
  }

  @Test
  void employeesCreate() throws Exception {

    Map<String, Object> employee = new HashMap<>();
    employee.put("username", "username900");
    employee.put("password", "secretpwD%1");
    employee.put("email", "mymail@mail.fr");

    ResultActions request =
        this.mockMvc
            .perform(
                post(baseUrl + "employees")
                    .contentType(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(employee)))
            .andExpect(status().isCreated());
    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(
            (null != employee.get("id"))
                ? jsonPath("$.id", notNullValue())
                : jsonPath("$.id", is(notNullValue())))
        .andExpect(jsonPath("$.username", is("username900")))
        .andExpect(jsonPath("$.password", is("secretpwD%1")))
        .andExpect(jsonPath("$.email", is("mymail@mail.fr")));
  }

  @Test
  void employeesGet() throws Exception {
    ResultActions request =
        this.mockMvc.perform(get(baseUrl + "employees")).andExpect(status().isOk());
    request.andReturn().getResponse().getHeader("Location");
    ConstraintDescriptions desc = new ConstraintDescriptions(Employee.class);

    request
        .andExpect(jsonPath("$[*].id", is(notNullValue())))
        .andExpect(jsonPath("$[*].username", is(notNullValue())))
        .andExpect(jsonPath("$[*].username", hasItems("username1", "username2")))
        .andDo(
            document(
                "employees-get",
                responseFields(
                    fieldWithPath("[]").description("An array of employees"),
                    fieldWithPath("[].id")
                        .description(
                            "The id of the input. "
                                + collectionToDelimitedString(
                                    desc.descriptionsForProperty("id"), ". ")),
                    fieldWithPath("[].username")
                        .description(
                            "The username of the input. "
                                + collectionToDelimitedString(
                                    desc.descriptionsForProperty("username"), ". ")),
                    fieldWithPath("[].password")
                        .description(
                            "The password of the input. "
                                + collectionToDelimitedString(
                                    desc.descriptionsForProperty("password"), ". ")),
                    fieldWithPath("[].email")
                        .description(
                            "The email of the input. "
                                + collectionToDelimitedString(
                                    desc.descriptionsForProperty("email"), ". ")))));
  }

  @Test
  void employeesGetById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "employees/{id}", 3L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.username", is("username2")))
        .andExpect(jsonPath("$.password", is("secretpwD%2")))
        .andExpect(jsonPath("$.email", is("email2@mail.com")))
        .andDo(
            document(
                "employees-get-by-id",
                pathParameters(
                    parameterWithName("id")
                        .description("The id of the employee to be retrieved"))));
  }

  @Test
  void employeesGetByIdNoPwd() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(
                get(baseUrl + "employees/NoPwd/{id}", 3L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());
    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.username", is("username2")))
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.email", is("email2@mail.com")))
        .andDo(
            document(
                "employees-get-by-id-no-pwd",
                pathParameters(
                    parameterWithName("id")
                        .description("The id of the employee to be retrieved"))));
  }

  @Test
  void employeesPatch() throws Exception {
    Map<String, Object> employee = new HashMap<>();
    employee.put("password", "secretpwD%666");

    ResultActions request =
        this.mockMvc
            .perform(
                patch(baseUrl + "employees/{id}", 3L)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(employee)))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.password", is("secretpwD%666")))
        .andDo(
            document(
                "employees-patch",
                pathParameters(
                    parameterWithName("id")
                        .description("The id of the employee to be retrieved"))));
  }

  @Test
  void employeesPut() throws Exception {
    Map<String, Object> employee = new HashMap<>();
    employee.put("id", 3L);
    employee.put("username", "usernamePut1");
    employee.put("password", "secretpwD%1");
    employee.put("email", "mymail@mail.fr");
    ResultActions request =
        this.mockMvc
            .perform(
                put(baseUrl + "employees/{id}", 3L)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(employee)))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.username", is("usernamePut1")))
        .andExpect(jsonPath("$.password", is("secretpwD%1")))
        .andExpect(jsonPath("$.email", is("mymail@mail.fr")))
        .andDo(
            document(
                "employees-put",
                pathParameters(
                    parameterWithName("id").description("The id of the employee to be retrieved")),
                requestFields(
                    fieldWithPath("id").description("The id of the employee"),
                    fieldWithPath("username").description("The username of the employee"),
                    fieldWithPath("password").description("The password of the employee"),
                    fieldWithPath("email").description("The email of the employee"))));
  }

  @Test
  void employeesDelete() throws Exception {
    this.mockMvc
        .perform(delete(baseUrl + "employees/{id}", 2L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andDo(
            document(
                "employees-delete-by-id",
                pathParameters(
                    parameterWithName("id")
                        .description("The id of the employee to be retrieved"))));
  }
}
