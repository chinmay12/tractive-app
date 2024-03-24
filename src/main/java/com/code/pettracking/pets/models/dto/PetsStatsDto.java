package com.code.pettracking.pets.models.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PetsStatsDto {

    @JsonProperty("countByTracker")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PetsTrackerStatsDto> petsTrackerStatsDtoList;
    @JsonProperty("countByPetType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PetsTypeStatsDto> petsTypeStatsDtoList;

}
