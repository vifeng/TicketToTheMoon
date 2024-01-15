package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
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
import com.vf.tickettothemoon_BackEnd.domain.dto.HallDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.HallService;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;
import com.vf.tickettothemoon_BackEnd.exception.PatchException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;

/**
 * HallController will handle all the requests related to the Hall entity.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    // Generic routes
    /**
     * @apiNote Get all Halls from all Venues - Useful for testing - /halls
     * @return List<HallDTO>
     */
    @GetMapping("/halls")
    public ResponseEntity<List<HallDTO>> getAllHalls() {
        return ResponseEntity.ok(hallService.findAll());
    }


    @GetMapping("/halls/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable Long id) throws FinderException {
        return ResponseEntity.ok(hallService.findById(id));
    }

    @PutMapping("/halls/{id}")
    public ResponseEntity<HallDTO> updateHall(@PathVariable Long id, @RequestBody HallDTO hallDTO)
            throws FinderException, NullException, UpdateException, IllegalArgumentException {
        if (hallDTO == null)
            throw new NullException("Hall update is null");
        return ResponseEntity.ok(hallService.updateHall(id, hallDTO));
    }

    @PatchMapping("/halls/{id}")
    public ResponseEntity<HallDTO> patchHall(@PathVariable Long id,
            @RequestBody Map<String, Object> hallPatch)
            throws FinderException, NullException, PatchException, IllegalArgumentException {
        if (hallPatch == null || hallPatch.isEmpty())
            throw new NullException("Hall patch is null");
        return ResponseEntity.ok(hallService.patchHall(id, hallPatch));
    }

    @DeleteMapping("/halls/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id)
            throws FinderException, RemoveException {
        // TODO_END: at the end because of the cascade
        hallService.deleteHall(id);
        return ResponseEntity.noContent().build();
    }

    // Restful routes

    ///////////////////////
    // By VENUE RELATIONSHIP
    ///////////////////////

    /**
     * @apiNote Create a Hall attached to a Venue- /venues/{venue_id}/halls
     * @param hallDTO
     * @return HallDTO
     * @throws NullException
     * @throws CreateException
     * @throws FinderException
     * @throws IllegalArgumentException
     */
    @PostMapping("/venues/{venue_id}/halls")
    public ResponseEntity<HallDTO> createHallForVenueId(@PathVariable Long venue_id,
            @RequestBody HallDTO hallDTO) throws NullException, CreateException, FinderException,
            IllegalArgumentException, DuplicateKeyException {
        if (hallDTO == null)
            throw new NullException("Hall post is null");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hallService.createHall(venue_id, hallDTO));
    }

    /**
     * @apiNote Get all Halls by Venue - /venues/{venue_id}/halls
     * @param id
     * @return List<HallDTO>
     * @throws FinderException
     */
    @GetMapping("/venues/{venue_id}/halls")
    public ResponseEntity<List<HallDTO>> getHallsByVenue(@PathVariable Long venue_id)
            throws FinderException {
        return ResponseEntity.ok(hallService.findHallsByVenueId(venue_id));
    }


}
