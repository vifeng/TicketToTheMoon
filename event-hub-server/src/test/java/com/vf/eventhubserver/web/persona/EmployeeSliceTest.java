package com.vf.eventhubserver.web.persona;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vf.eventhubserver.persona.EmployeeController;
import com.vf.eventhubserver.persona.EmployeeDTO;
import com.vf.eventhubserver.persona.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
public class EmployeeSliceTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private EmployeeService employeeService;

  @Test
  public void testHandleDetailsRequest() throws Exception {
    given(employeeService.findById(1L))
        .willReturn(new EmployeeDTO(1L, "username", "Secret1*", "email@admin.fr"));
    mockMvc
        .perform(get("/api/employees/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("username").value("username"))
        .andExpect(jsonPath("password").value("Secret1*"))
        .andExpect(jsonPath("email").value("email@admin.fr"));

    verify(employeeService).findById(1L);
  }
}
