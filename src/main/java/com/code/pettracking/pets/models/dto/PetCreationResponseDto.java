package com.code.pettracking.pets.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PetCreationResponseDto {
    private int petId;
    private String name;
}
