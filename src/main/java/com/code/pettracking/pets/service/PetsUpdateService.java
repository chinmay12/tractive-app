package com.code.pettracking.pets.service;

import com.code.pettracking.pets.mappers.CatsTrackerMapper;
import com.code.pettracking.pets.mappers.PetsMapper;
import com.code.pettracking.pets.models.dto.PetsUpdateDto;
import com.code.pettracking.pets.models.entities.CatsTracker;
import com.code.pettracking.pets.models.entities.Pets;
import com.code.pettracking.pets.repository.CatsTrackerRepository;
import com.code.pettracking.pets.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetsUpdateService {

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private CatsTrackerRepository catsTrackerRepository;

    public int updateInZone(PetsUpdateDto petsUpdateDto){
        Pets pet = PetsMapper.mapToPets(petsUpdateDto);
        return petsRepository.updatePetZone(pet);
    }

    @Transactional
    public int updatePetTracker(PetsUpdateDto petsUpdateDto){
        CatsTracker catsTracker = CatsTrackerMapper.mapToPetsTracker(petsUpdateDto);
        Pets existingCat = petsRepository.findPet(petsUpdateDto.getPetId());
        catsTrackerRepository.update(catsTracker);
        if(petsUpdateDto.isLostTracker()) {
            Pets petUpdate = Pets.builder().id(petsUpdateDto.getPetId()).inZone(false).build();
            return petsRepository.updatePetZone(petUpdate);
        }
        return 1;
    }
}
