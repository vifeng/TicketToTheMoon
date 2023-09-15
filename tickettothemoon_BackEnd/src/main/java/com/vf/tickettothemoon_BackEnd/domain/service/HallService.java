package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vf.tickettothemoon_BackEnd.domain.dao.HallRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.HallDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Hall;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.HallMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HallService {

    HallRepository hallRepository;
    VenueRepository venueRepository;


    private static final Logger log = LoggerFactory.getLogger(HallService.class);

    @Autowired
    public HallService(HallRepository hallRepository, VenueRepository venueRepository) {
        this.hallRepository = hallRepository;
        this.venueRepository = venueRepository;
    }

    public List<HallDTO> findAll() throws FinderException {
        Iterable<Hall> halls = hallRepository.findAll();
        int size = ((List<Hall>) halls).size();
        if (size == 0) {
            throw new FinderException("No Halls in the database");
        }
        List<HallDTO> hallDTOs = HallMapper.INSTANCE.toHallDTOs(halls);
        return hallDTOs;

    }

    public HallDTO findById(Long id) throws FinderException {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new FinderException("Hall with id {\" + id + \"} not found"));
        return HallMapper.INSTANCE.toHallDTO(hall);
    }

    public HallDTO createHall(HallDTO hallDTO)
            throws IllegalArgumentException, CreateException, FinderException {
        // TODISCUSS : Maybe checking the DTO id is enough, no need to check the DB ?
        if (hallDTO.id() != null && hallRepository.existsById(hallDTO.id())) {
            throw new DuplicateKeyException("Hall with id {" + hallDTO.id() + "} already exists");
        }
        try {
            Hall hall = HallMapper.INSTANCE.toHall(hallDTO);
            // Retrieve the corresponding Venue from the database based on the venueId in hallDTO
            Venue venue = venueRepository.findById(hall.getVenue().getId())
                    .orElseThrow(() -> new FinderException("FinderException : Venue with id {"
                            + hall.getVenue().getId() + "} not found"));
            Hall savedHall = hallRepository.save(hall);
            return HallMapper.INSTANCE.toHallDTO(savedHall);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Hall not created, IllegalArgumentException : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Hall not created : " + e.getMessage(), e);
        }
    }

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
                return HallMapper.INSTANCE.toHallDTO(hallToUpdate);
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

    public HallDTO patchHall(Long id, Map<String, Object> hallPatch) {
        // TODO: patchHall
        return null;
    }


    public HallDTO deleteHall(Long id) {
        // TODO_END : because of cascade remove
        return null;
    }

}
