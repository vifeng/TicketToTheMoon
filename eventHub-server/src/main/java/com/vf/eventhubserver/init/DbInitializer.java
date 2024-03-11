package com.vf.eventhubserver.init;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
@SuppressWarnings({"Null", "unused"})
public class DbInitializer {
    // Package Event

    EmployeeRepository employeeRepository;
    VenueRepository venueRepository;
    HallRepository hallRepository;
    ConfigurationHallRepository configurationHallRepository;
    SessionEventRepository sessionEventRepository;
    EventRepository eventRepository;

    // Package Ticket
    CategorySpatialRepository categorySpatialRepository;
    CategoryTariffRepository categoryTariffRepository;
    SeatStatusRepository seatStatusRepository;
    SeatRepository seatRepository;
    TarificationRepository tarificationRepository;

    // Package Reservation
    TicketReservationRepository ticketReservationRepository;
    BookingRepository bookingRepository;

    // Package Customer order
    CustomerRepository customerRepository;
    PaymentRepository paymentRepository;
    PaymentStatusRepository paymentStatusRepository;

    public DbInitializer(EmployeeRepository employeeRepository, VenueRepository venueRepository,
            HallRepository hallRepository, ConfigurationHallRepository configurationHallRepository,
            SessionEventRepository sessionEventRepository, EventRepository eventRepository,
            CategorySpatialRepository categorySpatialRepository,
            CategoryTariffRepository categoryTariffRepository,
            SeatStatusRepository seatStatusRepository, SeatRepository seatRepository,
            TarificationRepository tarificationRepository,
            TicketReservationRepository ticketReservationRepository,
            BookingRepository bookingRepository, CustomerRepository customerRepository,
            PaymentRepository paymentRepository, PaymentStatusRepository paymentStatusRepository) {
        this.employeeRepository = employeeRepository;
        this.venueRepository = venueRepository;
        this.hallRepository = hallRepository;
        this.configurationHallRepository = configurationHallRepository;
        this.sessionEventRepository = sessionEventRepository;
        this.eventRepository = eventRepository;
        this.categorySpatialRepository = categorySpatialRepository;
        this.categoryTariffRepository = categoryTariffRepository;
        this.seatStatusRepository = seatStatusRepository;
        this.seatRepository = seatRepository;
        this.tarificationRepository = tarificationRepository;
        this.ticketReservationRepository = ticketReservationRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.paymentStatusRepository = paymentStatusRepository;
    }

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
        Event event = createEvent();

        // Ticket
        CategorySpatial categorySpatial = createCategorySpatial();
        Tarification tarification = createTarification(event);
        CategoryTariff categoryTariff = createCategoryTariff(tarification);
        List<SeatStatus> seatStatuses = createSeatStatuses();
        // TOCHECK: la création d'un seat nécessite sessionEvent. devrait être Event ?
        // nécessite seats. devrait juste ajouté un seat à la liste de seats de sessionEvent pas
        // dans le controller ?
        SeatStatus sst = seatStatuses.get(0);
        if (sst == null)
            throw new IllegalArgumentException("SeatStatuses cannot be null");
        List<Seat> seats = createSeats(categorySpatial, categoryTariff, sst, configurationHall);
        SessionEvent sessionEvent = createSessionEvent(event, configurationHall);

        // Customer order
        Customer customer = createCustomer();
        PaymentStatus paymentStatus = createPaymentStatus();

