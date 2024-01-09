package com.vf.tickettothemoon_BackEnd.init;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.vf.tickettothemoon_BackEnd.domain.dao.BookingRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.CategorySpatialRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.CategoryTariffRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.ConfigurationHallRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.CustomerRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.EmployeeRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.EventRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.HallRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.PaymentRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.PaymentStatus_categoryRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.SeatRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Seat_StatusRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.SessionEventRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.TarificationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Ticket_ReservationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Booking;
import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;
import com.vf.tickettothemoon_BackEnd.domain.model.CategoryTariff;
import com.vf.tickettothemoon_BackEnd.domain.model.ConfigurationHall;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import com.vf.tickettothemoon_BackEnd.domain.model.Event;
import com.vf.tickettothemoon_BackEnd.domain.model.Hall;
import com.vf.tickettothemoon_BackEnd.domain.model.Payment;
import com.vf.tickettothemoon_BackEnd.domain.model.PaymentStatus_category;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat_Status;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;
import com.vf.tickettothemoon_BackEnd.domain.model.Tarification;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

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
    Seat_StatusRepository seat_StatusRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    TarificationRepository tarificationRepository;

    // Package Reservation
    // @Autowired
    // Ticket_ReservationIdRepository ticket_ReservationIdRepository;
    @Autowired
    Ticket_ReservationRepository ticket_ReservationRepository;
    @Autowired
    BookingRepository bookingRepository;

    // Package Customer order
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentStatus_categoryRepository paymentStatus_categoryRepository;

    private static final Logger log = LoggerFactory.getLogger(DbInitializer.class);

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
        List<Seat_Status> seat_statuses = createSeat_Statuses();
        // TOCHECK: la création d'un seat nécessite sessionEvent. devrait être Event ?
        // nécessite seats. devrait juste ajouté un seat à la liste de seats de sessionEvent pas
        // dans le controller ?
        List<Seat> seats = createSeats(categorySpatial, categoryTariff, seat_statuses.get(0),
                configurationHall);
        SessionEvent sessionEvent = createSessionEvent(event, configurationHall, seats);

        // Customer order
        Customer customer = createCustomer();
        PaymentStatus_category paymentStatus_category = createPaymentStatus_category();

        // Reservation
        log.info("///////////////////////////////////////////////");
        log.info("create a ticket reservation");
        Set<Ticket_Reservation> reservations = createTicket_Reservation(seats, sessionEvent);
        // calcul expiration date and time
        log.info("///////////////////////////////////////////////");
        log.info("Creating a booking");
        Timestamp booking_creationTimestamp = new Timestamp(System.currentTimeMillis());
        final int BOOKING_EXPIRYDATETIME = 30;
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(BOOKING_EXPIRYDATETIME);
        Booking booking = createBooking(booking_creationTimestamp, customer, reservations);
        if (expiryTime.isAfter(LocalDateTime.now())) {
            Payment payment = createPayment(booking, paymentStatus_category);
            // changer le status de la place en booked
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

    private List<Seat_Status> createSeat_Statuses() {
        List<Seat_Status> seat_Statuses = new ArrayList<>();
        Seat_Status available = new Seat_Status("available");
        Seat_Status unavailable = new Seat_Status("unavailable"); // exemple : if the seat is broken
                                                                  // or for the technical team
        Seat_Status booked = new Seat_Status("booked");
        Seat_Status sold = new Seat_Status("sold");
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
            Seat_Status seat_Status, ConfigurationHall configurationHall) {
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
        SessionEvent sessionEvent = new SessionEvent(LocalDateTime.of(2024, 01, 05, 20, 00), 90,
                event, configurationHall);
        return sessionEventRepository.save(sessionEvent);
    }

    // Package Reservation

    private Set<Ticket_Reservation> createTicket_Reservation(List<Seat> seats,
            SessionEvent sessionEvent) {
        Ticket_ReservationKey ticket_ReservationKey1 =
                new Ticket_ReservationKey(seats.get(0), sessionEvent);
        Ticket_Reservation oneTicket_Reservation1 =
                new Ticket_Reservation(ticket_ReservationKey1, true);
        Ticket_ReservationKey ticket_ReservationKey2 =
                new Ticket_ReservationKey(seats.get(1), sessionEvent);
        Ticket_Reservation oneTicket_Reservation2 =
                new Ticket_Reservation(ticket_ReservationKey2, true);

        log.info("//////////////// TICKETS ///////////////////////////////");
        log.info("oneTicket_Reservation1: " + oneTicket_Reservation1);
        log.info("oneTicket_Reservation2: " + oneTicket_Reservation2);
        ticket_ReservationRepository.save(oneTicket_Reservation1);
        ticket_ReservationRepository.save(oneTicket_Reservation2);

        Set<Ticket_Reservation> reservations =
                Set.of(oneTicket_Reservation1, oneTicket_Reservation2);
        return reservations;
    }

    private Booking createBooking(Timestamp booking_creationTimestamp, Customer customer,
            Set<Ticket_Reservation> reservations) {
        Booking booking = new Booking(booking_creationTimestamp, customer, reservations);
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
    }

    // Package Customer order

    private Customer createCustomer() {
        Address address = new Address("sesame street", "Paris", "75001", "France");
        Customer customer = new Customer("cookie", "Macaron", "Le glouton", address);

        Customer c = customerRepository.save(customer);
        return c;
    }

    private PaymentStatus_category createPaymentStatus_category() {
        PaymentStatus_category paymentStatus_category = new PaymentStatus_category("payé");
        return paymentStatus_categoryRepository.save(paymentStatus_category);
    }

    private Payment createPayment(Booking booking, PaymentStatus_category paymentStatus_category) {
        LocalDateTime paymentDateTime = LocalDateTime.now();
        Payment payment = new Payment(paymentDateTime, booking, paymentStatus_category);
        return paymentRepository.save(payment);
    }

}

