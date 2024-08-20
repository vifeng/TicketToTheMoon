package com.vf.eventhubserver.client.payment;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Assertions;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.order.BookingDTO;
import com.vf.eventhubserver.order.TicketReservation;
import com.vf.eventhubserver.order.TicketReservationKey;
import com.vf.eventhubserver.order.TicketReservationKeyDTO;
import com.vf.eventhubserver.order.TicketReservationKeyMapper;
import com.vf.eventhubserver.order.TicketReservationRepository;
import com.vf.eventhubserver.payment.PaymentRepository;
import com.vf.eventhubserver.show.SessionEvent;
import com.vf.eventhubserver.show.SessionEventRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorOrder;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPayment;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPersona;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorShow;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorTarification;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorVenue;
import com.vf.eventhubserver.venue.Seat;
import com.vf.eventhubserver.venue.SeatRepository;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
public class PaymentControllerTest {

  @Autowired private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  String baseUrl = "http://localhost:8080/api/payments";
  private EntitiesFieldDescriptorPayment entitiesFieldDescriptorPayment =
      new EntitiesFieldDescriptorPayment();
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
  @Autowired PaymentRepository paymentRepository;
  @Autowired TicketReservationRepository ticketReservationRepository;
  @Autowired SeatRepository seatRepository;
  @Autowired SessionEventRepository sessionEventRepository;
  @Autowired TicketReservationKeyMapper ticketReservationKeyMapper;

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
  public void getAllPayments() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(
                document(
                    "payments-get",
                    responseFields(fieldWithPath("[]").description("An array of payments"))
                        .andWithPrefix(
                            "[].", entitiesFieldDescriptorPayment.generatePaymentFields(true))
                        .andWithPrefix(
                            "[].booking.", entitiesFieldDescriptorOrder.generateBookingFields(true))
                        .andWithPrefix(
                            "[].booking.customer.",
                            entitiesFieldDescriptorPersona.generateCustomerFields(true))
                        .andWithPrefix(
                            "[].booking.customer.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "[].booking.reservations.[].",
                            entitiesFieldDescriptorOrder.generateTicketReservationFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.",
                            entitiesFieldDescriptorOrder.generateTicketReservationKeyFields(false))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.",
                            entitiesFieldDescriptorVenue.generateSeatFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.categorySpatial.",
                            entitiesFieldDescriptorVenue.generateCategorySpatialFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.categoryTariff.",
                            entitiesFieldDescriptorTarification.generateCategoryTariffFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.categoryTariff.tarification.",
                            entitiesFieldDescriptorTarification.generateTarificationFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.categoryTariff.tarification.event.",
                            entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.seatStatus.",
                            entitiesFieldDescriptorVenue.generateSeatStatusFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.seatId.configurationHall.hall.venue.employees.[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                        // sessionEventId
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.",
                            entitiesFieldDescriptorShow.generateSessionEventFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.event.",
                            entitiesFieldDescriptorShow.generateEventFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.configurationHall.",
                            entitiesFieldDescriptorVenue.generateConfigurationHallFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.configurationHall.hall.",
                            entitiesFieldDescriptorVenue.generateHallFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.configurationHall.hall.venue.",
                            entitiesFieldDescriptorVenue.generateVenueFields(true))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.configurationHall.hall.venue.address.",
                            entitiesFieldDescriptorPersona.generateAddressFields(false))
                        .andWithPrefix(
                            "[].booking.reservations.[].id.sessionEventId.configurationHall.hall.venue.employees.[].",
                            entitiesFieldDescriptorPersona.generateEmployeeFields(true))
                        .andWithPrefix(
                            "[].paymentStatus.",
                            entitiesFieldDescriptorPayment.generatePaymentStatusFields(true))
                    // end
                    ));
  }

  @Test
  public void getPaymentById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(
                document(
                    "payments-get-id",
                    pathParameters(
                        parameterWithName("id")
                            .description("The id of the Payment to be retrieved"))));
    request
        .andExpect(jsonPath("$.booking.reservations.[0].id.seatId.id").value(1L))
        .andExpect(jsonPath("$.booking.reservations.[0].id.sessionEventId.id").value(1L))
        .andExpect(jsonPath("$.booking.reservations.[1].id.seatId.id").value(2L))
        .andExpect(jsonPath("$.booking.reservations.[1].id.sessionEventId.id").value(1L));
  }

  @Test
  public void createPaymentBybookingId() throws Exception {
    // it is necessary to create a booking to create a payment, because the payment can be expired depending on the
    // booking.
    Long bookingId = createBooking();
    Seat seatbefore = seatRepository.findById(4L).get();
    String seatStatusbeforePayment = seatbefore.getSeatStatus().getName();

    ResultActions request =
        this.mockMvc
            .perform(
                post(baseUrl + "/booking/{bookingId}", bookingId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(
                document(
                    "payments-create",
                    pathParameters(
                        parameterWithName("bookingId")
                            .description("The id of the Booking to be paid"))));
            

    String locationPayment = request.andReturn().getResponse().getHeader("Location");
  
    this.mockMvc
        .perform(get(locationPayment))
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.booking.id").value(bookingId))
        .andExpect(jsonPath("$.paymentStatus.paymentStatusName").value("paid"))
;

    // check if the seat status has changed to sold
    Seat seatAfter = seatRepository.findById(4L).get();
    String seatStatusAfterPayment = seatAfter.getSeatStatus().getName();
    Assertions.assertNotEquals(seatStatusbeforePayment, seatStatusAfterPayment);
    Assertions.assertEquals("sold", seatStatusAfterPayment);
  }

  private Long createBooking() throws Exception {
    String baseUrl = "http://localhost:8080/api/";
    Seat seat = seatRepository.findById(4L).get();
    SessionEvent sessionEvent = sessionEventRepository.findById(2L).get();
    TicketReservationKey ticketReservationKey = new TicketReservationKey(seat, sessionEvent);
    TicketReservation ticketReservation = new TicketReservation(ticketReservationKey, false);
    ticketReservationRepository.save(ticketReservation);
    TicketReservationKeyDTO ticketReservationKeyDTO =
        ticketReservationKeyMapper.toDTO(ticketReservationKey);

    ResultActions postTicketReservationRequest =
        this.mockMvc
            .perform(
                post(baseUrl + "bookings/customer/{customerId}/reservationKey", 2L)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketReservationKeyDTO)))
            .andExpect(status().isCreated());

    String locationBooking =
        postTicketReservationRequest.andReturn().getResponse().getHeader("Location");
    System.out.println("location: " + locationBooking);
    ResultActions locationNewBookingrequest =
        this.mockMvc.perform(get(locationBooking).accept(MediaType.APPLICATION_JSON_VALUE));

    BookingDTO bookingDTO =
        objectMapper.readValue(
            locationNewBookingrequest.andReturn().getResponse().getContentAsString(),
            BookingDTO.class);
    return bookingDTO.id();
  }
}
