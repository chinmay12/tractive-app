package com.code.pettracking.pets.service;


import com.code.pettracking.pets.models.entities.Owners;
import com.code.pettracking.pets.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public Owners findOwner(int ownerId){
        return ownerRepository.findOwner(ownerId);
    }
}
