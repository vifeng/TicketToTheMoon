package com.vf.tickettothemoon_BackEnd.domain.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.VenueMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;
import com.vf.tickettothemoon_BackEnd.exception.PatchException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;

@Service
@Transactional
public class VenueService {


    @Autowired
    VenueRepository venueRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public VenueService(VenueRepository venueRepository, ObjectMapper objectMapper) {
        this.venueRepository = venueRepository;
        this.objectMapper = objectMapper;
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

    public VenueDTO findById(Long id) throws FinderException {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new FinderException("Venue with id {\" + id + \"} not found"));
        return VenueMapper.INSTANCE.toVenueDTO(venue);
    }

    public VenueDTO createVenue(VenueDTO venueDTO) throws NullException, CreateException {
        try {
            Venue venue = VenueMapper.INSTANCE.toVenue(venueDTO);
            venueRepository.save(venue);
            Venue savedVenue = venueRepository.save(venue);
            VenueDTO savedVenueDTO = VenueMapper.INSTANCE.toVenueDTO(savedVenue);
            return savedVenueDTO;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Venue is not created : " + e);
        } catch (Exception e) {
            throw new CreateException("Venue is not created");
        }
    }

    public VenueDTO updateVenue(Long id, VenueDTO venueDTO)
            throws FinderException, UpdateException, IllegalArgumentException {
        try {
            Optional<Venue> venue = venueRepository.findById(id);
            if (venue.isPresent()) {
                Venue venueToUpdate = venue.get();
                // Check null values and required fields
                venueToUpdate.setName(venueDTO.name());
                Address address = VenueMapper.INSTANCE.toAddress(venueDTO.address());
                venueToUpdate.setAddress(address);
                Set<Employee> employees = VenueMapper.INSTANCE.toEmployees(venueDTO.getEmployees());
                venueToUpdate.setEmployees(employees);

                venueRepository.save(venueToUpdate);
                VenueDTO updatedVenueDTO = VenueMapper.INSTANCE.toVenueDTO(venueToUpdate);
                return updatedVenueDTO;
            } else {
                throw new FinderException("Venue with id {" + id + "} not found");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Venue with id {" + id + "} update failed : " + e);
        } catch (Exception e) {
            throw new UpdateException("Venue with id {" + id + "} update failed" + e);
        }
    }

    public VenueDTO patchVenue(Long id, Map<String, Object> venuePatch)
            throws FinderException, PatchException, IllegalArgumentException {
        try {
            Optional<Venue> optionalVenue = venueRepository.findById(id);
            if (optionalVenue.isPresent()) {
                Venue venueToUpdate = optionalVenue.get();

                venuePatch.forEach((key, value) -> {

                    if ("address".equals(key)) {
                        // Handle updating the 'address' field
                        Address addressPatch = objectMapper.convertValue(value, Address.class);
                        venueToUpdate.setAddress(addressPatch);
                    } else if ("employees".equals(key)) {
                        // Handle updating the 'employees' field
                        TypeReference<Set<Employee>> typeReference =
                                new TypeReference<Set<Employee>>() {};
                        Set<Employee> employeesPatch =
                                objectMapper.convertValue(value, typeReference);

                        // Mettre à jour l'ensemble existant avec les éléments du patch
                        // (Supprimer les éléments existants qui ne sont pas dans le patch, ajouter
                        // les éléments du patch)
                        Set<Employee> existingEmployees = venueToUpdate.getEmployees();
                        existingEmployees.clear(); // Supprimer tous les employés existants

                        if (employeesPatch != null) {
                            existingEmployees.addAll(employeesPatch); // Ajouter les employés du
                                                                      // patch
                        }
                    } else {
                        // Handle updating other fields (if any)
                        Field field = ReflectionUtils.findField(Venue.class, key);
                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, venueToUpdate, value);
                    }
                });
                Venue patchVenue = venueRepository.save(venueToUpdate);
                return VenueMapper.INSTANCE.toVenueDTO(patchVenue);
            } else {
                throw new FinderException("Venue with id {" + id + "} not found");
            }
        } catch (

        IllegalArgumentException e) {
            throw new IllegalArgumentException("Venue with id {" + id + "} patch failed : " + e);
        } catch (Exception e) {
            throw new PatchException("Venue with id {" + id + "} patch failed" + e);
        }
    }

    public VenueDTO deleteVenue(Long id) throws FinderException, RemoveException {
        try {
            Optional<Venue> optionalVenue = venueRepository.findById(id);
            if (optionalVenue.isPresent()) {
                Venue venueToDelete = optionalVenue.get();
                venueRepository.delete(venueToDelete);
                return VenueMapper.INSTANCE.toVenueDTO(venueToDelete);
            } else {
                throw new FinderException("Venue with id {" + id + "} not found");
            }
        } catch (Exception e) {
            throw new RemoveException("Venue with id {" + id + "} delete failed" + e);
        }
    }



}
