package com.vf.eventhubserver.init;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.vf.eventhubserver.domain.dao.BookingRepository;
import com.vf.eventhubserver.domain.dao.CategorySpatialRepository;
import com.vf.eventhubserver.domain.dao.CategoryTariffRepository;
import com.vf.eventhubserver.domain.dao.ConfigurationHallRepository;
import com.vf.eventhubserver.domain.dao.CustomerRepository;
import com.vf.eventhubserver.domain.dao.EmployeeRepository;
import com.vf.eventhubserver.domain.dao.EventRepository;
import com.vf.eventhubserver.domain.dao.HallRepository;
import com.vf.eventhubserver.domain.dao.PaymentRepository;
import com.vf.eventhubserver.domain.dao.PaymentStatusRepository;
import com.vf.eventhubserver.domain.dao.SeatRepository;
import com.vf.eventhubserver.domain.dao.SeatStatusRepository;
import com.vf.eventhubserver.domain.dao.SessionEventRepository;
import com.vf.eventhubserver.domain.dao.TarificationRepository;
import com.vf.eventhubserver.domain.dao.TicketReservationRepository;
import com.vf.eventhubserver.domain.dao.VenueRepository;
import com.vf.eventhubserver.domain.model.Address;
import com.vf.eventhubserver.domain.model.Booking;
import com.vf.eventhubserver.domain.model.CategorySpatial;
import com.vf.eventhubserver.domain.model.CategoryTariff;
import com.vf.eventhubserver.domain.model.ConfigurationHall;
import com.vf.eventhubserver.domain.model.Customer;
import com.vf.eventhubserver.domain.model.Employee;
import com.vf.eventhubserver.domain.model.Event;
import com.vf.eventhubserver.domain.model.Hall;
import com.vf.eventhubserver.domain.model.Payment;
import com.vf.eventhubserver.domain.model.PaymentStatus;
import com.vf.eventhubserver.domain.model.Seat;
import com.vf.eventhubserver.domain.model.SeatStatus;
import com.vf.eventhubserver.domain.model.SessionEvent;
import com.vf.eventhubserver.domain.model.Tarification;
import com.vf.eventhubserver.domain.model.TicketReservation;
import com.vf.eventhubserver.domain.model.TicketReservationKey;
import com.vf.eventhubserver.domain.model.Venue;

@Component
@Transactional
public class DbInitializer {
    // Package Event
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    ConfigurationHallRepository configurationHallRepository;
    @Autowired
    SessionEventRepository sessionEventRepository;
    @Autowired
    EventRepository eventRepository;

    // Package Ticket
    @Autowired
    CategorySpatialRepository categorySpatialRepository;
    @Autowired
    CategoryTariffRepository categoryTariffRepository;
    @Autowired
    SeatStatusRepository seat_StatusRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    TarificationRepository tarificationRepository;

    // Package Reservation
    // @Autowired
    // Ticket_ReservationIdRepository ticket_ReservationIdRepository;
    @Autowired
    TicketReservationRepository ticket_ReservationRepository;
    @Autowired
    BookingRepository bookingRepository;

    // Package Customer order
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentStatusRepository paymentStatus_categoryRepository;


    /**
     * Cette méthode est appelée quand l'application démarre. On ne peut pas
     * utiliser @PostConstruct, qui est appelée trop tôt. Voir
     * https://stackoverflow.com/questions/17346679/transactional-on-postconstruct-method
     *
     */
    @EventListener(ContextRefreshedEvent.class)
    public void initDatabase() {
        createBooking();
    }



