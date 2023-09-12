package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.HallRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.HallDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HallService {

    @Autowired
    HallRepository hallRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public HallService(HallRepository hallRepository, ObjectMapper objectMapper) {
        this.hallRepository = hallRepository;
        this.objectMapper = objectMapper;
    }

    public List<HallDTO> findAll() {
        return null;
    }

    public HallDTO findById(Long id) {
        return null;
    }

    public HallDTO createHall(HallDTO hallDTO) {
        return null;
    }

    public HallDTO updateHall(Long id, HallDTO hallDTO) {
        return null;
    }

    public HallDTO patchHall(Long id, Map<String, Object> hallPatch) {
        return null;
    }


    public HallDTO deleteHall(Long id) {
        // TODO_END : because of cascade remove
        return null;
    }

}
