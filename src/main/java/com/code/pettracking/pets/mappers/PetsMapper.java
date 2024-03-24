package com.code.pettracking.pets.mappers;

import com.code.pettracking.pets.models.dto.PetCreationResponseDto;
import com.code.pettracking.pets.models.dto.PetsCreationDto;
import com.code.pettracking.pets.models.dto.PetsRespDto;
import com.code.pettracking.pets.models.dto.PetsUpdateDto;
import com.code.pettracking.pets.models.entities.Pets;

public class PetsMapper {

    public static Pets mapToPets(PetsCreationDto petsCreationDto){
        Pets pet = Pets.builder().name(petsCreationDto.getName())
                .trackerType(petsCreationDto.getTrackerType())
                .petType(petsCreationDto.getPetType())
                .ownerId(petsCreationDto.getOwnerId())
                .inZone(petsCreationDto.isInZone())
                .build();
        return pet;
    }

    public static PetCreationResponseDto mapToPetCreationResponseDto(Pets pet){
        PetCreationResponseDto petsCreationDto = PetCreationResponseDto.builder().petId(pet.getId()).
                                        name(pet.getName()).build();
        return petsCreationDto;
    }

    public static PetsRespDto maptoPetsResponseDto(Pets pet){
        PetsRespDto petsRespDto = PetsRespDto.builder().id(pet.getId()).name(pet.getName()).petType(pet.getPetType())
                .trackerType(pet.getTrackerType()).inZone(pet.isInZone()).ownerId(pet.getOwnerId())
                .build();
        return petsRespDto;
    }

    public static Pets mapToPets(PetsUpdateDto petsUpdateDto){
        Pets pet = Pets.builder().id(petsUpdateDto.getPetId()).inZone(petsUpdateDto.isInZone()).build();
        return pet;
    }
}
