package com.vf.eventhubserver.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.UpdateException;

@Service
@Transactional
public class HallService {

    HallRepository hallRepository;
    VenueRepository venueRepository;
    HallMapper hallMapper;
    static final String HALLMSG = "Hall with id {";
    static final String NOTFOUNDMSG = "} not found";
    static final String HALLNULL = "Hall is null";

    public HallService(HallRepository hallRepository, VenueRepository venueRepository,
            HallMapper hallMapper) {
        this.hallRepository = hallRepository;
        this.venueRepository = venueRepository;
        this.hallMapper = hallMapper;
    }

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
        return hallMapper.toDTOs(halls);
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
                .orElseThrow(() -> new FinderException(HALLMSG + id + NOTFOUNDMSG));
        if (hall == null) {
            throw new NullException(HALLMSG + id + HALLNULL);
        }
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
                hallToUpdate.setName(hallDTO.name());
                hallToUpdate.setCapacityOfHall(hallDTO.capacityOfHall());
                if (hallDTO.venue() != null) {
                    @SuppressWarnings("null")
                    Optional<Venue> venue = venueRepository.findById(hallDTO.venue().id());
                    if (venue.isPresent()) {
                        hallToUpdate.setVenue(venue.get());
                    } else {
                        throw new FinderException(
                                "Venue with id {" + hallDTO.venue().id() + NOTFOUNDMSG);
                    }
                }
                hallRepository.save(hallToUpdate);
                return hallMapper.toDTO(hallToUpdate);
            } else {
                throw new FinderException(HALLMSG + id + NOTFOUNDMSG);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Hall not updated, IllegalArgumentException : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new UpdateException(HALLMSG + id + "} update failed : " + e.getMessage(), e);
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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Delete a Hall - /halls/{id}
     * 
     * @param id
     * @return HallDTO
     */
    public void deleteHall(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new FinderException(HALLMSG + id + NOTFOUNDMSG));
        if (hall == null) {
            throw new NullException(HALLMSG + id + HALLNULL);
        }
        hallRepository.delete(hall);
    }

    /**
     * Create a Hall - /venues/{venueId}/halls
     * 
     * @param hallDTO
     * @return HallDTO
     * @throws IllegalArgumentException
     * @throws CreateException
     * @throws FinderException
     * @throws DuplicateKeyException
     */
    @SuppressWarnings("null")
    public HallDTO createHall(Long venueId, HallDTO hallDTO) throws IllegalArgumentException,
            CreateException, FinderException, DuplicateKeyException {
        venueRepository.getReferenceById(venueId);
        if (!Objects.equals(venueId, hallDTO.venue().id()))
            throw new IllegalArgumentException("IllegalArgumentException : Venue id {" + venueId
                    + "} and HallDTO venue id {" + hallDTO.venue().id()
                    + "} are not the same. You cannot create a Hall for a Venue with a different id.");

        if (hallDTO.id() != null && hallRepository.existsById(hallDTO.id())) {
            throw new DuplicateKeyException(HALLMSG + hallDTO.id() + "} already exists");
        }
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
     * Find all Halls by Venue id - /venues/{venueId}/halls
     * 
     * @param id
     * @return Set<HallDTO>
     * @throws FinderException
     */
    public List<HallDTO> findHallsByVenueId(Long venueId) throws FinderException {
        Iterable<Hall> halls = hallRepository.findAllHallsByVenueId(venueId);
        int size = ((List<Hall>) halls).size();
        if (size == 0) {
            throw new FinderException(
                    "No Halls in the database for Venue with id {" + venueId + "}");
        }
        return hallMapper.toDTOs(halls);
    }

}