        // Reservation
        Set<TicketReservation> reservations = createTicketReservation(seats, sessionEvent);
        // calcul expiration date and time
        Timestamp bookingCreationTimestamp = new Timestamp(System.currentTimeMillis());
        final int BOOKING_EXPIRYDATETIME = 30;
        LocalDateTime bookingCreationLocalDateTime = bookingCreationTimestamp.toLocalDateTime();
        LocalDateTime expiryTime = bookingCreationLocalDateTime.plusMinutes(BOOKING_EXPIRYDATETIME);
        Booking booking = createBooking(bookingCreationTimestamp, customer, reservations);
        if (expiryTime.isAfter(LocalDateTime.now())) {
            Payment payment = createPayment(booking, paymentStatus);
            // changes the seat status to "sold" and save the seat
            booking.getReservations().forEach(reservation -> {
                Seat seat = reservation.getId().getSeatId();
                if (seat != null) {
                    seat.setSeatStatus(seatStatuses.get(3));
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

    private Event createEvent() {
        Event event = new Event("Concert", "concert rock super bien", LocalDate.of(2024, 01, 05),
                LocalDate.of(2024, 02, 25), "Lundi");
        return eventRepository.save(event);
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

    private List<SeatStatus> createSeatStatuses() {
        List<SeatStatus> seatStatuses = new ArrayList<>();
        SeatStatus available = new SeatStatus("available");
        SeatStatus unavailable = new SeatStatus("unavailable"); // exemple : if the seat is broken
                                                                // or for the technical team
        SeatStatus booked = new SeatStatus("booked");
        SeatStatus sold = new SeatStatus("sold");
        seatStatuses.add(available);
        seatStatuses.add(unavailable);
        seatStatuses.add(booked);
        seatStatuses.add(sold);
        seatStatusRepository.save(booked);
        seatStatusRepository.save(available);
        seatStatusRepository.save(sold);
        seatStatusRepository.save(unavailable);
        return seatStatuses;
    }

    private List<Seat> createSeats(CategorySpatial categorySpatial, CategoryTariff categoryTariff,
            SeatStatus seatStatus, ConfigurationHall configurationHall) {
        Seat seat1 = new Seat(true, 1, 'A', categorySpatial, categoryTariff, seatStatus,
                configurationHall);
        Seat seat2 = new Seat(true, 2, 'A', categorySpatial, categoryTariff, seatStatus,
                configurationHall);
        Seat seat3 = new Seat(true, 3, 'B', categorySpatial, categoryTariff, seatStatus,
                configurationHall);
        Seat seat4 = new Seat(true, 4, 'B', categorySpatial, categoryTariff, seatStatus,
                configurationHall);
        List<Seat> seats = List.of(seat1, seat2, seat3, seat4);
        if (seats == null)
            throw new IllegalArgumentException("Seats cannot be null");
        return seatRepository.saveAll(seats);
    }

    private SessionEvent createSessionEvent(Event event, ConfigurationHall configurationHall) {
        SessionEvent sessionEvent = new SessionEvent(LocalDateTime.of(2026, 01, 05, 20, 00), 90,
                event, configurationHall);
        return sessionEventRepository.save(sessionEvent);
    }

    // Package Reservation

    @SuppressWarnings("null")
    private Set<TicketReservation> createTicketReservation(List<Seat> seats,
            SessionEvent sessionEvent) {
        Seat seat1 = seats.get(0);
        Seat seat2 = seats.get(1);
        if (seat1 == null || seat2 == null)
            throw new IllegalArgumentException("Seat cannot be null");
        TicketReservationKey ticketReservationKey1 = new TicketReservationKey(seat1, sessionEvent);
        TicketReservation oneTicketReservation1 =
                new TicketReservation(ticketReservationKey1, true);
        TicketReservationKey ticketReservationKey2 = new TicketReservationKey(seat2, sessionEvent);
        TicketReservation oneTicketReservation2 =
                new TicketReservation(ticketReservationKey2, true);
        ticketReservationRepository.save(oneTicketReservation1);
        ticketReservationRepository.save(oneTicketReservation2);
        return Set.of(oneTicketReservation1, oneTicketReservation2);
    }

    private Booking createBooking(Timestamp bookingCreationTimestamp, Customer customer,
            Set<TicketReservation> reservations) {
        Booking booking = new Booking(bookingCreationTimestamp, customer, reservations);
        return bookingRepository.save(booking);
    }

    // Package Customer order

    private Customer createCustomer() {
        Address address = new Address("sesame street", "Paris", "75001", "France");
        Customer customer = new Customer("Macaron", "Le glouton", "cookie", "cookie@gmail.com",
                "06 06 06 06 06", address, "1234567891234567");

        return customerRepository.save(customer);
    }

    private PaymentStatus createPaymentStatus() {
        PaymentStatus paymentStatus = new PaymentStatus("paid");
        return paymentStatusRepository.save(paymentStatus);
    }

    private Payment createPayment(Booking booking, PaymentStatus paymentStatus) {
        LocalDateTime paymentDateTime = LocalDateTime.now();
        Payment payment = new Payment(paymentDateTime, booking, paymentStatus);
        return paymentRepository.save(payment);
    }

}

