package com.code.pettracking;



import com.code.pettracking.pets.contoller.PetsQueryController;
import com.code.pettracking.pets.models.dto.PetsRespDto;
import com.code.pettracking.pets.models.dto.PetsStatsDto;
import com.code.pettracking.pets.models.dto.PetsTrackerStatsDto;
import com.code.pettracking.pets.models.dto.PetsTypeStatsDto;
import com.code.pettracking.pets.service.PetsFinderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetsQueryController.class)
public class PetsQueryControllerTest {

    @MockBean
    private PetsFinderService petsFinderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchPetSuccess() throws Exception {

        PetsRespDto petsRespDto = PetsRespDto.builder().id(1).name("Test")
                .petType("Cat").trackerType("MEDIUM").ownerId(1).inZone(true).build();

        when(petsFinderService.findExistingPet(1)).thenReturn(petsRespDto);

        mockMvc.perform(get("/api/pets/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void fetchPetNotFound() throws Exception {
        when(petsFinderService.findExistingPet(1)).thenReturn(null);

        mockMvc.perform(get("/api/pets/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void fetchPetsOutSideZoneNullQueryParam() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("groupBy", "");
        mockMvc.perform(get("/api/out-of-zone/pets")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void fetchPetsOutSideZoneByTrackerType() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("groupBy", "tracker-type");
        PetsStatsDto petsStatsDto = new PetsStatsDto();
        List<PetsTrackerStatsDto> petsTrackerStatsDtos = List.of(PetsTrackerStatsDto.builder().trackerType("MEDIUM").count(2).build(),
                PetsTrackerStatsDto.builder().trackerType("SMALL").count(1).build());
        petsStatsDto.setPetsTrackerStatsDtoList(petsTrackerStatsDtos);
        when(petsFinderService.countPetsByTracker()).thenReturn(petsTrackerStatsDtos);
        mockMvc.perform(get("/api/out-of-zone/pets")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void fetchPetsOutSideZoneByPetType() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("groupBy", "pet-type");
        PetsStatsDto petsStatsDto = new PetsStatsDto();
        List<PetsTypeStatsDto> petsTypeStatsDtos = List.of(PetsTypeStatsDto.builder().petType("Dog").count(2).build(),
                PetsTypeStatsDto.builder().petType("Cat").count(1).build());
        when(petsFinderService.countPetsByType()).thenReturn(petsTypeStatsDtos);
        mockMvc.perform(get("/api/out-of-zone/pets")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void fetchPetsOutSideZoneInvalidGroupBy() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("groupBy", "asd");
        PetsStatsDto petsStatsDto = new PetsStatsDto();
        mockMvc.perform(get("/api/out-of-zone/pets")
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andDo(print());
    }
}
