package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateVisit() throws Exception {

        int PET_ID = 1;
        LocalDate DATE = LocalDate.of(2024, 6, 5);
        String DESCRIPTION = "Consulta de control";

        VisitDTO newVisitDTO = new VisitDTO();
        newVisitDTO.setPetId(PET_ID);
        newVisitDTO.setDate(DATE);
        newVisitDTO.setDescription(DESCRIPTION);

        this.mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisitDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.petId", is(PET_ID)))
                .andExpect(jsonPath("$.date", is(DATE.toString())))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)));
    }


}
