package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class VisitController {

    private final VisitService visitService;
    private final VisitMapper mapper;

    public VisitController(VisitService visitService, VisitMapper mapper) {
        this.visitService = visitService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/visits")
    public ResponseEntity<List<VisitDTO>> findAllVisits() {
        List<Visit> visits = visitService.findAll();
        log.info("Visits found: {}", visits.size());
        visits.forEach(item -> log.debug("Visit details: {}", item));

        List<VisitDTO> visitsTO = this.mapper.toVisitDTOList(visits);
        log.info("Visits DTOs converted: {}", visitsTO.size());
        visitsTO.forEach(item -> log.debug("VisitDTO details: {}", item));

        return ResponseEntity.ok(visitsTO);
    }

    @PostMapping(value = "/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VisitDTO> create(@RequestBody VisitDTO visitDTO) {
        Visit newVisit = this.mapper.toVisit(visitDTO);
        VisitDTO newVisitDTO = this.mapper.toVisitDTO(visitService.create(newVisit));

        log.info("Visit created: {}", newVisitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVisitDTO);
    }

    @PutMapping(value = "/visits/{id}")
    public ResponseEntity<VisitDTO> update(@RequestBody VisitDTO visitDTO, @PathVariable Integer id) {
        VisitDTO updatedVisitDTO = null;

        try {
            Visit visitToUpdate = visitService.findById(id);

            // Update fields
            visitToUpdate.setPetId(visitDTO.getPetId());
            visitToUpdate.setDate(mapper.stringToDate(String.valueOf(visitDTO.getDate())));
            visitToUpdate.setDescription(visitDTO.getDescription());

            visitService.update(visitToUpdate);
            updatedVisitDTO = this.mapper.toVisitDTO(visitToUpdate);
            log.info("Visit updated: {}", updatedVisitDTO);

        } catch (VisitNotFoundException e) {
            log.error("Visit with ID {} not found for update.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVisitDTO);
    }

    @DeleteMapping(value = "/visits/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            visitService.delete(id);
            log.info("Visit with ID {} deleted successfully.", id);
            return ResponseEntity.ok("Visit with ID: " + id + " deleted successfully.");
        } catch (VisitNotFoundException e) {
            log.error("Visit with ID {} not found for deletion.", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/visits/date/{date}")
    public ResponseEntity<List<VisitDTO>> findByDate(@PathVariable String date) {
        try {
            LocalDate visitDate = mapper.stringToDate(date);
            List<Visit> visits = visitService.findByDate(visitDate);
            if (visits.isEmpty()) {
                log.warn("No visits found for date: {}", date);
                return ResponseEntity.notFound().build();
            }
            List<VisitDTO> visitDTOs = mapper.toVisitDTOList(visits);
            log.info("Found {} visits for date: {}", visits.size(), date);
            return ResponseEntity.ok(visitDTOs);
        } catch (VisitNotFoundException e) {
            log.error("VisitNotFoundException for date {}: {}", date, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error finding visits by date {}: {}", date, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}