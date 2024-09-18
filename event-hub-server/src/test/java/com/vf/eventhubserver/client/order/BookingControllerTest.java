package com.vf.eventhubserver.client.order;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.order.Booking;
import com.vf.eventhubserver.order.BookingRepository;
import com.vf.eventhubserver.order.TicketReservation;
import com.vf.eventhubserver.order.TicketReservationKey;
import com.vf.eventhubserver.order.TicketReservationKeyDTO;
import com.vf.eventhubserver.order.TicketReservationKeyMapper;
import com.vf.eventhubserver.order.TicketReservationRepository;
import com.vf.eventhubserver.show.Event;
import com.vf.eventhubserver.show.EventRepository;
import com.vf.eventhubserver.show.SessionEvent;
import com.vf.eventhubserver.show.SessionEventRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorOrder;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPersona;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorShow;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorTarification;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorVenue;
import com.vf.eventhubserver.venue.ConfigurationHall;
import com.vf.eventhubserver.venue.ConfigurationHallRepository;
import com.vf.eventhubserver.venue.Seat;
import com.vf.eventhubserver.venue.SeatRepository;
import java.time.LocalDateTime;
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
public class BookingControllerTest {
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
  @Autowired BookingRepository bookingRepository;
  @Autowired TicketReservationRepository ticketReservationRepository;
  @Autowired SeatRepository seatRepository;
  @Autowired SessionEventRepository sessionEventRepository;
  @Autowired TicketReservationKeyMapper ticketReservationKeyMapper;
  @Autowired EventRepository eventRepository;
  @Autowired ConfigurationHallRepository configurationHallRepository;

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
  public void getBookingById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "bookings/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.bookingCreationTimestamp", notNullValue()))
        .andExpect(jsonPath("$.bookingCreationTimestamp").isNotEmpty())
        .andExpect(jsonPath("$.totalPriceHt").value(20.0))
        .andExpect(jsonPath("$.reservations").isNotEmpty())
        .andExpect(jsonPath("$.customer").isNotEmpty())
        .andExpect(jsonPath("$.customer.id").value(1))
        .andDo(
            document(
                "booking-get-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the booking to be retrieved"))));
  }

  @Test
  public void getAllBookings() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "bookings").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].bookingCreationTimestamp").isNotEmpty())
        .andExpect(jsonPath("$[0].totalPriceHt").value(20.0))
        .andExpect(jsonPath("$[0].reservations").isNotEmpty())
        .andExpect(jsonPath("$[0].customer").isNotEmpty())
        .andExpect(jsonPath("$[0].customer.id").value(1))
        .andDo(
            document(
                "booking-get",
                responseFields(fieldWithPath("[]").description("An array of bookings"))
                    .andWithPrefix("[].", entitiesFieldDescriptorOrder.generateBookingFields(true))
                    .andWithPrefix(
                        "[].customer.", entitiesFieldDescriptorPersona.generateCustomerFields(true))
                    .andWithPrefix(
                        "[].customer.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    // reservations
                    .andWithPrefix(
                        "[].reservations.[].",
                        entitiesFieldDescriptorOrder.generateTicketReservationFields(false))
                    .andWithPrefix(
                        "[].reservations.[].id.",
                        entitiesFieldDescriptorOrder.generateTicketReservationKeyFields(false))
                    // seatId
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.",
                        entitiesFieldDescriptorVenue.generateSeatFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.categorySpatial.",
                        entitiesFieldDescriptorVenue.generateCategorySpatialFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.categoryTariff.",
                        entitiesFieldDescriptorTarification.generateCategoryTariffFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.categoryTariff.tarification.",
                        entitiesFieldDescriptorTarification.generateTarificationFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.categoryTariff.tarification.event.",
                        entitiesFieldDescriptorShow.generateEventFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.seatStatus.",
                        entitiesFieldDescriptorVenue.generateSeatStatusFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.configurationHall.",
                        entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.configurationHall.hall.",
                        entitiesFieldDescriptorVenue.generateHallFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.configurationHall.hall.venue.",
                        entitiesFieldDescriptorVenue.generateVenueFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.configurationHall.hall.venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "[].reservations.[].id.seatId.configurationHall.hall.venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                    // sessionEventId
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.",
                        entitiesFieldDescriptorShow.generateSessionEventFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.event.",
                        entitiesFieldDescriptorShow.generateEventFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.configurationHall.",
                        entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.configurationHall.hall.",
                        entitiesFieldDescriptorVenue.generateHallFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.configurationHall.hall.venue.",
                        entitiesFieldDescriptorVenue.generateVenueFields(true))
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.configurationHall.hall.venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "[].reservations.[].id.sessionEventId.configurationHall.hall.venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                // end
                ));
  }

  @Test
  public void createBookingForCustomerId() throws Exception {
    TicketReservationKeyDTO ticketReservationKeyDTO = createTicketReservationKeyDTO();

    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "bookings/customer/{customerId}/reservationKey", 2L)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketReservationKeyDTO)))
            .andExpect(status().isCreated())
            .andDo(
                document(
                    "booking-create-for-customer-id",
                    pathParameters(
                        parameterWithName("customerId")
                            .description("The id of the customer to create a booking for")),
                    requestFields(
                            entitiesFieldDescriptorOrder.generateTicketReservationKeyFields(false))
                        // seatId
                        .andWithPrefix(
                            "seatId.", entitiesFieldDescriptorVenue.generateSeatFields(true))
                        .andWithPrefix(
                            "seatId.categorySpatial.",
                            entitiesFieldDescriptorVenue.generateCategorySpatialFields(true))
                        .andWithPrefix(
                            "seatId.categoryTariff.",
                            entitiesFieldDescriptorTarification.generateCategoryTariffFields(true))
                        .andWithPrefix(
                            "seatId.categoryTariff.tarification.",
                            entitiesFieldDescriptorTarification.generateTarificationFields(true))
                        .andWithPrefix(
                            "seatId.categoryTariff.tarification.event.",
                            entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "seatId.seatStatus.",
                            entitiesFieldDescriptorVenue.generateSeatStatusFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.venue.employees.[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                        // sessionEventId
                        .andWithPrefix(
                            "sessionEventId.",
                            entitiesFieldDescriptorShow.generateSessionEventFields(true))
                        .andWithPrefix(
                            "sessionEventId.event.",
                            entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.venue.employees.[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                    // end
                    ));

    String location = postRequest.andReturn().getResponse().getHeader("Location");
    this.mockMvc
        .perform(get(location))
        .andExpect(jsonPath("$.bookingCreationTimestamp", notNullValue()))
        .andExpect(jsonPath("$.reservations").isNotEmpty())
        .andExpect(jsonPath("$.reservations[0].id.seatId.id").value(4))
        .andExpect(jsonPath("$.customer").isNotEmpty())
        .andExpect(jsonPath("$.customer.id").value(2));
  }

  @Test
  public void addReservationToBooking() throws Exception {

    TicketReservationKeyDTO ticketReservationKeyDTO = createTicketReservationKeyDTO();
    int ReservationLengthBefore = bookingRepository.findById(2L).get().getReservations().size();
    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "bookings/{bookingId}/reservationKey", 2L)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketReservationKeyDTO)))
            .andExpect(status().isCreated())
            .andDo(
                document(
                    "booking-add-reservation-to-booking",
                    pathParameters(
                        parameterWithName("bookingId")
                            .description("The id of the booking to add a reservation to")),
                    requestFields(
                            entitiesFieldDescriptorOrder.generateTicketReservationKeyFields(false))
                        // seatId
                        .andWithPrefix(
                            "seatId.", entitiesFieldDescriptorVenue.generateSeatFields(true))
                        .andWithPrefix(
                            "seatId.categorySpatial.",
                            entitiesFieldDescriptorVenue.generateCategorySpatialFields(true))
                        .andWithPrefix(
                            "seatId.categoryTariff.",
                            entitiesFieldDescriptorTarification.generateCategoryTariffFields(true))
                        .andWithPrefix(
                            "seatId.categoryTariff.tarification.",
                            entitiesFieldDescriptorTarification.generateTarificationFields(true))
                        .andWithPrefix(
                            "seatId.categoryTariff.tarification.event.",
                            entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "seatId.seatStatus.",
                            entitiesFieldDescriptorVenue.generateSeatStatusFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "seatId.configurationHall.hall.venue.employees.[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                        // sessionEventId
                        .andWithPrefix(
                            "sessionEventId.",
                            entitiesFieldDescriptorShow.generateSessionEventFields(true))
                        .andWithPrefix(
                            "sessionEventId.event.",
                            entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "sessionEventId.configurationHall.hall.venue.employees.[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                    // end
                    ));

    String location = postRequest.andReturn().getResponse().getHeader("Location");

    this.mockMvc
        .perform(get(location))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.bookingCreationTimestamp", notNullValue()))
        .andExpect(jsonPath("$.totalPriceHt").value(20.0))
        .andExpect(jsonPath("$.reservations").isNotEmpty())
        .andExpect(jsonPath("$.reservations").isArray())
        .andExpect(jsonPath("$.reservations.length()").value(ReservationLengthBefore + 1));
  }

  /**
   * This test does not work because we cannot add a reservation to a booking that has seat already
   * sold. it means the the booking has a paid status. So we cannot add a reservation to it.
   *
   * @throws Exception
   */
  @Test
  public void addReservationToBookingAlreadySold() throws Exception {
    TicketReservationKeyDTO ticketReservationKeyDTO = createTicketReservationKeyDTO();
    int ReservationLengthBefore = bookingRepository.findById(1L).get().getReservations().size();
    ResultActions postRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "bookings/{bookingId}/reservationKey", 1L)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketReservationKeyDTO)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.message").value("An illegal argument error occurred : "));
  }

  @Test
  public void deleteReservationFromBooking() throws Exception {
    Booking booking = bookingRepository.findById(2L).get();
    TicketReservation ticketReservation = booking.getReservations().iterator().next();
    TicketReservationKey ticketReservationKey = ticketReservation.getId();
    this.mockMvc
        .perform(
            delete(baseUrl + "bookings/{bookingId}/reservationKey", 2L)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketReservationKey)))
        .andExpect(status().isOk())
        .andDo(
            document(
                "booking-delete-reservation-from-booking",
                pathParameters(
                    parameterWithName("bookingId")
                        .description("The id of the booking to delete a reservation from")),
                requestFields(
                        entitiesFieldDescriptorOrder.generateTicketReservationKeyFields(false))
                    // seatId
                    .andWithPrefix("seatId.", entitiesFieldDescriptorVenue.generateSeatFields(true))
                    .andWithPrefix(
                        "seatId.categorySpatial.",
                        entitiesFieldDescriptorVenue.generateCategorySpatialFields(true))
                    .andWithPrefix(
                        "seatId.categoryTariff.",
                        entitiesFieldDescriptorTarification.generateCategoryTariffFields(true))
                    .andWithPrefix(
                        "seatId.categoryTariff.tarification.",
                        entitiesFieldDescriptorTarification.generateTarificationFields(true))
                    .andWithPrefix(
                        "seatId.categoryTariff.tarification.event.",
                        entitiesFieldDescriptorShow.generateEventFields(true))
                    .andWithPrefix(
                        "seatId.seatStatus.",
                        entitiesFieldDescriptorVenue.generateSeatStatusFields(true))
                    .andWithPrefix(
                        "seatId.configurationHall.",
                        entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                    .andWithPrefix(
                        "seatId.configurationHall.hall.",
                        entitiesFieldDescriptorVenue.generateHallFields(true))
                    .andWithPrefix(
                        "seatId.configurationHall.hall.venue.",
                        entitiesFieldDescriptorVenue.generateVenueFields(true))
                    .andWithPrefix(
                        "seatId.configurationHall.hall.venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "seatId.configurationHall.hall.venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                    // sessionEventId
                    .andWithPrefix(
                        "sessionEventId.",
                        entitiesFieldDescriptorShow.generateSessionEventFields(true))
                    .andWithPrefix(
                        "sessionEventId.event.",
                        entitiesFieldDescriptorShow.generateEventFields(true))
                    .andWithPrefix(
                        "sessionEventId.configurationHall.",
                        entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                    .andWithPrefix(
                        "sessionEventId.configurationHall.hall.",
                        entitiesFieldDescriptorVenue.generateHallFields(true))
                    .andWithPrefix(
                        "sessionEventId.configurationHall.hall.venue.",
                        entitiesFieldDescriptorVenue.generateVenueFields(true))
                    .andWithPrefix(
                        "sessionEventId.configurationHall.hall.venue.address.",
                        entitiesFieldDescriptorPersona.generateAddressFields(false))
                    .andWithPrefix(
                        "sessionEventId.configurationHall.hall.venue.employees.[].",
                        entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                // end
                ));
  }

  @Test
  public void deleteBookingById() throws Exception {
    this.mockMvc
        .perform(delete(baseUrl + "bookings/{id}", 2L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andDo(
            document(
                "booking-delete-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the booking to be deleted"))));

    this.mockMvc
        .perform(get(baseUrl + "bookings/{id}", 2L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  public void FalseDeleteBookingByIdPaid() throws Exception {
    this.mockMvc
        .perform(delete(baseUrl + "bookings/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isConflict())
        .andExpect(
            jsonPath("$.message")
                .value(
                    "Remove error : Booking with id {1} cannot be deleted because it has a paid status"));
  }

  private TicketReservationKeyDTO createTicketReservationKeyDTO() {
    Seat seat = seatRepository.findById(4L).get();
    SessionEvent sessionEvent = sessionEventRepository.findById(1L).get();
    TicketReservationKey ticketReservationKey = new TicketReservationKey(seat, sessionEvent);
    TicketReservation ticketReservation = new TicketReservation(ticketReservationKey, false);
    ticketReservationRepository.save(ticketReservation);
    return ticketReservationKeyMapper.toDTO(ticketReservationKey);
  }

  /**
   * The booking is impossible if its date is after the session event.
   *
   * @throws Exception
   */
  @Test
  public void FalsecreateBookingForCustomerId() throws Exception {
    // create a ticket reservation for a session event that is already past
    Seat seat = seatRepository.findById(4L).get();
    Event event = eventRepository.findById(1L).get();
    ConfigurationHall configurationHall = configurationHallRepository.findById(1L).get();
    LocalDateTime dateAndTimeStartSessionEvent = LocalDateTime.now().minusDays(10);
    SessionEvent sessionEvent =
        new SessionEvent(dateAndTimeStartSessionEvent, 90, event, configurationHall);
    sessionEventRepository.save(sessionEvent);
    TicketReservationKey ticketReservationKey = new TicketReservationKey(seat, sessionEvent);
    TicketReservation ticketReservation = new TicketReservation(ticketReservationKey, false);
    ticketReservationRepository.save(ticketReservation);
    TicketReservationKeyDTO ticketReservationKeyDTO =
        ticketReservationKeyMapper.toDTO(ticketReservationKey);
    // request
    this.mockMvc
        .perform(
            post(baseUrl + "bookings/customer/{customerId}/reservationKey", 2L)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketReservationKeyDTO)))
        .andExpect(status().isUnprocessableEntity());
  }
}
