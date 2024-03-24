package com.code.pettracking;


import com.code.pettracking.pets.contoller.PetsController;
import com.code.pettracking.pets.exceptions.RecordAlreadyExists;
import com.code.pettracking.pets.models.dto.PetCreationResponseDto;
import com.code.pettracking.pets.models.dto.PetsCreationDto;
import com.code.pettracking.pets.models.entities.Owners;
import com.code.pettracking.pets.service.OwnerService;
import com.code.pettracking.pets.service.PetsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetsController.class)
public class PetsControllerTest {
    @MockBean
    private PetsService petsService;

    @MockBean
    private OwnerService ownerService;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPetEntity() throws Exception {
        Owners mockOwner = Owners.builder().id(1).name("Test").build();
        PetsCreationDto petsCreationDto = PetsCreationDto.builder().name("Tom")
                .inZone(true).ownerId(mockOwner.getId()).trackerType("MEDIUM").petType("Cat").build();
        PetCreationResponseDto petCreationResponseDto = PetCreationResponseDto.builder().petId(1)
                .name("Tom").build();
        when(ownerService.findOwner(mockOwner.getId())).thenReturn(mockOwner);
        when(petsService.save(petsCreationDto)).thenReturn(petCreationResponseDto);
        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(petsCreationDto)))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    void badRequestPet() throws Exception {
        Owners mockOwner = Owners.builder().id(1).name("Test").build();
        PetsCreationDto duplicatePet = PetsCreationDto.builder().name("Tom")
                .inZone(true).ownerId(mockOwner.getId()).trackerType("MEDIUM").petType("Cat").build();;
        when(ownerService.findOwner(mockOwner.getId())).thenReturn(mockOwner);
        when(petsService.save(any())).thenThrow(new RecordAlreadyExists("Pet already exists"));
        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(duplicatePet)))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void petCreationInternalServerError() throws Exception {
        Owners mockOwner = Owners.builder().id(1).name("Test").build();
        PetsCreationDto petRequest = PetsCreationDto.builder().name("Tom")
                .inZone(true).ownerId(mockOwner.getId()).trackerType("MEDIUM").petType("Cat").build();;
        when(ownerService.findOwner(mockOwner.getId())).thenReturn(mockOwner);
        when(petsService.save(any())).thenThrow(new RuntimeException("Failed to save pet"));

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(petRequest)))
                .andExpect(status().isInternalServerError()).andDo(print());
    }
}