    private void createBooking() {
        Employee employee = createEmployee();
        Venue venue = createVenue(employee);
        Hall hall = createHall(venue);
        ConfigurationHall configurationHall = createConfigurationHall(hall);
        // TOCHECK: on devrait choisir le hall puis la configurationHall pour créer un event à faire
        // dans le controller ?
        Event event = createEvent(configurationHall);

        // Ticket
        CategorySpatial categorySpatial = createCategorySpatial();
        Tarification tarification = createTarification(event);
        CategoryTariff categoryTariff = createCategoryTariff(tarification);
        List<SeatStatus> seat_statuses = createSeat_Statuses();
        // TOCHECK: la création d'un seat nécessite sessionEvent. devrait être Event ?
        // nécessite seats. devrait juste ajouté un seat à la liste de seats de sessionEvent pas
        // dans le controller ?
        List<Seat> seats = createSeats(categorySpatial, categoryTariff, seat_statuses.get(0),
                configurationHall);
        SessionEvent sessionEvent = createSessionEvent(event, configurationHall, seats);

        // Customer order
        Customer customer = createCustomer();
        PaymentStatus paymentStatus_category = createPaymentStatus_category();

        // Reservation
        Set<TicketReservation> reservations = createTicket_Reservation(seats, sessionEvent);
        // calcul expiration date and time
        Timestamp booking_creationTimestamp = new Timestamp(System.currentTimeMillis());
        final int BOOKING_EXPIRYDATETIME = 30;
        LocalDateTime booking_creationLocalDateTime = booking_creationTimestamp.toLocalDateTime();
        LocalDateTime expiryTime =
                booking_creationLocalDateTime.plusMinutes(BOOKING_EXPIRYDATETIME);
        Booking booking = createBooking(booking_creationTimestamp, customer, reservations);
        if (expiryTime.isAfter(LocalDateTime.now())) {
            Payment payment = createPayment(booking, paymentStatus_category);
            // changes the seat status to "sold" and save the seat
            booking.getReservations().forEach(reservation -> {
                Seat seat = reservation.getId().getSeatId();
                if (seat != null) {
                    seat.setSeat_Status(seat_statuses.get(3));
                    seatRepository.save(seat);
                }
            });
        } else {
            // changer le status de la place en available
            // cancel booking and ticket_reservation
        }


    }

    private Employee createEmployee() {
        Employee employee = new Employee("username", "Secret1*", "email@admin.fr");
        return employeeRepository.save(employee);
    }

    private Venue createVenue(Employee employee) {
        Address address = new Address("sesame street", "Paris", "75001", "France");
        Venue venue = new Venue("Le Trianon", address);
        venue.addEmployee(employee);
        return venueRepository.save(venue);
    }

    private Hall createHall(Venue venue) {
        Hall hall = new Hall("Hall 1", 300, venue);
        return hallRepository.save(hall);
    }

    private ConfigurationHall createConfigurationHall(Hall hall) {
        ConfigurationHall configurationHall = new ConfigurationHall("debout", hall, 120);
        return configurationHallRepository.save(configurationHall);
    }

    private Event createEvent(ConfigurationHall configurationHall) {
        Event event = new Event("Concert", "concert rock super bien", LocalDate.of(2024, 01, 05),
                LocalDate.of(2024, 02, 25), "Lundi");
        return eventRepository.save(event);
    }

    // Package Ticket
    private void createCategoriesSpatial() {
        CategorySpatial balcon = new CategorySpatial("balcon");
        CategorySpatial fosse = new CategorySpatial("fosse");
        CategorySpatial balcon2 = new CategorySpatial("balcon2");
        categorySpatialRepository.save(balcon);
        categorySpatialRepository.save(fosse);
        categorySpatialRepository.save(balcon2);
    }

    private CategorySpatial createCategorySpatial() {
        CategorySpatial categorySpatial = new CategorySpatial("orchestre");
        return categorySpatialRepository.save(categorySpatial);
    }

    private Tarification createTarification(Event event) {
        Tarification tarification = new Tarification(10, 0.2, 0.5, 0.10, 0.20, 0.30, event);
        return tarificationRepository.save(tarification);
    }

    private CategoryTariff createCategoryTariff(Tarification tarification) {
        CategoryTariff categoryTariff = new CategoryTariff("catégorie 1", tarification);
        return categoryTariffRepository.save(categoryTariff);
    }

