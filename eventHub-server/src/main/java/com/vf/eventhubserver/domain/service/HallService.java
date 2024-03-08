package com.vf.eventhubserver.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vf.eventhubserver.domain.dao.HallRepository;
import com.vf.eventhubserver.domain.dao.VenueRepository;
import com.vf.eventhubserver.domain.dto.HallDTO;
import com.vf.eventhubserver.domain.model.Hall;
import com.vf.eventhubserver.domain.model.Venue;
import com.vf.eventhubserver.domain.service.mapper.HallMapper;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.UpdateException;

@Service
@Transactional
public class HallService {

    HallRepository hallRepository;
    VenueRepository venueRepository;
    HallMapper hallMapper;


    private static final Logger log = LoggerFactory.getLogger(HallService.class);

    public HallService(HallRepository hallRepository, VenueRepository venueRepository,
            HallMapper hallMapper) {
        this.hallRepository = hallRepository;
        this.venueRepository = venueRepository;
        this.hallMapper = hallMapper;
    }


    // Service for Generic routes

    /**
     * Find all Halls - /halls
     * 
     * @apiNote : Service method to find all Halls from all venues. Useful for testing - GET /halls
     * @return List<HallDTO>
     * @throws FinderException
     */
    public List<HallDTO> findAll() throws FinderException {
        Iterable<Hall> halls = hallRepository.findAllByOrderByVenueIdAsc();
        int size = ((List<Hall>) halls).size();
        if (size == 0) {
            throw new FinderException("No Halls in the database");
        }
        List<HallDTO> hallDTOs = hallMapper.toDTOs(halls);
        return hallDTOs;

    }

    /**
     * Find a Hall by its id - /halls/{id}
     * 
     * @param id
     * @return HallDTO
     * @throws FinderException
     */
    public HallDTO findById(Long id) throws FinderException {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new FinderException("Hall with id {\" + id + \"} not found"));
        return hallMapper.toDTO(hall);
    }

    /**
     * Update a Hall - /halls/{id}
     * 
     * @param id
     * @param hallDTO
     * @return HallDTO
     * @throws FinderException
     * @throws UpdateException
     * @throws IllegalArgumentException
     */
    public HallDTO updateHall(Long id, HallDTO hallDTO)
            throws FinderException, UpdateException, IllegalArgumentException {
        try {
            Optional<Hall> hallOptional = hallRepository.findById(id);
            if (hallOptional.isPresent()) {
                Hall hallToUpdate = hallOptional.get();
                // check null values and required fields
                // if (hallDTO.name() != null)
                hallToUpdate.setName(hallDTO.name());
                hallToUpdate.setCapacityOfHall(hallDTO.capacityOfHall());
                // check if Venue exist and is not null
                if (hallDTO.venue() != null) {
                    Optional<Venue> venue = venueRepository.findById(hallDTO.venue().id());
                    if (venue.isPresent()) {
                        hallToUpdate.setVenue(venue.get());
                    } else {
                        throw new FinderException(
                                "Venue with id {" + hallDTO.venue().id() + "} not found");
                    }
                }
                log.info("Hall to update : " + hallToUpdate.toString());
                hallRepository.save(hallToUpdate);
                return hallMapper.toDTO(hallToUpdate);
            } else {
                throw new FinderException("Hall with id {" + id + "} not found");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Hall not updated, IllegalArgumentException : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new UpdateException("Hall with id {" + id + "} update failed : " + e.getMessage(),
                    e);
        }
    }

    /**
     * Patch a Hall - /halls/{id}
     * 
     * @param id
     * @param Map<String, Object> hallPatch
     * @return HallDTO
     */
    public HallDTO patchHall(Long id, Map<String, Object> hallPatch) {
        // TODO: patchAHall
        return null;
    }

    /**
     * Delete a Hall - /halls/{id}
     * 
     * @param id
     * @return HallDTO
     */
    public HallDTO deleteHall(Long id) {
        // TODO_END : because of cascade remove
        return null;
    }


    // Service for Restful routes

    /**
     * Create a Hall - /venues/{venue_id}/halls
     * 
     * @param hallDTO
     * @return HallDTO
     * @throws IllegalArgumentException
     * @throws CreateException
     * @throws FinderException
     * @throws DuplicateKeyException
     */
    public HallDTO createHall(Long venue_id, HallDTO hallDTO) throws IllegalArgumentException,
            CreateException, FinderException, DuplicateKeyException {
        // TODISCUSS : Maybe checking the DTO id is enough, no need to check the DB ?

        // checks
        venueRepository.getReferenceById(venue_id);
        venueRepository.findById(venue_id).orElseThrow(() -> new FinderException(
                "FinderException : Venue with id {" + venue_id + "} not found"));
        if (venue_id != hallDTO.venue().id())
            throw new IllegalArgumentException("IllegalArgumentException : Venue id {" + venue_id
                    + "} and HallDTO venue id {" + hallDTO.venue().id()
                    + "} are not the same. You cannot create a Hall for a Venue with a different id.");
        if (hallDTO.id() != null && hallRepository.existsById(hallDTO.id())) {
            throw new DuplicateKeyException("Hall with id {" + hallDTO.id() + "} already exists");
        }
        // create and save
        try {
            Hall hall = hallMapper.toEntity(hallDTO);
            Hall savedHall = hallRepository.save(hall);
            return hallMapper.toDTO(savedHall);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Hall not created, IllegalArgumentException : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Hall not created : " + e.getMessage(), e);
        }
    }

    /**
     * Find all Halls by Venue id - /venues/{venue_id}/halls
     * 
     * @param id
     * @return Set<HallDTO>
     * @throws FinderException
     */
    public List<HallDTO> findHallsByVenueId(Long venue_id) throws FinderException {
        Iterable<Hall> halls = hallRepository.findAllHallsByVenueId(venue_id);
        int size = ((List<Hall>) halls).size();
        if (size == 0) {
            throw new FinderException(
                    "No Halls in the database for Venue with id {" + venue_id + "}");
        }
        List<HallDTO> hallDTOs = hallMapper.toDTOs(halls);
        return hallDTOs;
    }

}
