package com.tecsup.petclinic.services;
import com.tecsup.petclinic.entities.Visit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
@SpringBootTest
@Slf4j
public class VisitUpdateServiceTest {
    @Autowired
    private VisitService visitService;
    @Test
    public void testUpdateVisit() {
        int petId = 1;
        LocalDate visitDate = LocalDate.parse("2023-05-29");
        String description = "Test description";
        int up_petId = 4;
        LocalDate up_visitDate = LocalDate.parse("2023-10-29");
        String up_description = "Nuevo test description";
        Visit visit = new Visit(petId, visitDate, description);
        // ------------ Create ---------------
        log.info(">" + visit);
        Visit visitCreated = this.visitService.create(visit);
        log.info(">>" + visitCreated);
        // ------------ Update ---------------
        visitCreated.setPetId(up_petId);
        visitCreated.setDate(up_visitDate);
        visitCreated.setDescription(up_description);
        Visit updatedVisit = this.visitService.update(visitCreated);
        log.info(">>" + visitCreated);
        // ------------- Validation ----------
        assertEquals(up_petId, updatedVisit.getPetId());
        assertEquals(up_visitDate, updatedVisit.getDate());
        assertEquals(up_description, updatedVisit.getDescription());
    }
}
