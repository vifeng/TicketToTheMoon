package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.VenueService;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;

/**
 * VenueController will handle all the requests related to the Venue entity.
 */
@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;
    private final VenueRepository venueRepository;

    public VenueController(VenueService venueService, VenueRepository venueRepository) {
        this.venueService = venueService;
        this.venueRepository = venueRepository;
    }

    /**
     * @return all the venues in the database.
     * @throws JsonProcessingException
     */
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        try {
            return ResponseEntity.ok(venueService.findAll());
        } catch (FinderException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<VenueDTO> getVenueById(Long id) {
        try {
            return ResponseEntity.ok(venueService.findById(id));
        } catch (FinderException e) {
            return ResponseEntity.notFound().build();
        }
    }
}