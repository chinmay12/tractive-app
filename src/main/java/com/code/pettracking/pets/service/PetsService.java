package com.code.pettracking.pets.service;

import com.code.pettracking.pets.exceptions.RecordAlreadyExists;
import com.code.pettracking.pets.mappers.PetsMapper;
import com.code.pettracking.pets.models.dto.PetCreationResponseDto;
import com.code.pettracking.pets.models.dto.PetsCreationDto;
import com.code.pettracking.pets.models.entities.Pets;
import com.code.pettracking.pets.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetsService {

    @Autowired
    private PetsRepository petsRepository;

    public PetCreationResponseDto save(PetsCreationDto petsCreationDto){


        Pets existingPet = petsRepository.findPet(petsCreationDto.getOwnerId(), petsCreationDto.getName());
        if(existingPet != null){
            throw new RecordAlreadyExists("Pet already exists");
        }
        Pets reqData = PetsMapper.mapToPets(petsCreationDto);

        Pets savedPet  = petsRepository.save(reqData);
        PetCreationResponseDto savedPetsDto = PetsMapper.mapToPetCreationResponseDto(savedPet);
        return savedPetsDto;
    }


}
