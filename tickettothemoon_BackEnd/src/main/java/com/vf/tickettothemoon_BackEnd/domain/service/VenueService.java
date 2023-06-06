package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;

@Service
@Transactional
public class VenueService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    /**
     * @return all the venues in the database.
     */
    public List<VenueDTO> findAll() throws FinderException {

        Iterable<Venue> venues = venueRepository.findAll();
        int size;
        if ((size = ((Collection<Venue>) venues).size()) == 0) {
            throw new FinderException("No category in the database");
        }
        List<VenueDTO> venueDTOs = ((Collection<Venue>) venues).stream().map(this::convertToDto)
                .collect(Collectors.toList());
        return venueDTOs;
    }

    private VenueDTO convertToDto(Venue venue) {
        VenueDTO venueDTO = modelMapper.map(venue, VenueDTO.class);
        return venueDTO;
    }

}
