package com.vf.tickettothemoon_BackEnd.domain.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.EmployeeRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.VenueRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.VenueMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.PatchException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;

@Service
@Transactional
public class VenueService {

    private VenueRepository venueRepository;
    private EmployeeRepository employeeRepository;
    private ObjectMapper objectMapper;
    // TODO : maybe I should use AddressMapper instead of objectMapper
    private VenueMapper venueMapper;
    // private static final Logger log = LoggerFactory.getLogger(VenueService.class);

    public VenueService(VenueRepository venueRepository, EmployeeRepository employeeRepository,
            VenueMapper venueMapper, ObjectMapper objectMapper) {
        this.venueRepository = venueRepository;
        this.employeeRepository = employeeRepository;
        this.venueMapper = venueMapper;
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
        List<VenueDTO> venueDTOs = venueMapper.toVenueDTOs(venues);
        return venueDTOs;
    }

    public VenueDTO findById(Long id) throws FinderException {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new FinderException("Venue with id {\" + id + \"} not found"));
        return venueMapper.toVenueDTO(venue);
    }
    
    /**
     * 
     * @param venueDTO
     * @return
     * @throws IllegalArgumentException
     * @throws CreateException
     */
    public VenueDTO createVenue(VenueDTO venueDTO)
            throws IllegalArgumentException, CreateException {
        if (venueDTO.id() != null) {
            throw new DuplicateKeyException("Venue with id {" + venueDTO.id() + "} already exists");
        }
        try {
            Venue venue = venueMapper.toVenue(venueDTO);
            venueRepository.save(venue);
            Venue savedVenue = venueRepository.save(venue);
            VenueDTO savedVenueDTO = venueMapper.toVenueDTO(savedVenue);
            return savedVenueDTO;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Venue not created : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Venue not created : " + e.getMessage(), e);
        }
    }

    public VenueDTO updateVenue(Long id)
            throws FinderException, UpdateException, IllegalArgumentException {
        try {
            Optional<Venue> optionalVenue = venueRepository.findById(id);
            if (optionalVenue.isPresent()) {
                Venue venueToUpdate = optionalVenue.get();
                Venue updatedVenue = venueRepository.save(venueToUpdate);
                return venueMapper.toVenueDTO(updatedVenue);
            } else {
                throw new FinderException("Venue with id {" + id + "} not found");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Venue with id {" + id + "} update failed : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new UpdateException(
                    "Venue with id {" + id + "} update failed : " + e.getMessage(), e);
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
                        // FIXME: l'employee est mis à jour avec un nouvel id si je précise rien ou
                        // un id existant. si on supprime de la liste l'id alors il est supprimé de
                        // la base.si aucun id dans la database alors crée l'employee.
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
                return venueMapper.toVenueDTO(patchVenue);
            } else {
                throw new FinderException("Venue with id {" + id + "} not found");
            }
        } catch (

        IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Venue with id {" + id + "} patch failed : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new PatchException("Venue with id {" + id + "} patch failed" + e.getMessage(), e);
        }
    }

    public VenueDTO deleteVenue(Long id) throws FinderException, RemoveException {
        // TODO_END : delete venue because of cascade and FK
        try {
            Optional<Venue> optionalVenue = venueRepository.findById(id);
            if (optionalVenue.isPresent()) {
                Venue venueToDelete = optionalVenue.get();
                venueRepository.delete(venueToDelete);
                return venueMapper.toVenueDTO(venueToDelete);
            } else {
                throw new FinderException("Venue with id {" + id + "} not found");
            }
        } catch (Exception e) {
            throw new RemoveException("Venue with id {" + id + "} delete failed" + e.getMessage(),
                    e);
        }
    }

    ///////////////////////
    // EMPLOYEE RELATIONSHIP
    ///////////////////////


    /**
     * add an employee venues/{id}/employees/{employeeId}
     * 
     * @param id
     * @param employeeId
     * @return
     * @throws FinderException
     * @throws UpdateException
     */
    public VenueDTO addEmployee(Long id, Long employeeId) throws FinderException, UpdateException {
        try {
            Venue venue = venueRepository.findById(id)
                    .orElseThrow(() -> new FinderException("Venue with id {" + id + "} not found"));
            // Check employee exists
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new FinderException("Employee with id {" + employeeId
                            + "} not found. create employee first."));
            venue.addEmployee(employee);
            Venue savedVenue = venueRepository.save(venue);
            return venueMapper.toVenueDTO(savedVenue);
        } catch (Exception e) {
            throw new UpdateException(
                    "Venue with id {" + id + "} update failed : " + e.getMessage(), e);
        }
    }

    public VenueDTO removeEmployee(Long id, Long employeeId)
            throws FinderException, UpdateException {
        try {
            Venue venue = venueRepository.findById(id)
                    .orElseThrow(() -> new FinderException("Venue with id {" + id + "} not found"));
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                    () -> new FinderException("Employee with id {" + employeeId + "} not found"));
            venue.removeEmployee(employee);
            Venue savedVenue = venueRepository.save(venue);
            return venueMapper.toVenueDTO(savedVenue);
        } catch (Exception e) {
            throw new UpdateException(
                    "Venue with id {" + id + "} update failed : " + e.getMessage(), e);
        }
    }



}