    private List<SeatStatus> createSeat_Statuses() {
        List<SeatStatus> seat_Statuses = new ArrayList<>();
        SeatStatus available = new SeatStatus("available");
        SeatStatus unavailable = new SeatStatus("unavailable"); // exemple : if the seat is broken
                                                                // or for the technical team
        SeatStatus booked = new SeatStatus("booked");
        SeatStatus sold = new SeatStatus("sold");
        seat_Statuses.add(available);
        seat_Statuses.add(unavailable);
        seat_Statuses.add(booked);
        seat_Statuses.add(sold);
        seat_StatusRepository.save(booked);
        seat_StatusRepository.save(available);
        seat_StatusRepository.save(sold);
        seat_StatusRepository.save(unavailable);
        return seat_Statuses;
    }

    private List<Seat> createSeats(CategorySpatial categorySpatial, CategoryTariff categoryTariff,
            SeatStatus seat_Status, ConfigurationHall configurationHall) {
        Seat seat1 = new Seat(true, 1, 'A', categorySpatial, categoryTariff, seat_Status,
                configurationHall);
        Seat seat2 = new Seat(true, 2, 'A', categorySpatial, categoryTariff, seat_Status,
                configurationHall);
        Seat seat3 = new Seat(true, 3, 'B', categorySpatial, categoryTariff, seat_Status,
                configurationHall);
        Seat seat4 = new Seat(true, 4, 'B', categorySpatial, categoryTariff, seat_Status,
                configurationHall);
        List<Seat> seats = List.of(seat1, seat2, seat3, seat4);
        return seatRepository.saveAll(seats);
    }

    private SessionEvent createSessionEvent(Event event, ConfigurationHall configurationHall,
            List<Seat> seats) {
        SessionEvent sessionEvent = new SessionEvent(LocalDateTime.of(2026, 01, 05, 20, 00), 90,
                event, configurationHall);
        return sessionEventRepository.save(sessionEvent);
    }

    // Package Reservation

    private Set<TicketReservation> createTicket_Reservation(List<Seat> seats,
            SessionEvent sessionEvent) {
        TicketReservationKey ticket_ReservationKey1 =
                new TicketReservationKey(seats.get(0), sessionEvent);
        TicketReservation oneTicket_Reservation1 =
                new TicketReservation(ticket_ReservationKey1, true);
        TicketReservationKey ticket_ReservationKey2 =
                new TicketReservationKey(seats.get(1), sessionEvent);
        TicketReservation oneTicket_Reservation2 =
                new TicketReservation(ticket_ReservationKey2, true);
        ticket_ReservationRepository.save(oneTicket_Reservation1);
        ticket_ReservationRepository.save(oneTicket_Reservation2);

        Set<TicketReservation> reservations =
                Set.of(oneTicket_Reservation1, oneTicket_Reservation2);
        return reservations;
    }

    private Booking createBooking(Timestamp booking_creationTimestamp, Customer customer,
            Set<TicketReservation> reservations) {
        Booking booking = new Booking(booking_creationTimestamp, customer, reservations);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
    }

    // Package Customer order

    private Customer createCustomer() {
        Address address = new Address("sesame street", "Paris", "75001", "France");
        Customer customer = new Customer("Macaron", "Le glouton", "cookie", "cookie@gmail.com",
                "06 06 06 06 06", address, "1234567891234567");

        Customer c = customerRepository.save(customer);
        return c;
    }

    private PaymentStatus createPaymentStatus_category() {
        PaymentStatus paymentStatus_category = new PaymentStatus("paid");
        return paymentStatus_categoryRepository.save(paymentStatus_category);
    }

    private Payment createPayment(Booking booking, PaymentStatus paymentStatus_category) {
        LocalDateTime paymentDateTime = LocalDateTime.now();
        Payment payment = new Payment(paymentDateTime, booking, paymentStatus_category);
        return paymentRepository.save(payment);
    }

}

