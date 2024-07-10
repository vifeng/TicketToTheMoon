package com.vf.eventhubserver.client.persona;

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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.persona.Employee;
import com.vf.eventhubserver.persona.EmployeeDTO;
import com.vf.eventhubserver.persona.EmployeeMapper;
import com.vf.eventhubserver.persona.EmployeeService;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptor;
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

  EntitiesFieldDescriptor entitiesFieldDescriptor = new EntitiesFieldDescriptor();
  Map<String, Object> employee;

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

  /**
   * Test the creation of an employee and document the request fields
   *
   * @throws Exception
   */
  @Test
  void employeesCreate() throws Exception {
    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "employees")
                    .contentType(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(createEntityEmployee())))
            .andExpect(status().isCreated())
            .andDo(
                document(
                    "employees-create",
                    requestFields(
                        attributes(key("title").value("Fields for employee creation")),
                        entitiesFieldDescriptor.generateEmployeeFields(false))));
    postRequest.andReturn().getResponse().getHeader("Location");
  }

  /**
   * Test the response of the employee creation
   *
   * @throws Exception
   */
  @Test
  void employeeCreateResponseTest() throws Exception {
    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "employees")
                    .contentType(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(createEntityEmployee())))
            .andExpect(status().isCreated());
    String location = postRequest.andReturn().getResponse().getHeader("Location");

    ResultActions getRequest =
        this.mockMvc
            .perform(get(location))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.username", is(employee.get("username").toString())))
            .andExpect(jsonPath("$.password", is(employee.get("password").toString())))
            .andExpect(jsonPath("$.email", is(employee.get("email").toString())));
  }

  @Test
  void employeesGet() throws Exception {
    ResultActions request =
        this.mockMvc.perform(get(baseUrl + "employees")).andExpect(status().isOk());
    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$[*].id", is(notNullValue())))
        .andExpect(jsonPath("$[*].username", is(notNullValue())))
        .andExpect(jsonPath("$[*].username", hasItems("username1", "username2")));
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
                    parameterWithName("id").description("The id of the employee to be deleted"))));
  }

  public Map<String, Object> createEntityEmployee() {
    employee = new HashMap<>();
    employee.put("username", "username900");
    employee.put("password", "secretpwD%1");
    employee.put("email", "mymail@mail.fr");
    return employee;
  }
}
