package com.code.pettracking.pets.models.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PetsUpdateDto {
    private int petId;
    private boolean inZone;
    private boolean lostTracker;
}
