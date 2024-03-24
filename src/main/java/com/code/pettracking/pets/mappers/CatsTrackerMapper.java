package com.code.pettracking.pets.mappers;

import com.code.pettracking.pets.models.dto.PetsUpdateDto;
import com.code.pettracking.pets.models.entities.CatsTracker;

public class CatsTrackerMapper {

    public static CatsTracker mapToPetsTracker(PetsUpdateDto petsUpdateDto){
        CatsTracker catsTracker = CatsTracker.builder().catId(petsUpdateDto.getPetId()).lostTracker(petsUpdateDto.isLostTracker()).build();
        return catsTracker;
    }
}
