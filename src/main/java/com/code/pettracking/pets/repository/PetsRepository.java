package com.code.pettracking.pets.repository;

import com.code.pettracking.pets.models.PetTypes;
import com.code.pettracking.pets.models.TrackerType;
import com.code.pettracking.pets.models.dto.PetsTrackerStatsDto;
import com.code.pettracking.pets.models.dto.PetsTypeStatsDto;
import com.code.pettracking.pets.models.entities.CatsTracker;
import com.code.pettracking.pets.models.entities.Pets;

import java.util.List;
import java.util.Map;

public interface PetsRepository {

    Pets save(Pets pet);
    Pets findPet(int petId);

    Pets findPet(int ownerId, String petName);

    List<Pets> findPetsByTrackerType(TrackerType trackerType);

    List<Pets> findPetsByPetType(PetTypes petType);

    int updatePetZone(Pets pet);

    int updatePetTracker(CatsTracker catsTracker);
    List<PetsTrackerStatsDto> countOutofZonePetByTracker();

    List<PetsTypeStatsDto> countOutOfZonePetByPetType();


}
