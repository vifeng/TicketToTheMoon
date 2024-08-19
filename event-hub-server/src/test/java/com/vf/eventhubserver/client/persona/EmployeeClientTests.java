package com.vf.eventhubserver.client.persona;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vf.eventhubserver.persona.EmployeeDTO;
import java.net.URI;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/testdb/data.sql"})
public class EmployeeClientTests {

  @Autowired private TestRestTemplate testRestTemplate;
  String EMPLOYEES_URL = "/api/employees";

  @BeforeEach
  public void setup() {
    EmployeeDTO employeeDTO = new EmployeeDTO(1L, "user1", "Password1&", "email@mail.com");
    testRestTemplate.postForLocation(EMPLOYEES_URL, employeeDTO);
  }

  @Test
  public void getAllEmployees() {
    ParameterizedTypeReference<Set<EmployeeDTO>> typeRef =
        new ParameterizedTypeReference<Set<EmployeeDTO>>() {};
    ResponseEntity<Set<EmployeeDTO>> responseEntity =
        testRestTemplate.exchange(EMPLOYEES_URL, HttpMethod.GET, null, typeRef);
    Set<EmployeeDTO> employeeDTOs = responseEntity.getBody();

    assertThat(employeeDTOs).isNotNull();
    assertThat(employeeDTOs.size()).isGreaterThan(0);
  }

  @Test
  public void getEmployeeById() {
    String url = EMPLOYEES_URL + "/1";
    EmployeeDTO employeeDTO = testRestTemplate.getForObject(url, EmployeeDTO.class);

    assertThat(employeeDTO).isNotNull();
    assertThat(employeeDTO.id()).isEqualTo(1L);
  }

  @Test
  public void createEmployee() {
    EmployeeDTO employeeDTO = new EmployeeDTO(2L, "user2", "Password2&", "email2@mail.com");
    URI newLocation = testRestTemplate.postForLocation(EMPLOYEES_URL, employeeDTO);

    EmployeeDTO retrievedEmployeeDTO =
        testRestTemplate.getForObject(newLocation, EmployeeDTO.class);
    assert retrievedEmployeeDTO != null;
    assertThat(retrievedEmployeeDTO.username()).isEqualTo(employeeDTO.username());
  }

  @Test
  public void createFailEmployee() {
    EmployeeDTO employeeDTO = new EmployeeDTO(3L, "user3", "password1", "email3@mail.com");

    ResponseEntity<String> responseEntity =
        testRestTemplate.postForEntity(EMPLOYEES_URL, employeeDTO, String.class);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    assertTrue(responseEntity.getBody().contains("invalid password"));
  }
}
