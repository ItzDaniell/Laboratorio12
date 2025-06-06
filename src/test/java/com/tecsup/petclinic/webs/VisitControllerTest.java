package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {
    private static final ObjectMapper om;

    static {
        om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateVisit() throws Exception {
        int petId = 1;
        LocalDate visitDate = LocalDate.parse("2023-05-29");
        String description = "Test description";

        int up_petId = 4;
        LocalDate up_visitDate = LocalDate.parse("2023-10-29");
        String up_description = "Nuevo test description";

        VisitDTO newVisitTO = new VisitDTO();
        newVisitTO.setPetId(petId);
        newVisitTO.setDate(visitDate);
        newVisitTO.setDescription(description);

        // Create
        ResultActions mvcActions = mockMvc.perform(post("/visits")
                    .content(om.writeValueAsString(newVisitTO))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.read(response, "$.id");

        // Update
        VisitDTO upVisitTO = new VisitDTO();
        upVisitTO.setId(id);
        upVisitTO.setPetId(up_petId);
        upVisitTO.setDate(up_visitDate);
        upVisitTO.setDescription(up_description);

        mockMvc.perform(put("/visits/"+id)
                    .content(om.writeValueAsString(upVisitTO))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/visits/" + id))  //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.petId", is(up_petId)))
                .andExpect(jsonPath("$.date", is(up_visitDate)))
                .andExpect(jsonPath("$.description", is(up_description)));

        mockMvc.perform(delete("/visits/" + id))
                .andExpect(status().isOk());
    }

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

    	/**
     * 
     * @throws Exception
     */
	@Test
	public void testDeletePet() throws Exception {

		String PET_NAME = "Beethoven3";
		int TYPE_ID = 1;
		int OWNER_ID = 1;
		String BIRTH_DATE = "2020-05-20";

		PetDTO newPetTO = new PetDTO();
		newPetTO.setName(PET_NAME);
		newPetTO.setTypeId(TYPE_ID);
		newPetTO.setOwnerId(OWNER_ID);
		newPetTO.setBirthDate(BIRTH_DATE);

		ResultActions mvcActions = mockMvc.perform(post("/pets")
						.content(om.writeValueAsString(newPetTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/pets/" + id ))
				/*.andDo(print())*/
				.andExpect(status().isOk());
	}

	@Test
	public void testDeletePetKO() throws Exception {

		mockMvc.perform(delete("/pets/" + "1000" ))
				/*.andDo(print())*/
				.andExpect(status().isNotFound());
	}
}
