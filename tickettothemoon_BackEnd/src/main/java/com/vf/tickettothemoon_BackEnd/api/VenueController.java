package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;
import com.vf.tickettothemoon_BackEnd.domain.service.VenueService;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;

/**
 * VenueController will handle all the requests related to the Venue entity.
 */
@RestController
@RequestMapping("/api/venues")
public class VenueController {
    @Autowired
    private VenueService venueService;
    @Autowired
    private VenueRepository venueRepository;

    /**
     * @return all the venues in the database.
     * @throws JsonProcessingException
     */
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        initdb();
        try {
            return ResponseEntity.ok(venueService.findAll());
        } catch (FinderException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void initdb() {
        Venue venue1 = new Venue("venue1", "address1", "zipCode1", "town1", "contact1", "email1");
        Venue venue2 = new Venue("venue2", "address2", "zipCode2", "town2", "contact2", "email2");
        Venue venue3 = new Venue("venue3", "address3", "zipCode3", "town3", "contact3", "email3");

        venueRepository.save(venue1);
        venueRepository.save(venue2);
        venueRepository.save(venue3);
    }

}
