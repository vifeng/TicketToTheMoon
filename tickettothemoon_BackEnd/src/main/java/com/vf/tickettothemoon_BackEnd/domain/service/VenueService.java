package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
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
        int size = ((Collection<Venue>) venues).size();
        if (size == 0) {
            throw new FinderException("No Venues in the database");
        }
        // Mapping des propriétés entre Venue et VenueDTO avec MapStruct
        List<VenueDTO> venueDTOs = VenueMapper.INSTANCE.toVenueDTOs(venues);
        return venueDTOs;
    }



}
