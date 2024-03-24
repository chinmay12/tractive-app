package com.code.pettracking.pets.repository;


import com.code.pettracking.pets.models.entities.Owners;

public interface OwnerRepository {

    Owners findOwner(int ownerId);
}
