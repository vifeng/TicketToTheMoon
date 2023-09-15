package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<List<HallDTO>> getAllHalls() throws FinderException {
        return ResponseEntity.ok(hallService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable Long id) throws FinderException {
        return ResponseEntity.ok(hallService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HallDTO> createHall(@RequestBody HallDTO hallDTO)
            throws NullException, CreateException, FinderException, IllegalArgumentException {
        if (hallDTO == null)
            throw new NullException("Hall post is null");
        return ResponseEntity.ok(hallService.createHall(hallDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallDTO> updateHall(@PathVariable Long id, @RequestBody HallDTO hallDTO)
            throws FinderException, NullException, UpdateException, IllegalArgumentException {
        if (hallDTO == null)
            throw new NullException("Hall update is null");
        return ResponseEntity.ok(hallService.updateHall(id, hallDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HallDTO> patchHall(@PathVariable Long id,
            @RequestBody Map<String, Object> hallPatch)
            throws FinderException, NullException, PatchException, IllegalArgumentException {
        if (hallPatch == null || hallPatch.isEmpty())
            throw new NullException("Hall patch is null");
        return ResponseEntity.ok(hallService.patchHall(id, hallPatch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HallDTO> deleteHall(@PathVariable Long id)
            throws FinderException, RemoveException {
        // TODO_END: at the end because of the cascade
        return ResponseEntity.ok(hallService.deleteHall(id));
    }


}
