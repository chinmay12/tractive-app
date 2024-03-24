package com.code.pettracking.pets.models.dto;

import com.code.pettracking.pets.models.PetTypes;
import com.code.pettracking.pets.models.TrackerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetsRespDto {
    private int id;
    private String name;
    private String petType;
    private String trackerType;
    private int ownerId;
    private boolean inZone;
}
