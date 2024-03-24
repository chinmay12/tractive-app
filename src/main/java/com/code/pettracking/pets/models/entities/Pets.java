package com.code.pettracking.pets.models.entities;


import com.code.pettracking.pets.models.PetTypes;
import com.code.pettracking.pets.models.TrackerType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pets {
    private int id;
    private String name;
    private String petType;
    private String trackerType;
    private int ownerId;
    private boolean inZone;
}
