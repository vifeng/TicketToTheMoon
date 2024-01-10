package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.VenueService;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;
import com.vf.tickettothemoon_BackEnd.exception.PatchException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;

/**
 * VenueController will handle all the requests related to the Venue entity.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/venues")
@Validated
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    /**
     * @return all the venues in the database.
     * @throws JsonProcessingException
     */
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        return ResponseEntity.ok(venueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(@RequestBody VenueDTO venueDTO)
            throws NullException, CreateException {
        if (venueDTO == null)
            throw new NullException("Venue post is null");
        return ResponseEntity.ok(venueService.createVenue(venueDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id)
            throws FinderException, UpdateException, IllegalArgumentException {
        return ResponseEntity.ok(venueService.updateVenue(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VenueDTO> patchVenue(@PathVariable Long id,
            @RequestBody Map<String, Object> venuePatch)
            throws FinderException, PatchException, IllegalArgumentException {
        if (venuePatch == null || venuePatch.isEmpty())
            throw new NullException("Venue patch is null");
        return ResponseEntity.ok(venueService.patchVenue(id, venuePatch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id)
            // TODO_END: At the end, because of the cascade, we will have to delete hall,
            // configHall,
            // sessionEvent, event, tariffication, seat, categoryTariff, CategorySpatial related to
            // the venue.
            // TODO_END : But we should not delete Employee, because he can work in another venue
            // depending on the functionnal requirements.
            // TicketReservation maybe we should not delete to keep the history of the venue. We
            // should not delete customer, payment and paymentstatus
            throws FinderException, RemoveException {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }

    ///////////////////////
    // EMPLOYEE RELATIONSHIP
    ///////////////////////
    @PutMapping("/{id}/employees/{employeeId}")
    public ResponseEntity<VenueDTO> addEmployee(@PathVariable Long id,
            @PathVariable Long employeeId)
            throws FinderException, UpdateException, IllegalArgumentException {
        return ResponseEntity.ok(venueService.addEmployee(id, employeeId));
    }

    @PutMapping("/{id}/remove_employees/{employeeId}")
    public ResponseEntity<VenueDTO> removeEmployee(@PathVariable Long id,
            @PathVariable Long employeeId)
            throws FinderException, UpdateException, IllegalArgumentException {
        return ResponseEntity.ok(venueService.removeEmployee(id, employeeId));
    }



}

