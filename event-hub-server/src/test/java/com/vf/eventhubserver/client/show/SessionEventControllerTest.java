package com.vf.eventhubserver.client.show;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.show.SessionEventRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPersona;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorShow;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorVenue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
public class SessionEventControllerTest {

  @Autowired private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  String baseUrl = "http://localhost:8080/api/sessionevents";
  private EntitiesFieldDescriptorShow entitiesFieldDescriptorShow =
      new EntitiesFieldDescriptorShow();
  private EntitiesFieldDescriptorVenue entitiesFieldDescriptorVenue =
      new EntitiesFieldDescriptorVenue();
  private EntitiesFieldDescriptorPersona entitiesFieldDescriptorPersona =
      new EntitiesFieldDescriptorPersona();
  @Autowired SessionEventRepository sessionEventRepository;

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
  }

  @Test
  public void testGetAllSessionEvents() throws Exception {
    ResultActions request =
        mockMvc
            .perform(get(baseUrl).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(
                document(
                    "sessionevents-get",
                    responseFields(fieldWithPath("[]").description("An array of session events"))
                        .andWithPrefix(
                            "[].", entitiesFieldDescriptorShow.generateSessionEventFields(true))
                        .andWithPrefix(
                            "[].event.", entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "[].configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "[].configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "[].configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "[].configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "[].configurationHall.hall.venue.employees[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))

                    // end
                    ));

    request
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].durationInMinutes").value(90))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].durationInMinutes").value(120));
  }

  @Test
  public void testGetSessionEventById() throws Exception {
    ResultActions request =
        mockMvc
            .perform(
                get(baseUrl + "/{sessionEventId}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(
                document(
                    "sessionevents-get-id",
                    pathParameters(
                        parameterWithName("sessionEventId")
                            .description("The id of the session event to be retrieved"))));

    request
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.durationInMinutes").value(90));
  }
}
