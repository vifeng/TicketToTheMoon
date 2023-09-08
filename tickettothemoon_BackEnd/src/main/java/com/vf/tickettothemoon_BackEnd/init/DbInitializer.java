package com.vf.tickettothemoon_BackEnd.init;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.vf.tickettothemoon_BackEnd.domain.dao.CategorySpatialRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.CategoryTariffRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.ConfigurationHallRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.CustomerRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.EmployeeRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.EventRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.HallRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.PriceRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.SeatRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.SessionEventRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Ticket_ReservationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;
import com.vf.tickettothemoon_BackEnd.domain.model.CategoryTariff;
import com.vf.tickettothemoon_BackEnd.domain.model.ConfigurationHall;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import com.vf.tickettothemoon_BackEnd.domain.model.Event;
import com.vf.tickettothemoon_BackEnd.domain.model.Hall;
import com.vf.tickettothemoon_BackEnd.domain.model.Price;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

@Component
@Transactional
public class DbInitializer {
    @Autowired
    VenueRepository venueRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    Ticket_ReservationRepository eventOrderRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    ConfigurationHallRepository configurationHallRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    SessionEventRepository sessionEventRepository;
    @Autowired
    CategorySpatialRepository categorySpatialRepository;
    @Autowired
    CategoryTariffRepository categoryTariffRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    EmployeeRepository administratorRepository;

    /**
     * Cette méthode est appelée quand l'application démarre. On ne peut pas
     * utiliser @PostConstruct, qui est appelée trop tôt. Voir
     * https://stackoverflow.com/questions/17346679/transactional-on-postconstruct-method
     * 
     * @param event
     */
    @EventListener(ContextRefreshedEvent.class)
    public void initDatabase() {
        createTicket_Reservation();
        createVenues();
        // TODO: créer les relations une par une et les testées avec des collections de
        // requêtes. il faut faire les services et les controllers
    }

    private void createTicket_Reservation() {
        /*
         * <pre> 1. Create a customer 2. Create a Venue 3. Create a hall 4. Create a
         * configurationHall 5. Create a event 6. Create a sessionEvent 7. Create a BookableSeat 8.
         * Create all categories 9. Create an Area 10. Create all the seats 11. create an order
         * 
         * </pre>
         */
        Customer customer = createCustomer();
        Venue venue = createVenue();
        Hall hall = createHall(venue);
        ConfigurationHall configurationHall = createConfigurationHall(hall);
        Event event = createEvent(configurationHall);
        SessionEvent sessionEvent = createSessionEvent(event, configurationHall);
        CategorySpatial categorySpatial = createCategory();
        Price price = createPrice(event);
        CategoryTariff categoryTariff = createCategoryTariff(configurationHall, price);
        // BookableSeat bookableSeat = createBookableSeat(sessionEvent);

    }


    private Price createPrice(Event event) {
        Price price = new Price(100, event);
        return priceRepository.save(price);
    }

    private CategoryTariff createCategoryTariff(ConfigurationHall configurationHall, Price price) {
        // tarif par catégorie. la catégorie 1 est la plus cher
        CategoryTariff categoryTariff = new CategoryTariff("catégorie 1", configurationHall, price);
        return categoryTariffRepository.save(categoryTariff);
    }

    // private BookableSeat createBookableSeat(SessionEvent sessionEvent) {
    // // TOCHECK: ce fichier n'est pas usefull according to the new database. créer un set de
    // // places réservables
    // // BookableSeat bookableSeat = new BookableSeat("place réservable", 0, sessionEvent);
    // // bookableSeat.createBundleNumerotedSeats(1, 'A',
    // // categoryTariffRepository.findByName("Catégorie 1"),
    // // categorySpatialRepository.findByName("orchestre"), 20);
    // // return bookableSeat;
    // return null;
    // }

    private void createCategories() {
        CategorySpatial balcon = new CategorySpatial("balcon");
        CategorySpatial orchestre = new CategorySpatial("orchestre");
        CategorySpatial fosse = new CategorySpatial("fosse");
        CategorySpatial balcon2 = new CategorySpatial("balcon2");
        categorySpatialRepository.save(balcon);
        categorySpatialRepository.save(orchestre);
        categorySpatialRepository.save(fosse);
        categorySpatialRepository.save(balcon2);
    }

    private CategorySpatial createCategory() {
        CategorySpatial balcon = new CategorySpatial("balcon");
        return categorySpatialRepository.save(balcon);
    }

    private SessionEvent createSessionEvent(Event event, ConfigurationHall configurationHall) {
        SessionEvent sessionEvent = new SessionEvent(LocalDateTime.of(2024, 01, 05, 20, 30, 00),
                150, event, configurationHall);
        return sessionEventRepository.save(sessionEvent);
    }

    private Event createEvent(ConfigurationHall configurationHall) {
        Event event = new Event("Concert", "concert rock super bien", LocalDate.of(2024, 01, 05),
                LocalDate.of(2024, 02, 25), "Lundi");
        return eventRepository.save(event);
    }

    private ConfigurationHall createConfigurationHall(Hall hall) {
        ConfigurationHall configurationHall = new ConfigurationHall("assis numéroté", 120, hall);
        return configurationHallRepository.save(configurationHall);
    }

    private Hall createHall(Venue venue) {
        Hall hall = new Hall("Hall 1", 300, venue);
        return hallRepository.save(hall);
    }

    private void createVenues() {
        if (venueRepository.count() == 0) {
            // Créer 10 venues...
            List<Venue> venues = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Address address = new Address("street" + i, "city" + i, "country" + i, "zip" + i);
                Employee admin = new Employee("username" + i, "passworD%" + i,
                        "email" + i + "mymail@mail.fr");
                administratorRepository.save(admin);
                Venue v = new Venue("venue" + i, address);
                v.addEmployee(admin);
                venues.add(venueRepository.save(v));

            }
        }
    }


    private Venue createVenue() {
        Address address = new Address("bvd Rochechouard", "Paris", "France", "750018");
        Employee admin = new Employee("admin", "Secret1*", "admin@admin.fr");
        administratorRepository.save(admin);
        Venue v = new Venue("Le Trianon", address);
        v.addEmployee(admin);
        return venueRepository.save(v);
    }

    private Customer createCustomer() {
        Address address = new Address("sesame street", "Paris", "75001", "France");
        Customer customer = new Customer("cookie", "Macaron", "Le glouton", address);

        Customer c = customerRepository.save(customer);
        return c;
    }


}

