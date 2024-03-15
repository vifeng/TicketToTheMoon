package com.vf.eventhubserver.venue;

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
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.PatchException;
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.exception.UpdateException;
import com.vf.eventhubserver.persona.Address;
import com.vf.eventhubserver.persona.Employee;
import com.vf.eventhubserver.persona.EmployeeRepository;

@Service
@Transactional
public class VenueService {

    private VenueRepository venueRepository;
    private EmployeeRepository employeeRepository;
    private ObjectMapper objectMapper;
    private VenueMapper venueMapper;
    static final String VEMSG = "Venue with id {";
    static final String NOTFOUNDMSG = "} not found";
    static final String VENUENULLMSG = "Venue is null";
    static final String UPDATEMSG = "} update failed : ";

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
        return venueMapper.toDTOs(venues);
    }

    public VenueDTO findById(Long id) throws FinderException {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new FinderException(VEMSG + id + NOTFOUNDMSG));
        if (venue == null)
            throw new NullException(VENUENULLMSG);
        return venueMapper.toDTO(venue);
    }

    /**
     * @param venueDTO
     * @return
     * @throws IllegalArgumentException
     * @throws CreateException
     */
    public VenueDTO createVenue(VenueDTO venueDTO)
            throws IllegalArgumentException, CreateException {
        if (venueDTO.id() != null) {
            throw new DuplicateKeyException(VEMSG + venueDTO.id() + "} already exists");
        }
        try {
            Venue venue = venueMapper.toEntity(venueDTO);
            venueRepository.save(venue);
            Venue savedVenue = venueRepository.save(venue);
            return venueMapper.toDTO(savedVenue);
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
                if (venueToUpdate == null) {
                    throw new NullException(VENUENULLMSG);
                }
                Venue updatedVenue = venueRepository.save(venueToUpdate);
                return venueMapper.toDTO(updatedVenue);
            } else {
                throw new FinderException(VEMSG + id + NOTFOUNDMSG);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(VEMSG + id + UPDATEMSG + e.getMessage(), e);
        } catch (Exception e) {
            throw new UpdateException(VEMSG + id + UPDATEMSG + e.getMessage(), e);
        }
    }

    public VenueDTO patchVenue(Long id, Map<String, Object> venuePatch)
            throws FinderException, PatchException, IllegalArgumentException {
        try {
            Optional<Venue> optionalVenue = venueRepository.findById(id);
            if (optionalVenue.isPresent()) {
                Venue venueToUpdate = optionalVenue.get();

                venuePatch.forEach((key, value) -> {
                    if (key != null) {
                        if ("address".equals(key)) {
                            Address addressPatch = objectMapper.convertValue(value, Address.class);
                            venueToUpdate.setAddress(addressPatch);
                        } else if ("employees".equals(key)) {
                            TypeReference<Set<Employee>> typeReference =
                                    new TypeReference<Set<Employee>>() {};
                            Set<Employee> employeesPatch =
                                    objectMapper.convertValue(value, typeReference);

                            Set<Employee> existingEmployees = venueToUpdate.getEmployees();
                            existingEmployees.clear();

                            if (employeesPatch != null) {
                                existingEmployees.addAll(employeesPatch);
                            }
                        } else {
                            Field field = ReflectionUtils.findField(Venue.class, key);
                            if (field != null) {
                                ReflectionUtils.makeAccessible(field);
                                ReflectionUtils.setField(field, venueToUpdate, value);
                            }
                        }
                    }
                });
                if (venueToUpdate == null) {
                    throw new UpdateException("Venue to update is null. Patch failed.");
                }
                Venue patchVenue = venueRepository.save(venueToUpdate);
                return venueMapper.toDTO(patchVenue);
            } else {
                throw new FinderException(VEMSG + id + NOTFOUNDMSG);
            }
        } catch (

        IllegalArgumentException e) {
            throw new IllegalArgumentException(VEMSG + id + "} patch failed : " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new PatchException(VEMSG + id + "} patch failed" + e.getMessage(), e);
        }
    }

    public void deleteVenue(Long id) throws FinderException, RemoveException {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new FinderException(VEMSG + id + NOTFOUNDMSG));
        if (venue == null) {
            throw new NullException(VENUENULLMSG);
        }
        venueRepository.delete(venue);
    }

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
                    .orElseThrow(() -> new FinderException(VEMSG + id + NOTFOUNDMSG));
            // Check employee exists
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new FinderException("Employee with id {" + employeeId
                            + "} not found. create employee first."));
            venue.addEmployee(employee);
            Venue savedVenue = venueRepository.save(venue);
            return venueMapper.toDTO(savedVenue);
        } catch (Exception e) {
            throw new UpdateException(VEMSG + id + UPDATEMSG + e.getMessage(), e);
        }
    }

    public VenueDTO removeEmployee(Long id, Long employeeId)
            throws FinderException, UpdateException {
        try {
            Venue venue = venueRepository.findById(id)
                    .orElseThrow(() -> new FinderException(VEMSG + id + NOTFOUNDMSG));
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                    () -> new FinderException("Employee with id {" + employeeId + NOTFOUNDMSG));
            venue.removeEmployee(employee);
            Venue savedVenue = venueRepository.save(venue);
            return venueMapper.toDTO(savedVenue);
        } catch (Exception e) {
            throw new UpdateException(VEMSG + id + UPDATEMSG + e.getMessage(), e);
        }
    }

}
