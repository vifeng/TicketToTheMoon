package com.vf.eventhubserver.client.order;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.order.TicketReservationRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorOrder;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPersona;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorShow;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorTarification;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorVenue;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
public class TicketReservationControllerTest {

  @Autowired private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  String baseUrl = "http://localhost:8080/api/";
  private EntitiesFieldDescriptorOrder entitiesFieldDescriptorOrder =
      new EntitiesFieldDescriptorOrder();
  private EntitiesFieldDescriptorVenue entitiesFieldDescriptorVenue =
      new EntitiesFieldDescriptorVenue();
  private EntitiesFieldDescriptorTarification entitiesFieldDescriptorTarification =
      new EntitiesFieldDescriptorTarification();
  private EntitiesFieldDescriptorShow entitiesFieldDescriptorShow =
      new EntitiesFieldDescriptorShow();
  private EntitiesFieldDescriptorPersona entitiesFieldDescriptorPersona =
      new EntitiesFieldDescriptorPersona();

  @Autowired TicketReservationRepository ticketReservationRepository;

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
  public void getAllTicketReservations() throws Exception {
    ResultActions request =
        this.mockMvc.perform(get(baseUrl + "ticketReservation")).andExpect(status().isOk());

    request
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$[0].ticketReservationKey").isNotEmpty())
        .andExpect(jsonPath("$[0].ticketReservationKey.seatId.id").value(1))
        .andExpect(jsonPath("$[0].ticketReservationKey.sessionEventId.id").value(1))
        .andExpect(jsonPath("$[1].ticketReservationKey.seatId.id").value(2))
        .andExpect(jsonPath("$[1].ticketReservationKey.sessionEventId.id").value(1))
        .andDo(
            document(
                "ticketReservation-get",
                responseFields(fieldWithPath("[]").description("An array of ticket reservations"))
                    .andWithPrefix(
                        "[].", entitiesFieldDescriptorOrder.generateTicketReservationFields(false))
                    .andWithPrefix(
                        "[].ticketReservationKey.",
                        entitiesFieldDescriptorOrder.generateTicketReservationKeyFields(false))
                    // seatId
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.",
                        entitiesFieldDescriptorVenue.generateSeatFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.categorySpatial.",
                        entitiesFieldDescriptorVenue.generateCategorySpatialFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.categoryTariff.",
                        entitiesFieldDescriptorTarification.generateCategoryTariffFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.categoryTariff.tarification.",
                        entitiesFieldDescriptorTarification.generateTarificationFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.categoryTariff.tarification.event.",
                        entitiesFieldDescriptorShow.generateEventFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.seatStatus.",
                        entitiesFieldDescriptorVenue.generateSeatStatusFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.configurationHall.",
                        entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.configurationHall.hall.",
                        entitiesFieldDescriptorVenue.generateHallFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.configurationHall.hall.venue.",
                        entitiesFieldDescriptorVenue.generateVenueFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.configurationHall.hall.venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "[].ticketReservationKey.seatId.configurationHall.hall.venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                    // sessionEventId
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.",
                        entitiesFieldDescriptorShow.generateSessionEventFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.event.",
                        entitiesFieldDescriptorShow.generateEventFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.configurationHall.",
                        entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.configurationHall.hall.",
                        entitiesFieldDescriptorVenue.generateHallFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.configurationHall.hall.venue.",
                        entitiesFieldDescriptorVenue.generateVenueFields(true))
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.configurationHall.hall.venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "[].ticketReservationKey.sessionEventId.configurationHall.hall.venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))));
  }
}
