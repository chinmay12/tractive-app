package com.code.pettracking.pets.service;


import com.code.pettracking.pets.mappers.PetsMapper;
import com.code.pettracking.pets.models.PetTypes;
import com.code.pettracking.pets.models.TrackerType;
import com.code.pettracking.pets.models.dto.PetsRespDto;
import com.code.pettracking.pets.models.dto.PetsTrackerStatsDto;
import com.code.pettracking.pets.models.dto.PetsTypeStatsDto;
import com.code.pettracking.pets.models.entities.Pets;
import com.code.pettracking.pets.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetsFinderService {

    @Autowired
    private PetsRepository petsRepository;

    public PetsRespDto findExistingPet(int petId){
        Pets existingPet = petsRepository.findPet(petId);
        if(existingPet == null){
            return null;
        }
        PetsRespDto PetsRespDto = PetsMapper.maptoPetsResponseDto(existingPet);
        return PetsRespDto;
    }

    public List<PetsRespDto> findPetsByTracker(TrackerType trackerType){
        List<Pets> pets = petsRepository.findPetsByTrackerType(trackerType);
        List<PetsRespDto> petsRespDtos = pets.stream().map(pet -> PetsMapper.maptoPetsResponseDto(pet))
                .collect(Collectors.toList());
        return petsRespDtos;
    }

    public List<PetsRespDto> findPetsByType(PetTypes petType){
        List<Pets> pets = petsRepository.findPetsByPetType(petType);
        List<PetsRespDto> petsRespDtos = pets.stream().map(pet -> PetsMapper.maptoPetsResponseDto(pet))
                .collect(Collectors.toList());
        return petsRespDtos;
    }

    public List<PetsTrackerStatsDto> countPetsByTracker(){
        List<PetsTrackerStatsDto> petsTrackerStatsDtos  = petsRepository.countOutofZonePetByTracker();
        return petsTrackerStatsDtos;
    }

    public List<PetsTypeStatsDto> countPetsByType(){
        List<PetsTypeStatsDto> petsTypeStatsDtos = petsRepository.countOutOfZonePetByPetType();
        return petsTypeStatsDtos;
    }
}
