package com.vf.tickettothemoon_BackEnd.init;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

@Component
@Transactional
public class DbInitializer {
    @Autowired
    VenueRepository venueRepository;

    /**
     * Cette méthode est appelée quand l'application démarre. On ne peut pas
     * utiliser @PostConstruct, qui est appelée trop tôt. Voir
     * https://stackoverflow.com/questions/17346679/transactional-on-postconstruct-method
     * 
     * @param event
     */
    @EventListener(ContextRefreshedEvent.class)
    public void initDatabase() {
        if (venueRepository.count() == 0) {
            // Créer 10 venues...
            List<Venue> venues = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Address address = new Address("street" + i, "city" + i, "country" + i, "zip" + i);
                Venue v = new Venue("venue" + i, "contact" + i, "email" + i, address);
                venues.add(venueRepository.save(v));

            }
        }
    }
}
