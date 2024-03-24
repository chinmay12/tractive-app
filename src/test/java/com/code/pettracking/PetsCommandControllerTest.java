package com.code.pettracking;

import com.code.pettracking.pets.contoller.PetsCommandController;
import com.code.pettracking.pets.contoller.PetsQueryController;
import com.code.pettracking.pets.models.dto.PetsRespDto;
import com.code.pettracking.pets.models.dto.PetsUpdateDto;
import com.code.pettracking.pets.service.OwnerService;
import com.code.pettracking.pets.service.PetsFinderService;
import com.code.pettracking.pets.service.PetsService;
import com.code.pettracking.pets.service.PetsUpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetsCommandController.class)
public class PetsCommandControllerTest {

    @MockBean
    private PetsFinderService petsFinderService;

    @MockBean
    private PetsUpdateService petsUpdateService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateInZoneForPetSuccess() throws Exception {
        PetsRespDto mockPets = PetsRespDto.builder().id(1).name("Test")
                               .inZone(true).petType("Cat").trackerType("MEDIUM").build();
        PetsUpdateDto petsUpdateDto = PetsUpdateDto.builder().inZone(false).build();

        when(petsFinderService.findExistingPet(1)).thenReturn(mockPets);
        when(petsUpdateService.updateInZone(petsUpdateDto)).thenReturn(1);

        mockMvc.perform(put("/api/pets/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(petsUpdateDto)))
                .andExpect(status().isAccepted()).andDo(print());

    }

    @Test
    void updateInZoneForPetBadRequest() throws Exception {
        PetsRespDto mockPets = PetsRespDto.builder().id(1).name("Test")
                .inZone(true).petType("Cat").trackerType("MEDIUM").build();
        PetsUpdateDto petsUpdateDto = PetsUpdateDto.builder().inZone(false).build();

        when(petsFinderService.findExistingPet(1)).thenReturn(null);

        mockMvc.perform(put("/api/pets/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(petsUpdateDto)))
                .andExpect(status().isBadRequest()).andDo(print());

    }

}
